package com.dd.direkt.shared_kernel.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(value = "accounts_roles")
public class AccountRole {

    @Id
    private long id;

    @Column("account_id")
    private long accountId;

    @Column("role_id")
    private long roleId;
}
