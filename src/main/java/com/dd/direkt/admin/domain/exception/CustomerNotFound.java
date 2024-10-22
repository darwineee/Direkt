package com.dd.direkt.admin.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ResourceNotFoundException;

public class CustomerNotFound extends ResourceNotFoundException {
    @Override
    public String getMsgKey() {
        return "err.customer.notfound";
    }
}
