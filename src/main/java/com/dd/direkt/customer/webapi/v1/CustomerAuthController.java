package com.dd.direkt.customer.webapi.v1;

import com.dd.direkt.customer.app.dto.CustomerLoginResponse;
import com.dd.direkt.customer.app.dto.CustomerSignUpRequest;
import com.dd.direkt.customer.app.mapper.CustomerMapper;
import com.dd.direkt.shared_kernel.app.dto.LoginRequest;
import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerAuthController {
    private final BasicAuthService basicAuthService;
    private final CustomerMapper mapper;

    @PostMapping("/login")
    ResponseEntity<CustomerLoginResponse> login(
            @RequestBody
            @Valid
            LoginRequest loginRequest
    ) {
        var loginRsp = basicAuthService.login(
                loginRequest,
                List.of(UserRole.Customer)
        );
        var customerLoginRsp = mapper.toCustomerLoginRsp(loginRsp);
        return ResponseEntity.ok(customerLoginRsp);
    }

    @PostMapping("/signup")
    ResponseEntity<Void> signup(
            @RequestBody
            @Valid
            CustomerSignUpRequest request
    ) {
        var signUpRequest = mapper.toSignUpRequest(request);
        basicAuthService.signUp(
                signUpRequest,
                List.of(UserRole.Customer)
        );
        return ResponseEntity.ok().build();
    }
}
