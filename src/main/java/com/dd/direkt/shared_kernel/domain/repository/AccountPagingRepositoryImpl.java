package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.shared_kernel.domain.type.AccountPagingFilter;
import com.dd.direkt.shared_kernel.util.Stringx;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AccountPagingRepositoryImpl implements AccountPagingRepository {

    private final JdbcAggregateTemplate template;

    @Override
    public Page<Account> findAllPaging(
            Pageable pageable,
            AccountPagingFilter filter
    ) {
        var condition = buildCondition(filter);
        var offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        var countQuery = Query.query(condition);
        var pagingQuery = Query.query(condition)
                .sort(pageable.getSort())
                .limit(pageable.getPageSize())
                .offset(offset);
        var total = template.count(countQuery, Account.class);
        var accounts = template.findAll(pagingQuery, Account.class);
        return new PageImpl<>(
                Streamable.of(accounts).toList(),
                pageable,
                total
        );
    }

    private Criteria buildCondition(AccountPagingFilter filter) {
        var condition = Criteria.empty();
        if (Objects.isNull(filter)) return condition;
        if (filter.getCustomerId() != null) {
            condition = condition.and("customer_id").is(filter.getCustomerId());
        }
        if (Stringx.isValuable(filter.getEmail())) {
            condition = condition.and("upper(email)").like("upper(%" + filter.getEmail() + "%)");
        }
        if (Stringx.isValuable(filter.getName())) {
            condition = condition.and("upper(name)").like("upper(%" + filter.getName() + "%)");
        }
        if (filter.getEnabled() != null) {
            condition = condition.and("enabled").is(filter.getEnabled());
        }
        return condition;
    }
}
