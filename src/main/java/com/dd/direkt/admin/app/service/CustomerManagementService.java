package com.dd.direkt.admin.app.service;

import com.dd.direkt.admin.app.dto.CreateCustomerRequest;
import com.dd.direkt.admin.app.dto.CustomerInfoResponse;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerManagementService {
    Page<CustomerInfoResponse> getCustomers(Pageable pageable, AccountPagingFilter filter);
    CustomerInfoResponse getCustomerInfo(long id);
    void createCustomer(CreateCustomerRequest request, long adminId);
    void deleteCustomer(long id);
}
