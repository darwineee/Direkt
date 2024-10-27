package com.dd.direkt.admin.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    String name;
    @NotBlank
    String email;
    @NotBlank
    String password;
}

