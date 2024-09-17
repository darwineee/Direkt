package com.dd.direkt.management.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "users")
public class User {
    @Id
    private long id;
    private long clientId;
    private String name;
    private String email;
}
