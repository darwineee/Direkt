package com.dd.direkt.management.app.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateUser(
        @NotBlank
        String name,
        @NotBlank
        String email
) {
}
