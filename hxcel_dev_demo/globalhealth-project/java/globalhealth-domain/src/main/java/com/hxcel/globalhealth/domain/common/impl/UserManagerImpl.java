/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.impl;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.emr.model.Emr;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientTypeCd;
import com.hxcel.globalhealth.domain.patient.dao.PatientDAO;
import com.hxcel.globalhealth.domain.utils.springsecurity.UserDetailsImpl;
import com.hxcel.globalhealth.domain.utils.springsecurity.DataAccessExceptionImpl;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.dao.OrganizationDAO;
import com.hxcel.globalhealth.domain.platform.dao.OrganizationUserDAO;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.OrganizationUser;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.phr.dao.PersonalProfileDAO;
import com.hxcel.globalhealth.domain.phr.model.PersonalProfile;
import com.hxcel.globalhealth.domain.phr.model.Phr;
import com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.common.dao.UserDAO;
import com.hxcel.globalhealth.domain.common.dao.RoleDAO;
import com.hxcel.globalhealth.domain.common.dao.UserRoleDAO;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.Image;
import com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd;
import com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd;
import com.hxcel.globalhealth.service.cms.CmsException;
import com.hxcel.globalhealth.service.cms.CmsService;
import com.hxcel.globalhealth.utils.image.ImageResizer;
import net.sf.dozer.util.mapping.MapperIF;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.LockMode;
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
public class UserManagerImpl implements UserManager {
    private final static Logger log = LoggerFactory.getLogger(UserManagerImpl.class);
    private static final String USERS = "/content/users";

