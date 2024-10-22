package com.dd.direkt.admin.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private String token;
}
