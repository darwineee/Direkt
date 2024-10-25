package com.dd.direkt.user.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("room_members")
public class RoomMember {
    @Id
    private long id;

    @Column("room_id")
    private long roomId;

    @Column("member_id")
    private long memberId;
}
