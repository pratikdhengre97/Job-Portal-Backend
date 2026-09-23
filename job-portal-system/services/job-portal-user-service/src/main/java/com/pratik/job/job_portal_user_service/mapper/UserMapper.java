package com.pratik.job.job_portal_user_service.mapper;


import com.pratik.job.job_portal_user_service.model.User;
import com.pratik.job.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse toDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setProfileImage(user.getProfileImage());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());


        return dto;
    }
    public static List<UserResponse> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

}
