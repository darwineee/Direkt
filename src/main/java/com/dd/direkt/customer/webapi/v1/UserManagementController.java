package com.dd.direkt.customer.webapi.v1;

import com.dd.direkt.customer.app.dto.CreateUserRequest;
import com.dd.direkt.customer.app.dto.UserInfoResponse;
import com.dd.direkt.customer.app.service.UserManagementService;
import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/user-management")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping("/users")
    ResponseEntity<Page<UserInfoResponse>> getUsers(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,

            @ParameterObject
            @PageableDefault(size = 20)
            Pageable pageable,

            @ParameterObject
            AccountPagingFilter filter
    ) {
        filter.setCustomerId(userDetails.getId());
        var data = userManagementService.findAllUsers(
                pageable,
                filter
        );
        return ResponseEntity.ok(data);
    }

    @PostMapping("/users")
    ResponseEntity<Void> createUser(
            @RequestBody
            CreateUserRequest request,

            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        userManagementService.createUser(
                request,
                userDetails.getId()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    ResponseEntity<Void> changeUserEnabledStatus(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "enabled") boolean enabled
    ) {
        userManagementService.changeUserEnabledStatus(id, enabled);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<Void> deleteUser(
            @PathVariable(name = "id") long id
    ) {
        userManagementService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }
}
