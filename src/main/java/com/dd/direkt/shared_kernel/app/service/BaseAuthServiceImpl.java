package com.dd.direkt.shared_kernel.app.service;

import com.dd.direkt.shared_kernel.app.dto.LoginRequest;
import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.app.dto.SignUpRequest;
import com.dd.direkt.shared_kernel.app.event.AccountSignedUp;
import com.dd.direkt.shared_kernel.app.mapper.AuthMapper;
import com.dd.direkt.shared_kernel.domain.entity.AccountRole;
import com.dd.direkt.shared_kernel.domain.entity.Role;
import com.dd.direkt.shared_kernel.domain.exception.AccountAlreadyConfirmed;
import com.dd.direkt.shared_kernel.domain.exception.AccountIdentityExisted;
import com.dd.direkt.shared_kernel.domain.exception.AccountNotFound;
import com.dd.direkt.shared_kernel.domain.repository.AccountRepository;
import com.dd.direkt.shared_kernel.domain.repository.AccountRoleRepository;
import com.dd.direkt.shared_kernel.domain.repository.RoleRepository;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import com.dd.direkt.shared_kernel.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseAuthServiceImpl implements BasicAuthService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final ApplicationEventPublisher eventPublisher;
    private final JwtHelper jwtHelper;
    private final AuthMapper mapper;

    @Transactional
    @Override
    public LoginResponse login(LoginRequest request, List<UserRole> roles) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var token = jwtHelper.generateToken(request.getEmail(), roles);
        var account = accountRepository
                .findByEmail(request.getEmail())
                .orElseThrow(AccountNotFound::new);
        var rsp = mapper.toLoginResponse(account);
        rsp.setToken(token);
        return rsp;
    }

    @Transactional
    @Override
    public void signUp(SignUpRequest request, List<UserRole> roles) {
        if (accountRepository.existsByEmail(request.getEmail())) throw new AccountIdentityExisted();
        var account = mapper.toAccount(request);
        var encodePassword = passwordEncoder.encode(request.getPassword());
        var basicVerifyToken = UUID.randomUUID().toString();
        account.setPassword(encodePassword);
        account.setVerifyToken(basicVerifyToken);
        var savedAccount = accountRepository.save(account);
        var roleList = roles.stream().map(UserRole::name).toList();
        roleRepository
                .findByRoleIn(roleList)
                .stream()
                .map(Role::getId)
                .map(rId -> AccountRole.builder()
                        .accountId(savedAccount.getId())
                        .roleId(rId)
                        .build()
                )
                .forEach(accountRoleRepository::save);
        eventPublisher.publishEvent(new AccountSignedUp(savedAccount));
    }

    @Transactional
    @Override
    public void confirm(long id, String token) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFound::new);
        if (account.getVerifyToken().isBlank()) throw new AccountAlreadyConfirmed();
        if (account.getVerifyToken().equals(token)) {
            account.setVerifyToken(null);
            account.setEnabled(true);
            accountRepository.save(account);
        }
    }
}
