package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountPagingRepository {
    Page<Account> findAllPaging(
            Pageable pageable,
            AccountPagingFilter filter
    );
}
