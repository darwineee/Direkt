package com.dd.direkt.shared_kernel.domain.entity.view;

import com.dd.direkt.shared_kernel.domain.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetail {
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    private String verifyToken;
    private List<Role> roles;
}
