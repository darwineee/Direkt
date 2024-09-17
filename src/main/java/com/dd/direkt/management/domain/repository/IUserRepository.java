package com.dd.direkt.management.domain.repository;

import com.dd.direkt.management.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserRepository extends
        ListCrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    @Query(
            """
             select u from users u
             where u.client_id = :clientId
             and (
                  upper(u.email) like upper(concat('%', :email, '%'))
                  or upper(u.name) like upper(concat('%', :name, '%'))
             )
             """
    )
    Page<User> findAll(
            long clientId,
            String email,
            String name,
            Pageable pageable
    );

    boolean existsByEmail(String email);
}
