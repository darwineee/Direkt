package com.dd.direkt.customer.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerLoginResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private String token;
}
