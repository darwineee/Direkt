package com.dd.direkt.user.domain.model;

import java.time.LocalDateTime;

public sealed interface RoomEvent permits
        RoomEvent.UserJoined,
        RoomEvent.UserLeft,
        RoomEvent.IncomingMessage {

    record UserJoined(String userEmail) implements RoomEvent {
    }

    record UserLeft(String userEmail) implements RoomEvent {
    }

    record IncomingMessage(
            String id,
            long from,
            String senderEmail,
            LocalDateTime createdAt,
            Msg msg
    ) implements RoomEvent {
    }

}
