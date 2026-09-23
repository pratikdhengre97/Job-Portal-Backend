package com.pratik.job.job_portal_user_service.service.impl;

import com.pratik.job.domain.UserStatus;

import com.pratik.job.job_portal_user_service.mapper.UserMapper;
import com.pratik.job.job_portal_user_service.model.User;
import com.pratik.job.job_portal_user_service.payload.UpdateUserRequest;
import com.pratik.job.job_portal_user_service.repository.UserRepository;
import com.pratik.job.job_portal_user_service.service.UserService;
import com.pratik.job.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new Exception("User not Found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found")
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception {

        User user = getUserByEmail(email);
        if(req.getFullName() != null) {
            user.setFullName(req.getFullName());
        }
        if(req.getPhone() != null) {
            user.setPhone(req.getPhone());
        }
        if(req.getProfileImage() != null) {
            user.setProfileImage(req.getProfileImage());
        }
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse suspendUser(Long id) throws Exception {

        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }
}
