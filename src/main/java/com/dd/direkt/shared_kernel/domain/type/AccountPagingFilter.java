package com.dd.direkt.shared_kernel.domain.type;

import lombok.Data;

@Data
public class AccountPagingFilter {
    private Long customerId = null;
    private String email = null;
    private String name = null;
    private Boolean enabled = null;
}
