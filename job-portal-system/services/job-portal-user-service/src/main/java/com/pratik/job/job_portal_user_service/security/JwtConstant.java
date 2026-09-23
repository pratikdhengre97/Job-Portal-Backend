package com.pratik.job.job_portal_user_service.security;

public class JwtConstant {

//    public static final String SECRET_KEY = ${JWT_SECRET};

    public static final String SECRET_KEY = System.getenv("JWT_SECRET");
}
