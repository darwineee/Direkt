package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.app.mapper.AuthMapper;
import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.shared_kernel.domain.entity.AccountRole;
import com.dd.direkt.shared_kernel.domain.entity.Role;
import com.dd.direkt.shared_kernel.domain.model.AccountDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountDetailRepositoryImpl implements AccountDetailRepository {

    private final JdbcAggregateTemplate template;
    private final AuthMapper mapper;

    @Override
    public Optional<AccountDetail> findDetailById(long id) {
        var account = template.findById(id, Account.class);
        if (account == null) return Optional.empty();
        return Optional.of(findDetail(account));
    }

    @Override
    public Optional<AccountDetail> findDetailByEmail(String email) {
        return template
                .findOne(
                        Query.query(Criteria.where("email").is(email)),
                        Account.class
                )
                .map(this::findDetail);
    }

    @Override
    public AccountDetail findDetail(Account account) {
        var accountRoleIterator = template.findAll(
                Query.query(Criteria.where("account_id").is(account.getId())),
                AccountRole.class
        );
        var roleIds = Streamable.of(accountRoleIterator)
                .map(AccountRole::getRoleId)
                .toList();
        var roleIterator = template.findAllById(roleIds, Role.class);
        var roles = Streamable.of(roleIterator).toList();
        var accDetail = mapper.toAccountDetail(account);
        accDetail.setRoles(roles);
        return accDetail;
    }
}
