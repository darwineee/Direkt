package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.CustomerInfoResponse;
import com.dd.direkt.customer.app.dto.UpdateCustomerRequest;

public interface CustomerService {
    CustomerInfoResponse getInfo(String email);
    CustomerInfoResponse updateInfo(UpdateCustomerRequest request, String email);
    void suspendAccount(String email);
}
