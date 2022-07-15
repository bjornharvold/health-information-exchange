/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform;

import com.hxcel.globalhealth.platform.springsecurity.UserDetailsImpl;
import com.hxcel.globalhealth.platform.springsecurity.DataAccessExceptionImpl;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.model.*;
import com.hxcel.globalhealth.platform.dao.*;
import com.hxcel.globalhealth.platform.dto.UserDto;
import com.hxcel.globalhealth.platform.dto.RoleDto;
import com.hxcel.globalhealth.platform.model.Image;
import com.hxcel.globalhealth.common.spec.image.ImageResizer;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.cms.spec.CmsService;
import com.hxcel.globalhealth.cms.spec.CmsException;
import net.sf.dozer.util.mapping.MapperIF;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author: Bjorn Harvold
 * User: paul
 * Date: May 14, 2006
 * Time: 2:03:27 AM
 */
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USERS = "/content/users";

    /**
     * This method is for spring security
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserDetails result = null;
        IUserDto u = null;

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username/Password cannot be empty");
        }
        try {
            u = getUserByUsername(username);

            if (u == null) {
                throw new UsernameNotFoundException(username + " cannot be found");
            }

            List<UserRole> roles = userRoleDAO.searchForUserRoles(u.getId(), null, null, null);
            result = new UserDetailsImpl(u, roles);

        } catch (PersistenceException e) {
            throw new DataAccessExceptionImpl("Roles for user with ID: " + u.getId() + " cannot be found");
        } catch (PlatformException e) {
            throw new DataAccessExceptionImpl(username + " cannot be found");
        }

        return result;
    }

    @Override
    public IUserDto getUser(String username, String password) throws PlatformException {
        if (StringUtils.isBlank(username)) {
            throw new PlatformException("error.missing.data", username);
        }
        if (StringUtils.isBlank(password)) {
            throw new PlatformException("error.missing.data", password);
        }

        UserDto result = null;

        try {
            User u = userDAO.getUserByUsername(username);

            if (u != null) {
                StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
                if (spe.checkPassword(password, u.getPassword())) {
                    result = (UserDto) mapperIF.map(u, UserDto.class);
                }
            }

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return result;
    }

    @Override
    public IUserDto getUser(String id) throws PlatformException {
        if (StringUtils.isBlank(id)) {
            throw new PlatformException("error.missing.data", id);
        }

        UserDto result = null;

        try {
            User u = userDAO.get(User.class, id);

            if (u != null) {
                result = (UserDto) mapperIF.map(u, UserDto.class);
            }

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return result;
    }

    @Override
    public void deactivateUser(String userId) throws PlatformException {
        try {
            userDAO.deactivateUser(userId);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }
    }

    @Override
    public void activateUser(String userId) throws PlatformException {
        try {
            userDAO.activateUser(userId);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }
    }

    @Override
    public Boolean isUserUnique(String userId, String username) throws PlatformException {
        Boolean result;
        try {
            User u = userDAO.isUserUnique(userId, username);
            result = u == null;
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return result;
    }

    @Override
    public IUserDto getUserByUsername(String username) throws PlatformException {
        UserDto result = null;

        try {
            if (StringUtils.isNotBlank(username)) {
                User u = userDAO.getUserByUsername(username);
                if (u != null) {
                    result = (UserDto) mapperIF.map(u, UserDto.class);
                }
            } else {
                throw new PlatformException("error.missing.data", "username");
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return result;
    }

    @Override
    public Boolean isUsernameTaken(String username) throws PlatformException {
        Boolean result = false;

        if (log.isTraceEnabled()) {
            log.trace("Checking if username " + username + " is taken");
        }
        IUserDto u = getUserByUsername(username);
        if (u != null) {
            result = true;
        }
        if (log.isTraceEnabled()) {
            log.trace("Username taken: " + result);
        }

        return result;
    }

    @Override
    public List<IUserDto> searchForUsers(String name, Integer index, Integer maxResults) throws PlatformException {
        return getUsers(name, index, maxResults);
    }

    @Override
    public List<IUserDto> getUsers(String name, Integer index, Integer maxResults) throws PlatformException {
        List<IUserDto> result = null;

        try {

            // find the users
            List<User> users = userDAO.getUsers(name, index, maxResults);

            // convert users to dtos
            if (users != null) {
                result = new ArrayList<IUserDto>();
                for (User user : users) {
                    result.add((UserDto) mapperIF.map(user, UserDto.class));
                }
            }

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return result;
    }

    @Override
    public Integer getUserCount(String name) throws PlatformException {
        try {
            return userDAO.getUserCount(name);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }
    }

    /**
     * Probably one of the more complex methods. It create a complete user ready for
     * db insertion with all the necessary fields
     * <p/>
     * - creates a user entity with a public profile.
     * - creates relationship with HXCEL orgnization (all users are part of the HXCEL organization by default)
     * - creates a Patient entity with a PHR
     * - creates the relationship between the User and the Patient
     * - creates a PersonalProfile and attaches it to the phr
     *
     * @param dto
     * @return
     * @throws PlatformException
     */
    @Override
    public IUserDto saveNewUser(IUserDto dto) throws PlatformException {

        if (isUsernameTaken(dto.getUsername())) {
            if (log.isDebugEnabled()) {
                log.debug("User already exists with username: " + dto.getUsername());
            }
            throw new PlatformException("error.user.exists", dto.getUsername());
        }

        if (log.isTraceEnabled()) {
            log.trace("Saving new user: " + dto.toString());
        }

        try {
            // WE ONLY CONVERT TO A USER IN THIS METHOD!!!!
            User u = (User) mapperIF.map(dto, User.class);

            if (StringUtils.isBlank(u.getId())) {
                u.setPassword(passwordEncryptor.encryptPassword(u.getPassword()));
                u.setUserStatus(UserStatusCd.ACTIVE);
                u.setIdentifier(RandomStringUtils.randomAlphabetic(10));

                u = userDAO.save(u);

                // flush everything
                userDAO.flush();

                // add a basic role to the user
                Role role = roleDAO.getRoleByStatusCode("ROLE_USER");
                userRoleDAO.save(new UserRole(u, role));
            }

            // and then map back only what we want of data
            dto = (UserDto) mapperIF.map(u, UserDto.class);

            if (log.isTraceEnabled()) {
                log.trace("Saved new user successfully with id: " + u.getId());
            }

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    @Override
    public IUserDto updateUser(IUserDto dto) throws PlatformException {

        if (isUsernameTaken(dto.getUsername())) {
            if (log.isDebugEnabled()) {
                log.debug("User already exists with username: " + dto.getUsername());
            }
            throw new PlatformException("error.user.exists", dto.getUsername());
        }

        if (log.isTraceEnabled()) {
            log.trace("Saving new user: " + dto.toString());
        }

        try {
            User u = (User) mapperIF.map(dto, User.class);

            u = userDAO.update(u);

            // and then map back only what we want of data
            dto = (UserDto) mapperIF.map(u, UserDto.class);

            if (log.isTraceEnabled()) {
                log.trace("Updated userdto successfully with id: " + u.getId());
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    @Override
    public void deleteUser(String id) throws PlatformException {
        try {
            userDAO.delete(id);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e.getParams());
        }
    }

    @Override
    public List<IUserRoleDto> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IUserRoleDto> result = null;

        if (StringUtils.isBlank(id)) {
            log.error("id is null");
            throw new PlatformException("error.missing.argument.exception", "id");
        }
        try {
            List<UserRole> roles = userRoleDAO.searchForUserRoles(id, name, index, maxResults);

            if (roles != null) {
                result = new ArrayList<IUserRoleDto>();

                for (UserRole role : roles) {
                    result.add((IUserRoleDto) mapperIF.map(role, IUserRoleDto.class));
                }
            }
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer getUserRolesCount(String id, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(id)) {
            log.error("id is null");
            throw new PlatformException("error.missing.argument.exception", "id");
        }
        try {
            result = userRoleDAO.searchForUserRolesCount(id, name);
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IUserRoleDto saveOrUpdateUserRole(IUserRoleDto ur) throws PlatformException {
        if (ur == null) {
            log.error("userRole entity is null");
            throw new PlatformException("error.missing.argument.exception", "userRole");
        }
        try {

            UserRole result = (UserRole) mapperIF.map(ur, UserRole.class);

            result = userRoleDAO.saveOrUpdate(result);

            if (result != null) {
                ur = (IUserRoleDto) mapperIF.map(result, IUserRoleDto.class);

            }
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return ur;
    }

    @Override
    public IUserRoleDto deleteUserRole(String userRoleId) throws PlatformException {
        IUserRoleDto result = null;

        if (StringUtils.isBlank(userRoleId)) {
            log.error("userRoleId is null");
            throw new PlatformException("error.missing.argument.exception", "userRoleId");
        }
        try {
            UserRole ur = userRoleDAO.get(UserRole.class, userRoleId);

            if (ur != null) {
                // convert before deleting so we can return the deleted dto
                result = (IUserRoleDto) mapperIF.map(ur, IUserRoleDto.class);

                userRoleDAO.delete(ur);
            } else {
                log.error("Cannot delete a user role entity with ID: " + userRoleId);
            }
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IUserRoleDto> addUserRoles(String userId, List<String> roles) throws PlatformException {
        List<IUserRoleDto> result = null;

        if (StringUtils.isBlank(userId)) {
            log.error("userId is null");
            throw new PlatformException("error.missing.argument.exception", "userId");
        }

        IUserDto u = getUser(userId);

        if (u != null) {
            if (roles != null) {
                result = new ArrayList<IUserRoleDto>();
                for (String roleId : roles) {
                    result.add(addUserRole(userId, roleId));
                }
            }
        } else {
            log.error("Can't find user with id: " + userId);
        }

        return result;
    }

    /**
     *
     * @param userId
     * @param roleId
     * @return
     * @throws PlatformException
     */
    @Override
    public IUserRoleDto addUserRole(String userId, String roleId) throws PlatformException {
        IUserRoleDto result = null;

        try {
            User u = userDAO.get(User.class, userId);
            UserRole ur = userRoleDAO.getUserRoleByUserIdAndRoleId(userId, roleId);

            if (ur == null) {
                Role role = roleDAO.get(Role.class, roleId);

                if (role != null) {
                    log.info("Adding new role to user id: " + userId + ". Role id: " + roleId);
                    ur = userRoleDAO.save(new UserRole(u, role));
                } else {
                    log.error("Can't find role with id: " + roleId);
                }
            } else {
                log.info("Role with id: " + roleId + " already exists for user with id: " + userId);
            }

            result = (IUserRoleDto) mapperIF.map(ur, IUserRoleDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     *
     * @param id
     * @param iconName
     * @param icon
     * @param size
     * @return
     * @throws PlatformException
     */
    @Override
    public IUserDto addUserIcon(String id, String iconName, InputStream icon, IconSizeCd size) throws PlatformException {
        IUserDto result = null;

        try {

            User user = userDAO.get(User.class, id);

            if (user != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = USERS + "/" + user.getId() + "/";
                    InputStream resize = null;
                    Image image = user.getUserInfo().getImage();

                    if (image == null) {
                        image = new Image();
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("Creating " + size.name() + " icon for user. Resizing if necessary...");
                    }
                    switch (size) {
                        case LARGE:
                            resize = imageResizer.resize(icon, largeIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.LARGE.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setLargeIconUrl(iconPath);
                            break;
                        case MEDIUM:
                            resize = imageResizer.resize(icon, mediumIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.MEDIUM.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setMediumIconUrl(iconPath);
                            break;
                        case SMALL:
                            resize = imageResizer.resize(icon, smallIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.SMALL.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setSmallIconUrl(iconPath);
                            break;
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("New icon uploaded with path: " + iconPath);
                    }

                    user.getUserInfo().setImage(image);
                    userDAO.update(user);

                    result = (IUserDto) mapperIF.map(user, IUserDto.class);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find user with id: " + id);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRoleDto getRoleByStatusCode(String statusCode) throws PlatformException {
        IRoleDto result = null;

        if (StringUtils.isBlank(statusCode)) {
            log.error("statusCode is null");
            throw new PlatformException("error.missing.argument.exception", "statusCode");
        }

        try {
            Role role = roleDAO.getRoleByStatusCode(statusCode);

            if (role != null) {
                // convert to dto here
                result = (IRoleDto) mapperIF.map(role, RoleDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRoleDto saveOrUpdateRole(IRoleDto role) throws PlatformException {
        if (role == null) {
            log.error("role is null");
            throw new PlatformException("error.missing.argument.exception", "role");
        }

        try {
            // make sure the status code is all upper case
            role.setStatusCode(StringUtils.upperCase(role.getStatusCode()));

            // convert to entity here
            Role r = (Role) mapperIF.map(role, Role.class);

            // save entity
            r = roleDAO.saveOrUpdate(r);

            // convert back to dto here
            role = (IRoleDto) mapperIF.map(r, RoleDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return role;
    }

    @Override
    public List<IRoleDto> searchForRoles(String name, Integer index, Integer maxResults) throws PlatformException {
        List<IRoleDto> result = null;

        try {
            List<Role> list = roleDAO.searchForRoles(name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IRoleDto>();

                for (Role role : list) {
                    result.add((IRoleDto) mapperIF.map(role, RoleDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForRolesCount(String name) throws PlatformException {
        Integer result = null;

        try {
            result = roleDAO.searchForRolesCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRoleDto getRole(String id) throws PlatformException {
        IRoleDto result = null;

        if (StringUtils.isBlank(id)) {
            log.error("roleId is null");
            throw new PlatformException("error.missing.argument.exception", "roleId");
        }

        try {
            Role r = roleDAO.get(Role.class, id);

            if (r != null) {
                // convert to dto here
                result = (IRoleDto) mapperIF.map(r, RoleDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * This is a pretty big role. Super Super user should be able to execute this
     * as it will change permissions across many entities
     *
     * @param roleId
     * @return
     * @throws PlatformException
     */
    @Override
    public IRoleDto deleteRole(String roleId) throws PlatformException {
        IRoleDto result = null;

        if (StringUtils.isBlank(roleId)) {
            log.error("roleId is null");
            throw new PlatformException("error.missing.argument.exception", "roleId");
        }

        try {
            Role r = roleDAO.get(Role.class, roleId);

            if (r != null) {
                // first we need to delete all constraining entities
                List<ApplicationRole> appRoles = applicationRoleDAO.getApplicationRolesByRoleId(roleId);
                List<UserRole> userRoles = userRoleDAO.getUserRolesByRoleId(roleId);
                List<OrganizationUserRole> orgUserRoles = organizationUserRoleDAO.getOrganizationUserRolesByRoleId(roleId);

                if (appRoles != null && appRoles.size() > 0) {
                    applicationRoleDAO.deleteAll(appRoles);
                }
                if (userRoles != null && userRoles.size() > 0) {
                    userRoleDAO.deleteAll(userRoles);
                }
                if (orgUserRoles != null && orgUserRoles.size() > 0) {
                    organizationUserRoleDAO.deleteAll(orgUserRoles);
                }

                // convert to dto here
                result = (IRoleDto) mapperIF.map(r, RoleDto.class);

                // now delete the role itself
                roleDAO.delete(r);
                roleDAO.flush();
            } else {
                log.error("Role with id: " + roleId + " does not exist");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IUserDto> getLastModifiedUsers(Integer maxResults) throws PlatformException {
        List<IUserDto> result = null;

        try {
            List<User> list = userDAO.getLastModifiedUsers(maxResults);

            if (list != null) {
                result = new ArrayList<IUserDto>();

                for (User u : list) {
                    result.add((IUserDto) mapperIF.map(u, UserDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IRoleDto> getLastModifiedRoles(Integer maxResults) throws PlatformException {
        List<IRoleDto> result = null;

        try {
            List<Role> list = roleDAO.getLastModifiedRoles(maxResults);

            if (list != null) {
                result = new ArrayList<IRoleDto>();

                for (Role r : list) {
                    result.add((IRoleDto) mapperIF.map(r, RoleDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRoleDto createRole() {
        return new RoleDto();
    }

    @Override
    public IUserDto createUser() {
        return new UserDto();
    }

    // Spring IoC
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private ApplicationRoleDAO applicationRoleDAO;

    @Autowired
    private OrganizationUserRoleDAO organizationUserRoleDAO;

    @Autowired
    private MapperIF mapperIF;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private CmsService cmsService;

    @Autowired
    private ImageResizer imageResizer;

    private Integer largeIconWidth;
    private Integer mediumIconWidth;
    private Integer smallIconWidth;

    public void setLargeIconWidth(Integer largeIconWidth) {
        this.largeIconWidth = largeIconWidth;
    }

    public void setMediumIconWidth(Integer mediumIconWidth) {
        this.mediumIconWidth = mediumIconWidth;
    }

    public void setSmallIconWidth(Integer smallIconWidth) {
        this.smallIconWidth = smallIconWidth;
    }
}
