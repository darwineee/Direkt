package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.app.dto.LoginRequest;
import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import com.dd.direkt.user.app.dto.UserLoginResponse;
import com.dd.direkt.user.app.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAuthController {
    private final BasicAuthService basicAuthService;
    private final UserMapper mapper;

    @PostMapping("/login")
    ResponseEntity<UserLoginResponse> login(
            @RequestBody
            @Valid
            LoginRequest request
    ) {
        var loginRsp = basicAuthService.login(
                request,
                List.of(UserRole.EndUser)
        );
        var userInfoRsp = mapper.toResponse(loginRsp);
        return ResponseEntity.ok(userInfoRsp);
    }
}
