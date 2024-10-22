package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.AccountRole;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AccountRoleRepository extends ListCrudRepository<AccountRole, Long> {
    List<AccountRole> findByAccountId(Long accountId);
}
