package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.dto.UserInfoResponse;
import com.dd.direkt.user.app.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    ResponseEntity<UserInfoResponse> getUser(
            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        UserInfoResponse response = userService.find(userDetails.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/colleagues")
    ResponseEntity<Page<UserInfoResponse>> getUserColleagues(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,

            @ParameterObject
            @PageableDefault(size = 20)
            Pageable pageable,

            @ParameterObject
            AccountPagingFilter filter
    ) {
        filter.setCustomerId(userDetails.getCreatorId());
        var data = userService.findColleagues(pageable, filter);
        return ResponseEntity.ok(data);
    }

    @PutMapping
    ResponseEntity<UserInfoResponse> updateUser(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,

            @RequestBody
            @Valid
            UpdateUserRequest request
    ) {
        UserInfoResponse response = userService.update(request, userDetails.getId());
        return ResponseEntity.ok(response);
    }
}
