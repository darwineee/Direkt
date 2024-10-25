package com.dd.direkt.user.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateRoomRequest {
    @NotBlank
    private String name;

    //min = 1 -> send msg to yourself
    //min = 2 -> direct message
    //min > 2 -> group chat
    @Size(min = 1)
    private List<Long> members;
}
