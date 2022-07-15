package com.hxcel.globalhealth.calendar.spec;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 26, 2008
 * Time: 5:06:50 PM
 * Responsibility: Chandler server user manager API. Chandler needs it's own users and as such we need to
 * duplicate our users with the user with Chandler.
 */
public interface CmpService {
    
    List<CalendarUser> getUsers(String name, CalendarSortType sortBy, CalendarSortOrder orderBy, Integer index, Integer maxResults) throws CalendarException;
    CalendarUser getUser(String username) throws CalendarException;
    void createUser(String username, String firstName, String lastName,
                            String password, String email) throws CalendarException;
    void updateUser(String currentUsername, String username, String firstName, String lastName,
                            String password, String email) throws CalendarException;
    //void deleteUsers(List<String> username) throws CalendarException;
    void deleteUser(String username) throws CalendarException;
    //void activateUser(String username) throws CalendarException;
    Integer getUserCount() throws CalendarException;
    //List<SpaceUsage> getAggregateSpaceUsage() throws CalendarException;
    //SpaceUsage getSpaceUsage(String username) throws CalendarException;
    UserServiceDocument getUserServiceDocument(String username) throws CalendarException;
}
