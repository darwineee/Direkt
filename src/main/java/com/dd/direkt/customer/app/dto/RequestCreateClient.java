package com.dd.direkt.customer.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCreateClient(
        @NotBlank
        String name,
        @Size(min = 36, max = 36)
        String apiKey
) {
}
