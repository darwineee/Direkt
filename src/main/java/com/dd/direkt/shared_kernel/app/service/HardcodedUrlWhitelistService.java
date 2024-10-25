package com.dd.direkt.shared_kernel.app.service;

import org.springframework.stereotype.Service;

@Service
public class HardcodedUrlWhitelistService implements UrlWhitelistService {
    @Override
    public String[] getList() {
        return new String[]{
                //for swagger
                "/swagger-ui/**",
                "/*/api-docs/**",
                "/swagger-resources/**",
                //for api
                "/api/*/*/login",
                "/api/*/*/signup",
                "/api/*/*/confirm",
                //for ws handshake
                "/ws/*/message",
                //for resources
                "/*.ico",
                "/*.html",
                "/*.js",
                "/*.css",
        };
    }
}
