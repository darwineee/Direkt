package com.dd.direkt.shared_kernel.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private String token;
    private Long customerId;
}
