package com.dd.direkt.management.domain.repository;

import com.dd.direkt.management.domain.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long>, UserPagingRepository {
    boolean existsByEmail(String email);
}
