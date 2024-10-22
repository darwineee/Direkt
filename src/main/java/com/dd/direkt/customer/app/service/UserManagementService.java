package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.CreateUserRequest;
import com.dd.direkt.customer.app.dto.UserInfoResponse;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {
    void createUser(CreateUserRequest request, String customerEmail);
    void changeUserEnabledStatus(long id, boolean enabled);
    void deleteUser(long id);

    Page<UserInfoResponse> findAllUsers(String customerEmail, Pageable pageable, AccountPagingFilter filter);
}
