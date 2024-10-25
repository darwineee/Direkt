package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.CustomerInfoResponse;
import com.dd.direkt.customer.app.dto.UpdateCustomerRequest;
import com.dd.direkt.customer.app.mapper.CustomerMapper;
import com.dd.direkt.customer.domain.exception.CustomerNotFound;
import com.dd.direkt.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerInfoResponse getInfo(long id) {
       var account = repository
               .findById(id)
               .orElseThrow(CustomerNotFound::new);
       return mapper.toGetCustomerRsp(account);
    }

    @Transactional
    @Override
    public CustomerInfoResponse updateInfo(UpdateCustomerRequest request, long id) {
        var account = repository.findById(id).orElseThrow(CustomerNotFound::new);
        mapper.updateAccountEntity(account, request);
        return mapper.toGetCustomerRsp(repository.save(account));
    }

    @Transactional
    @Override
    public void suspendAccount(long id) {
        repository.updateEnabledById(id, false);
    }
}
