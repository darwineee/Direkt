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
    public CustomerInfoResponse getInfo(String email) {
       var account = repository
               .findByEmail(email)
               .orElseThrow(CustomerNotFound::new);
       return mapper.toGetCustomerRsp(account);
    }

    @Transactional
    @Override
    public CustomerInfoResponse updateInfo(UpdateCustomerRequest request, String email) {
        var account = repository.findByEmail(email).orElseThrow(CustomerNotFound::new);
        mapper.updateAccountEntity(account, request);
        return mapper.toGetCustomerRsp(repository.save(account));
    }

    @Transactional
    @Override
    public void suspendAccount(String email) {
        repository.updateEnabledByEmail(email, false);
    }
}
