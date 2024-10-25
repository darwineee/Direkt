package com.dd.direkt.user.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Table("messages")
public class Message {
    @Id
    private UUID id;

    @Column("member_id")
    private long from;

    @Column("room_id")
    private long to;

    @Column("created_at")
    private LocalDateTime createdAt;

    private String data;
}
