package com.dd.direkt.customer.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerSignUpRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
