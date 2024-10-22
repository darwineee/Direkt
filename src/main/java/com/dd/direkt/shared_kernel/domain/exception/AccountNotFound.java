package com.dd.direkt.shared_kernel.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ResourceNotFoundException;

public class AccountNotFound extends ResourceNotFoundException {
    @Override
    public String getMsgKey() {
        return "err.acc.notfound";
    }
}
