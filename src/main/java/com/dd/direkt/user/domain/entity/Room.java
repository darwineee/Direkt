package com.dd.direkt.user.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("rooms")
public class Room {
    @Id
    private long id;
    private String name;
    @Column("owner_id")
    private long ownerId;
}