    /**
     * This method is for spring security
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserDetails result = null;
        User u = null;

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
        } catch (DomainException e) {
            throw new DataAccessExceptionImpl(username + " cannot be found");
        }

        return result;
    }

    /**
     * Grabs the user by username first as there is no way to recreate the encrypted password.
     * If the user exists we check the password. And if they match we return the user.
     *
     * @param username
     * @param password
     * @return
     * @throws DomainException
     */
    public User getUser(String username, String password) throws DomainException {
        User result = null;

        try {
            result = userDAO.getUserByUsername(username);

            if (result != null) {
                StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
                if (!spe.checkPassword(password, result.getPassword())) {
                    result = null;
                }
            }

        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public User getUserByUsername(String username) throws DomainException {
        User result = null;

        try {
            result = userDAO.getUserByUsername(username);

        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public UserDto getUserDto(String username, String password) throws DomainException {
        if (StringUtils.isBlank(username)) {
            throw new DomainException("error.missing.data", username);
        }
        if (StringUtils.isBlank(password)) {
            throw new DomainException("error.missing.data", password);
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
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public UserDto getUserDtoById(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", id);
        }

        UserDto result = null;

        try {
            User u = userDAO.get(User.class, id);

            if (u != null) {
                result = (UserDto) mapperIF.map(u, UserDto.class);
            }

        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public void deactivateUser(String userId) throws DomainException {
        try {
            userDAO.deactivateUser(userId);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public void activateUser(String userId) throws DomainException {
        try {
            userDAO.activateUser(userId);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public Boolean isUserUnique(String userId, String username) throws DomainException {
        Boolean result;
        try {
            User u = userDAO.isUserUnique(userId, username);
            result = u == null;
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public UserDto getUserDtoByUsername(String username) throws DomainException {
        UserDto result = null;

        try {
            if (StringUtils.isNotBlank(username)) {
                User u = userDAO.getUserByUsername(username);
                if (u != null) {
                    result = (UserDto) mapperIF.map(u, UserDto.class);
                }
            } else {
                throw new DomainException("error.missing.data", "username");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public Boolean isUsernameTaken(String username) throws DomainException {
        Boolean result = false;

        if (log.isTraceEnabled()) {
            log.trace("Checking if username " + username + " is taken");
        }
        User u = getUserByUsername(username);
        if (u != null) {
            result = true;
        }
        if (log.isTraceEnabled()) {
            log.trace("Username taken: " + result);
        }

        return result;
    }

    public User findUserById(String id) throws DomainException {
        try {
            return userDAO.findById(id, false);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<User> searchForUsers(String criteria, Integer index, Integer maxResults) throws DomainException {
        List<User> result = null;
        if (StringUtils.isBlank(criteria)) {
            throw new DomainException("search criteria cannot be null");
        }

        // TODO implement

        return result;
    }

    public List<User> getUsers(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return userDAO.getUsers(name, index, maxResults);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public Integer getUserCount(String name) throws DomainException {
        try {
            return userDAO.getUserCount(name);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public User getUser(String id) throws DomainException {
        try {
            return userDAO.get(User.class, id);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public User getUser(String id, LockMode lockMode) throws DomainException {
        try {
            return userDAO.get(User.class, id, lockMode);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public UserDto loadUser(String id) throws DomainException {
        UserDto result = null;

        try {
            if (StringUtils.isNotBlank(id)) {
                User u = userDAO.get(User.class, id);
                if (u != null) {
                    result = (UserDto) mapperIF.map(u, UserDto.class);
                }
            } else {
                throw new DomainException("error.missing.data", "id");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public User loadUser(String id, LockMode lockMode) throws DomainException {
        try {
            return userDAO.load(User.class, id, lockMode);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public User saveOrUpdateUser(User entity) throws DomainException {
        try {
            return userDAO.saveOrUpdate(entity);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
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
     * @throws DomainException
     */
    public UserDto saveNewUserDto(UserDto dto) throws DomainException {

        if (isUsernameTaken(dto.getUsername())) {
            if (log.isDebugEnabled()) {
                log.debug("User already exists with username: " + dto.getUsername());
            }
            throw new DomainException("error.user.exists", dto.getUsername());
        }

        if (log.isTraceEnabled()) {
            log.trace("Saving new user: " + dto.toString());
        }

        // WE ONLY CONVERT TO A USER IN THIS METHOD!!!!
        User u = (User) mapperIF.map(dto, User.class);

        u = saveNewUser(u);

        // and then map back only what we want of data
        dto = (UserDto) mapperIF.map(u, UserDto.class);

        if (log.isTraceEnabled()) {
            log.trace("Saved new user successfully with id: " + u.getId());
        }


        return dto;
    }

    public UserDto updateUserDto(UserDto dto) throws DomainException {

        if (isUsernameTaken(dto.getUsername())) {
            if (log.isDebugEnabled()) {
                log.debug("User already exists with username: " + dto.getUsername());
            }
            throw new DomainException("error.user.exists", dto.getUsername());
        }

        if (log.isTraceEnabled()) {
            log.trace("Saving new user: " + dto.toString());
        }

        User u = (User) mapperIF.map(dto, User.class);

        u = saveOrUpdateUser(u);

        // and then map back only what we want of data
        dto = (UserDto) mapperIF.map(u, UserDto.class);

        if (log.isTraceEnabled()) {
            log.trace("Updated userdto successfully with id: " + u.getId());
        }

        return dto;
    }

    /**
     * This method only gets called once when the user is registering
     * We are assuming that the username, unencrypted password and preferred country has already been set
     *
     * @param u
     * @return
     * @throws DomainException
     */
    public User saveNewUser(User u) throws DomainException {
        try {
            if (StringUtils.isBlank(u.getId())) {
                u.setPassword(passwordEncryptor.encryptPassword(u.getPassword()));
                u.setUserStatus(UserStatusCd.ACTIVE);
                u.setSecurityCode(RandomStringUtils.randomAlphabetic(10));

                u = userDAO.save(u);

                // flush everything
                userDAO.flush();

                // add a basic role to the user
                Role role = roleDAO.getRoleByStatusCode("ROLE_USER");
                userRoleDAO.save(new UserRole(u, role));

                // associate the user with our organization entity
                // note that all users have a relationship with HXCEL ;-)
                Organization organization = organizationDAO.getHXCELOrganization();

                // update org
                organizationUserDAO.save(new OrganizationUser(organization, u));

                // create the primary patient record for this user
                Patient p = new Patient();
                p.setPatientStatus(PatientStatusCd.ACTIVE);
                p.setPatientType(PatientTypeCd.PRIMARY);

                Phr phr = new Phr();
                phr.setPatient(p);
                p.setPhr(phr);

                Emr emr = new Emr();
                emr.setPatient(p);
                p.setEmr(emr);
                p = patientDAO.save(p);

                // associate user and patient entities
                relationshipManager.createRelationshipNow(u.getId(), p.getId(), EntityTypeCd.USER, EntityTypeCd.PATIENT, null);

                // create personal profile as it's the only 1 - 1 mapping in the PHR
                PersonalProfile personalProfile = new PersonalProfile();
                personalProfile.setRecordType(RecordTypeCd.PHR);
                personalProfile.setPhrType(PhrTypeCd.PERSONAL_PROFILE);

                // default to false - patient can change this in the UI
                personalProfile.setEmergency(false);
                personalProfile.setRecordStatus(RecordStatusCd.ACTIVE);
                personalProfile.setRecordCreatorId(p.getId());
                personalProfile.setRecordCreatorType(EntityTypeCd.PATIENT);
                personalProfile.setPhr(p.getPhr());
                personalProfileDAO.save(personalProfile);
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return u;
    }

    public User updateUser(User entity) throws DomainException {
        try {
            return userDAO.update(entity);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public void deleteUser(User entity) throws DomainException {
        try {
            userDAO.delete(entity);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public void deleteUser(String id) throws DomainException {
        try {
            userDAO.delete(id);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws DomainException {
        List<UserRole> result = null;

        if (StringUtils.isBlank(id)) {
            log.error("id is null");
            throw new DomainException("error.missing.argument.exception", "id");
        }
        try {
            result = userRoleDAO.searchForUserRoles(id, name, index, maxResults);
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer getUserRolesCount(String id, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(id)) {
            log.error("id is null");
            throw new DomainException("error.missing.argument.exception", "id");
        }
        try {
            result = userRoleDAO.searchForUserRolesCount(id, name);
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public UserRole saveOrUpdateUserRole(UserRole ur) throws DomainException {
        if (ur == null) {
            log.error("userRole entity is null");
            throw new DomainException("error.missing.argument.exception", "userRole");
        }
        try {
            ur = userRoleDAO.saveOrUpdate(ur);
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ur;
    }

    public UserRole deleteUserRole(String userRoleId) throws DomainException {
        UserRole result = null;

        if (StringUtils.isBlank(userRoleId)) {
            log.error("userRoleId is null");
            throw new DomainException("error.missing.argument.exception", "userRoleId");
        }
        try {
            result = userRoleDAO.get(UserRole.class, userRoleId);

            if (result != null) {
                userRoleDAO.delete(result);
            } else {
                log.error("Cannot delete a user role entity with ID: " + userRoleId);
            }
        }
        catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<UserRole> addUserRoles(String userId, List<String> roles) throws DomainException {
        List<UserRole> result = null;

        if (StringUtils.isBlank(userId)) {
            log.error("userId is null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        User u = getUser(userId);

        if (u != null) {
            if (roles != null) {
                result = new ArrayList<UserRole>();
                for (String roleId : roles) {
                    result.add(addUserRole(userId, roleId));
                }
            }
        } else {
            log.error("Can't find user with id: " + userId);
        }

        return result;
    }

    public UserRole addUserRole(String userId, String roleId) throws DomainException {
        UserRole result = null;

        try {
            User u = getUser(userId);
            result = userRoleDAO.getUserRoleByUserIdAndRoleId(userId, roleId);

            if (result == null) {
                Role role = roleDAO.get(Role.class, roleId);

                if (role != null) {
                    log.info("Adding new role to user id: " + userId + ". Role id: " + roleId);
                    result = userRoleDAO.save(new UserRole(u, role));
                } else {
                    log.error("Can't find role with id: " + roleId);
                }
            } else {
                log.info("Role with id: " + roleId + " already exists for user with id: " + userId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public User addUserIcon(String id, String iconName, InputStream icon, IconSizeCd size) throws DomainException {
        User result = null;

        try {

            result = getUser(id);

            if (result != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = USERS + "/" + result.getId() + "/";
                    InputStream resize = null;
                    Image image = result.getUserInfo().getImage();

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

                    result.getUserInfo().setImage(image);
                    userDAO.update(result);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find user with id: " + id);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private PersonalProfileDAO personalProfileDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private OrganizationUserDAO organizationUserDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private MapperIF mapperIF;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private RelationshipManager relationshipManager;

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
