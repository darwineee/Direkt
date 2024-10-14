package com.dd.direkt.management.domain.repository;

import com.dd.direkt.management.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserPagingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<User> findAllPaging(
            long clientId,
            String email,
            String name,
            Pageable pageable
    ) {
        String condition = """
                where u.client_id = ?
                and (
                     upper(u.email) like upper(concat('%', ?, '%'))
                     or upper(u.name) like upper(concat('%', ?, '%'))
                )
                """;
        String countQuery = "select count(*) from users u" + condition;
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        String userQuery = "select * from users u" + condition + "limit ? offset ?";
        Integer total = jdbcTemplate.queryForObject(
                countQuery,
                Integer.class,
                clientId, email, name
        );
        if (total == null) total = 0;
        List<User> users = jdbcTemplate.queryForList(
                userQuery,
                User.class,
                clientId, email, name, pageable.getPageSize(), offset
        );
        return new PageImpl<>(users, pageable, total);
    }
}
