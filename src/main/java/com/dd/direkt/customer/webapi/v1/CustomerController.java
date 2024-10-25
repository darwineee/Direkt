package com.dd.direkt.customer.webapi.v1;

import com.dd.direkt.customer.app.dto.CustomerInfoResponse;
import com.dd.direkt.customer.app.dto.UpdateCustomerRequest;
import com.dd.direkt.customer.app.service.CustomerService;
import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/info")
    ResponseEntity<CustomerInfoResponse> getSelfInfo(
            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        CustomerInfoResponse response = customerService.getInfo(userDetails.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info")
    ResponseEntity<CustomerInfoResponse> updateSelfInfo(
            @RequestBody
            @Valid
            UpdateCustomerRequest request,
            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        CustomerInfoResponse response = customerService.updateInfo(request, userDetails.getId());
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/suspend")
    ResponseEntity<Void> requestSuspendAccount(
            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        customerService.suspendAccount(userDetails.getId());
        return ResponseEntity.accepted().build();
    }
}
