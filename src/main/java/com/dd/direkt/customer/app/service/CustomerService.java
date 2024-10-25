package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.CustomerInfoResponse;
import com.dd.direkt.customer.app.dto.UpdateCustomerRequest;

public interface CustomerService {
    CustomerInfoResponse getInfo(long id);
    CustomerInfoResponse updateInfo(UpdateCustomerRequest request, long id);
    void suspendAccount(long id);
}
