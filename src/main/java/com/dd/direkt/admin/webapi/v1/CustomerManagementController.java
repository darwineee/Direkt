package com.dd.direkt.admin.webapi.v1;

import com.dd.direkt.admin.app.dto.CustomerInfoResponse;
import com.dd.direkt.admin.app.service.CustomerManagementService;
import com.dd.direkt.shared_kernel.app.dto.SignUpRequest;
import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/customer-management")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CustomerManagementController {

    private final CustomerManagementService customerManagementService;
    private final BasicAuthService basicAuthService;

    @GetMapping("/customers")
    ResponseEntity<Page<CustomerInfoResponse>> getCustomers(
            @ParameterObject
            @PageableDefault(size = 20)
            Pageable pageable,

            @ParameterObject
            AccountPagingFilter filter
    ) {
        var data = customerManagementService.getCustomers(pageable, filter);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/customers/{id}")
    ResponseEntity<CustomerInfoResponse> getCustomer(
            @PathVariable("id") long id
    ) {
        var data = customerManagementService.getCustomerInfo(id);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/customers")
    ResponseEntity<Void> createCustomer(
            @RequestBody
            @Valid
            SignUpRequest request
    ) {
        basicAuthService.signUp(
                request,
                List.of(UserRole.Customer)
        );
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/customers/{id}")
    ResponseEntity<Void> deleteCustomer(
            @PathVariable("id") long id
    ) {
        customerManagementService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }
}
