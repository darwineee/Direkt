package com.dd.direkt.customer.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    String name;
    @NotBlank
    String email;
    @NotBlank
    String password;
}
