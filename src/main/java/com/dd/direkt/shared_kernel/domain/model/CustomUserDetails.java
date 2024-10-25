package com.dd.direkt.shared_kernel.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final long id;

    public CustomUserDetails(
            long id,
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
    }
}
