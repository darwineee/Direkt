package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.RequestCreateClient;
import com.dd.direkt.customer.app.dto.RequestUpdateClient;
import com.dd.direkt.customer.app.dto.ResponseGetClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ResponseGetClient find(long id);
    Page<ResponseGetClient> findAll(Pageable pageable, String name);
    ResponseGetClient create(RequestCreateClient request);
    ResponseGetClient update(RequestUpdateClient request, long id);
    void delete(long id);
}
