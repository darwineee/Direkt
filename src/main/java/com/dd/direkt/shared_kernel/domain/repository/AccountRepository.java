package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends ListCrudRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update accounts set enabled = :enabled where email = :email")
    void updateEnabledByEmail(@Param("email") String email, @Param("enabled") boolean enabled);

    @Modifying
    @Query("update accounts set enabled = :enabled where id = :id")
    void updateEnabledById(@Param("id") long id, @Param("enabled") boolean enabled);
}
