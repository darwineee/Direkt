package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.dto.UserInfoResponse;

public interface UserService {
    UserInfoResponse find(long id);
    UserInfoResponse update(UpdateUserRequest request, long userId);
}
