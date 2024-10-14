package com.dd.direkt.management.domain.repository;

import com.dd.direkt.management.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserPagingRepository {
    Page<User> findAllPaging(
            long clientId,
            String email,
            String name,
            Pageable pageable
    );
}
