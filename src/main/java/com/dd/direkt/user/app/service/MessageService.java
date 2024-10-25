package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.domain.model.RoomEvent;

public interface MessageService {
    void sendTextMessage(SendTextMsgRequest request);
    RoomEvent.IncomingMessage processTextMsg(SendTextMsgRequest request);
}
