package com.dd.direkt.admin.app.service;

import com.dd.direkt.admin.app.mapper.AdminMapper;
import com.dd.direkt.admin.app.dto.CustomerInfoResponse;
import com.dd.direkt.admin.domain.repository.CustomerManagementRepository;
import com.dd.direkt.admin.domain.exception.CustomerNotFound;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerManagementServiceImpl implements CustomerManagementService {

    private final CustomerManagementRepository customerRepository;
    private final AdminMapper mapper;

    @Override
    public Page<CustomerInfoResponse> getCustomers(Pageable pageable, AccountPagingFilter filter) {
        var data = customerRepository.findAllPaging(pageable, filter);
        return new PageImpl<>(
                data.map(mapper::toCustomerInfoRsp).toList(),
                pageable,
                data.getTotalElements()
        );
    }

    @Override
    public CustomerInfoResponse getCustomerInfo(long id) {
        var account = customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFound::new);
        return mapper.toCustomerInfoRsp(account);
    }

    @Transactional
    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
