package com.dd.direkt.shared_kernel.domain.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final long id;
    private final long creatorId;

    public CustomUserDetails(
            long id,
            long creatorId,
            String username,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.creatorId = creatorId;
    }
}
