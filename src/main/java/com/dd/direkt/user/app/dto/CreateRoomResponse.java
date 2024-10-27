package com.dd.direkt.user.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateRoomResponse {
    private long id;
    private String name;
    private long ownerId;
    private List<Long> members;
}
