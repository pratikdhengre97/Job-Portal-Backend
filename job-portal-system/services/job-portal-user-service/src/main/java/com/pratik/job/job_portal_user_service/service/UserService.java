package com.pratik.job.job_portal_user_service.service;


import com.pratik.job.job_portal_user_service.model.User;
import com.pratik.job.job_portal_user_service.payload.UpdateUserRequest;
import com.pratik.job.response.UserResponse;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws Exception;

    User getUserById(Long id) throws Exception;

    List<User> getAllUsers();

    UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception;

    //admin action
    UserResponse suspendUser(Long id) throws Exception;
    UserResponse activateUser(Long id) throws Exception;

    UserResponse deleteUser(Long id) throws Exception;
}
