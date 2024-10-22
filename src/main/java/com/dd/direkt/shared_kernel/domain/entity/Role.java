package com.dd.direkt.shared_kernel.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "roles")
public class Role {
    @Id
    private long id;
    private String role;
}
