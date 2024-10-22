package com.dd.direkt.user.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private String token;
    private long customerId;
}
