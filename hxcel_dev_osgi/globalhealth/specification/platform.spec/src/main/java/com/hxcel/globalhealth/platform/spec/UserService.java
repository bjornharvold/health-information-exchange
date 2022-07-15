package com.hxcel.globalhealth.platform.spec;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;

import java.util.List;
import java.io.InputStream;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 4:01:28 PM
 * Responsibility:
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;

    IUserDto getUser(String username, String password) throws PlatformException;

    IUserDto getUser(String id) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deactivateUser(String userId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void activateUser(String userId) throws PlatformException;

    Boolean isUserUnique(String userId, String username) throws PlatformException;

    IUserDto getUserByUsername(String username) throws PlatformException;

    Boolean isUsernameTaken(String username) throws PlatformException;

    List<IUserDto> searchForUsers(String criteria, Integer index, Integer maxResults) throws PlatformException;

    List<IUserDto> getUsers(String name, Integer index, Integer maxResults) throws PlatformException;

    Integer getUserCount(String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserDto saveNewUser(IUserDto dto) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserDto updateUser(IUserDto dto) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteUser(String id) throws PlatformException;

    List<IUserRoleDto> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer getUserRolesCount(String id, String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserRoleDto saveOrUpdateUserRole(IUserRoleDto ur) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserRoleDto deleteUserRole(String userRoleId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<IUserRoleDto> addUserRoles(String userId, List<String> roles) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserRoleDto addUserRole(String userId, String roleId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IUserDto addUserIcon(String id, String iconName, InputStream icon, IconSizeCd size) throws PlatformException;

    IRoleDto getRoleByStatusCode(String statusCode) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRoleDto saveOrUpdateRole(IRoleDto role) throws PlatformException;

    List<IRoleDto> searchForRoles(String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForRolesCount(String name) throws PlatformException;

    IRoleDto getRole(String id) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRoleDto deleteRole(String roleId) throws PlatformException;

    List<IUserDto> getLastModifiedUsers(Integer maxResults) throws PlatformException;

    List<IRoleDto> getLastModifiedRoles(Integer maxResults) throws PlatformException;

    IRoleDto createRole();

    IUserDto createUser();
}
