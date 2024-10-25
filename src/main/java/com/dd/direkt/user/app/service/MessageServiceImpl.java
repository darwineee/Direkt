package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.app.mapper.MsgMapper;
import com.dd.direkt.user.domain.model.Msg;
import com.dd.direkt.user.domain.model.RoomEvent;
import com.dd.direkt.user.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MsgMapper msgMapper;
    private final SimpMessagingTemplate template;

    @Override
    public void sendTextMessage(SendTextMsgRequest request) {
        var event = processTextMsg(request);
        sendEventToRoom(event, request.getTo());
    }

    @Override
    public RoomEvent.IncomingMessage processTextMsg(SendTextMsgRequest request) {
        var msg = msgMapper.toMsgEntity(request);
        var savedMsg = messageRepository.save(msg);
        return new RoomEvent.IncomingMessage(
                savedMsg.getId().toString(),
                savedMsg.getFrom(),
                savedMsg.getCreatedAt(),
                new Msg.Text(savedMsg.getData())
        );
    }

    private void sendEventToRoom(RoomEvent event, long roomId) {
        template.convertAndSend("/room/" + roomId, event);
    }
}
