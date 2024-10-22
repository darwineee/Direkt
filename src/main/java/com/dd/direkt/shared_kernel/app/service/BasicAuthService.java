package com.dd.direkt.shared_kernel.app.service;

import com.dd.direkt.shared_kernel.app.dto.LoginRequest;
import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.app.dto.SignUpRequest;
import com.dd.direkt.shared_kernel.domain.type.UserRole;

import java.util.List;

public interface BasicAuthService {
    LoginResponse login(LoginRequest loginRequest, List<UserRole> roles);
    void signUp(SignUpRequest signUpRequest, List<UserRole> roles);
    void confirm(long id, String token);
}
