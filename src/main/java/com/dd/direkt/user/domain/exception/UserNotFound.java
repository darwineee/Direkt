package com.dd.direkt.user.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ResourceNotFoundException;

public class UserNotFound extends ResourceNotFoundException {
    @Override
    public String getMsgKey() {
        return "err.user.notfound";
    }
}
