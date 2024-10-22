package com.dd.direkt.admin.webapi.v1;

import com.dd.direkt.admin.app.dto.AdminLoginResponse;
import com.dd.direkt.admin.app.mapper.AdminMapper;
import com.dd.direkt.shared_kernel.app.dto.LoginRequest;
import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final BasicAuthService basicAuthService;
    private final AdminMapper mapper;

    @PostMapping("/login")
    ResponseEntity<AdminLoginResponse> login(
            @RequestBody
            @Valid
            LoginRequest loginRequest
    ) {
        var loginRsp = basicAuthService.login(
                loginRequest,
                List.of(UserRole.Admin)
        );
        var adminLoginRsp = mapper.toAdminLoginRsp(loginRsp);
        return ResponseEntity.ok(adminLoginRsp);
    }
}
