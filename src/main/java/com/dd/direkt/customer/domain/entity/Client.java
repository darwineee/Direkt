package com.dd.direkt.customer.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "clients")
public class Client {
    @Id
    private long id;
    private String name;
    private String apiKey;
}
