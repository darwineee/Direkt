package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.CreateRoomRequest;
import com.dd.direkt.user.app.dto.CreateRoomResponse;

public interface RoomService {
    boolean isRoomMember(long roomId, long userId);
    void joinRoom(long roomId, long userId);
    void leaveRoom(long roomId, long userId);
    CreateRoomResponse createRoom(CreateRoomRequest request, long userId);
}
