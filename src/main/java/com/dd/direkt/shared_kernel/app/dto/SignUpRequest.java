package com.dd.direkt.shared_kernel.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    String name;
    @NotBlank
    String email;
    @NotBlank
    String password;
    Long customerId;
}
