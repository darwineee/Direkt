package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.shared_kernel.domain.entity.view.AccountDetail;

import java.util.Optional;

public interface AccountDetailRepository {
    Optional<AccountDetail> findDetailById(long id);
    Optional<AccountDetail> findDetailByEmail(String email);
    AccountDetail findDetail(Account account);
}
