package com.dd.direkt.shared_kernel.app.service;

import com.dd.direkt.shared_kernel.domain.entity.Role;
import com.dd.direkt.shared_kernel.domain.repository.AccountDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountDetailRepository accountDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var accountDetail = accountDetailRepository
                .findDetailByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var authorities = accountDetail.getRoles()
                .stream()
                .map(Role::getRole)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return User.builder()
                .username(accountDetail.getEmail())
                .password(accountDetail.getPassword())
                .disabled(!accountDetail.isEnabled())
                .authorities(authorities)
                .build();
    }
}
