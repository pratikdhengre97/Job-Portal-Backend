package com.pratik.job.job_portal_user_service.controller;

import com.pratik.job.job_portal_user_service.payload.AuthResponse;
import com.pratik.job.job_portal_user_service.service.AuthService;
import com.pratik.job.job_portal_user_service.payload.LoginRequest;
import com.pratik.job.job_portal_user_service.payload.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid SignupRequest req
            ) throws Exception {
        return ResponseEntity.ok(authService.signup(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest req
            ) throws Exception {
        return ResponseEntity.ok(authService.login(req));
    }


}
