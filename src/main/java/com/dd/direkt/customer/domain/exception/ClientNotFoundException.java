package com.dd.direkt.customer.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.ResourceNotFoundException;

public class ClientNotFoundException extends ResourceNotFoundException {
    @Override
    public String getMsgKey() {
        return "err.client.not.found";
    }
}
