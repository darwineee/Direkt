package com.dd.direkt.customer.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerInfoResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
}
