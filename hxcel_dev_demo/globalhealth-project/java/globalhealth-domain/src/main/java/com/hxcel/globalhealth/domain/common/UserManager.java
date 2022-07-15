/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import org.hibernate.LockMode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.userdetails.UserDetailsService;

import java.util.List;
import java.io.InputStream;

/**
 * User: Bjorn Harvold
 * Date: Jul 6, 2007
 * Time: 7:10:02 PM
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UserManager extends UserDetailsService {
    User getUser(String username, String password) throws DomainException;
    User getUserByUsername(String username) throws DomainException;
    UserDto getUserDtoById(String id) throws DomainException;
    UserDto updateUserDto(UserDto user) throws DomainException;
    UserDto getUserDto(String username, String password) throws DomainException;

    void deactivateUser(String userId) throws DomainException;

    void activateUser(String userId) throws DomainException;

    Boolean isUserUnique(String userId, String username) throws DomainException;

    UserDto getUserDtoByUsername(String username) throws DomainException;

    Boolean isUsernameTaken(String username) throws DomainException;

    User findUserById(String id) throws DomainException;

    List<User> searchForUsers(String criteria, Integer index, Integer maxResults) throws DomainException;

    List<User> getUsers(String name, Integer index, Integer maxResults) throws DomainException;

    Integer getUserCount(String name) throws DomainException;

    User getUser(String id) throws DomainException;

    User getUser(String id, LockMode lockMode) throws DomainException;

    UserDto loadUser(String id) throws DomainException;

    User loadUser( String id, LockMode lockMode) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User saveOrUpdateUser(User entity) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    UserDto saveNewUserDto(UserDto dto) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User saveNewUser(User u) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User updateUser(User entity) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteUser(User entity) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteUser(String id) throws DomainException;

    List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws DomainException;

    Integer getUserRolesCount(String id, String name) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    UserRole saveOrUpdateUserRole(UserRole ur) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    UserRole deleteUserRole(String userRoleId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<UserRole> addUserRoles(String userId, List<String> roles) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    UserRole addUserRole(String userId, String roleId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    User addUserIcon(String id, String iconName, InputStream icon, IconSizeCd size) throws DomainException;

}
