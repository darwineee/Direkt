package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.CreateUserRequest;
import com.dd.direkt.customer.app.dto.UserInfoResponse;
import com.dd.direkt.customer.app.mapper.CustomerMapper;
import com.dd.direkt.customer.domain.exception.CustomerNotFound;
import com.dd.direkt.customer.domain.repository.UserManagementRepository;
import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import com.dd.direkt.shared_kernel.domain.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementRepository userRepository;
    private final BasicAuthService basicAuthService;
    private final CustomerMapper mapper;

    @Transactional
    @Override
    public void createUser(CreateUserRequest request, String customerEmail) {
        var signUpRequest = mapper.toSignUpRequest(request);
        var customerId = userRepository
                .findByEmail(customerEmail)
                .orElseThrow(CustomerNotFound::new)
                .getId();
        signUpRequest.setCustomerId(customerId);
        basicAuthService.signUp(signUpRequest, List.of(UserRole.EndUser));
    }

    @Transactional
    @Override
    public void changeUserEnabledStatus(long id, boolean enabled) {
        userRepository.updateEnabledById(id, enabled);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserInfoResponse> findAllUsers(String customerEmail, Pageable pageable, AccountPagingFilter filter) {
        var customerId = userRepository
                .findByEmail(customerEmail)
                .orElseThrow(CustomerNotFound::new)
                .getId();
        filter.setCustomerId(customerId);
        var data = userRepository.findAllPaging(pageable, filter);
        return new PageImpl<>(
                data.map(mapper::toUserInfoRsp).toList(),
                pageable,
                data.getTotalElements()
        );
    }
}
