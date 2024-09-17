package com.dd.direkt.customer.domain.repository;

import com.dd.direkt.customer.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClientRepository extends
        ListCrudRepository<Client, Long>,
        PagingAndSortingRepository<Client, Long>
{
    Page<Client> findByNameContainingIgnoreCase(Pageable pageable, String username);
}
