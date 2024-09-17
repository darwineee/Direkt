package com.dd.direkt.management.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    @Override
    public String getMsgKey() {
        return "err.user.not.found";
    }
}
