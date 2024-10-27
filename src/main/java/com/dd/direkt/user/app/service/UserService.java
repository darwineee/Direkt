package com.dd.direkt.user.app.service;

import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.dto.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserInfoResponse find(long id);
    Page<UserInfoResponse> findColleagues(Pageable pageable, AccountPagingFilter filter);
    UserInfoResponse update(UpdateUserRequest request, long userId);
}
