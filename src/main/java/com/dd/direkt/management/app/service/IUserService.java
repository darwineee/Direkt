package com.dd.direkt.management.app.service;

import com.dd.direkt.management.app.dto.RequestCreateUser;
import com.dd.direkt.management.app.dto.RequestUpdateUser;
import com.dd.direkt.management.app.dto.ResponseGetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    ResponseGetUser find(long id);
    Page<ResponseGetUser> findAll(
            long clientId,
            String name,
            String email,
            Pageable pageable
    );
    ResponseGetUser create(RequestCreateUser request, long clientId);
    ResponseGetUser update(RequestUpdateUser request, long clientId, long userId);
    void delete(long id);
}
