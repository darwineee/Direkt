package com.dd.direkt.shared_kernel.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "accounts")
public class Account {
    @Id
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;

    @Column("verify_token")
    private String verifyToken;
}
