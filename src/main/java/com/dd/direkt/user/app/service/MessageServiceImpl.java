package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.app.mapper.MsgMapper;
import com.dd.direkt.user.domain.exception.NotJoinedRoom;
import com.dd.direkt.user.domain.model.Msg;
import com.dd.direkt.user.domain.model.RoomEvent;
import com.dd.direkt.user.domain.repository.MessageRepository;
import com.dd.direkt.user.domain.repository.RoomMemberRepository;
import com.dd.direkt.user.infra.WebSocketConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MsgMapper msgMapper;
    private final SimpMessagingTemplate template;

    @Override
    public void sendTextMessage(SendTextMsgRequest request) {
        verifyRoomMember(request.getTo(), request.getFrom(), request.getSenderEmail());
        var event = processTextMsg(request);
        template.convertAndSend(WebSocketConfig.DEST_ROOM_EVENT + request.getTo(), event);
    }

    @Override
    public RoomEvent.IncomingMessage processTextMsg(SendTextMsgRequest request) {
        verifyRoomMember(request.getTo(), request.getFrom(), request.getSenderEmail());
        var msg = msgMapper.toMsgEntity(request);
        var savedMsg = messageRepository.save(msg);
        return new RoomEvent.IncomingMessage(
                savedMsg.getId().toString(),
                savedMsg.getFrom(),
                request.getSenderEmail(),
                savedMsg.getCreatedAt(),
                new Msg.Text(savedMsg.getData())
        );
    }

    private void verifyRoomMember(long roomId, long memberId, String memberEmail) {
        if (roomMemberRepository.findByRoomIdAndMemberId(roomId, memberId).isEmpty()) {
            throw new NotJoinedRoom(memberEmail);
        }
    }
}
