package com.pratik.job.job_portal_user_service.service.impl;

import com.pratik.job.domain.UserRole;
import com.pratik.job.domain.UserStatus;
import com.pratik.job.job_portal_user_service.mapper.UserMapper;
import com.pratik.job.job_portal_user_service.model.User;
import com.pratik.job.job_portal_user_service.payload.AuthResponse;
import com.pratik.job.job_portal_user_service.repository.UserRepository;
import com.pratik.job.job_portal_user_service.security.CustomUserDetailsService;
import com.pratik.job.job_portal_user_service.service.AuthService;
import com.pratik.job.job_portal_user_service.service.JwtProvider;
import com.pratik.job.job_portal_user_service.payload.LoginRequest;
import com.pratik.job.job_portal_user_service.payload.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class  AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;



    @Override
    public AuthResponse signup(SignupRequest req) throws Exception {

        if(userRepository.existsByEmail(req.getEmail())) {
            throw new Exception("Email already registered : " + req.getEmail());
        }

        if(req.getRole() == UserRole.ROLE_ADMIN) {
            throw new Exception("Cannot self register as a role admin");
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .lastLogin(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        Authentication authentication  = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome " + savedUser.getFullName());
        res.setMessage("Registered successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(savedUser));


        return res;
    }

    @Override
    public AuthResponse login(LoginRequest req) throws Exception {


        Authentication authentication  = authenticate(
                req.getEmail(), req.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(req.getEmail());

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome back " + user.getFullName());
        res.setMessage("Login successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));

        return res;
    }

    private Authentication authenticate(String email, String password) throws Exception {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails == null) {
            throw new Exception("User not found with email "+ email);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
