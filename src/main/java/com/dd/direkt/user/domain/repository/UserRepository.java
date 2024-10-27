package com.dd.direkt.user.domain.repository;

import com.dd.direkt.shared_kernel.domain.repository.AccountPagingRepository;
import com.dd.direkt.shared_kernel.domain.repository.AccountRepository;

public interface UserRepository extends AccountRepository, AccountPagingRepository {
}
