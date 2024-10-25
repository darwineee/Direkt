package com.dd.direkt.user.infra;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.user.app.service.RoomService;
import com.dd.direkt.user.domain.exception.WsSubRoomChangeFailed;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class MessagingSocketEventListener {

    private final RoomService roomService;
    private final SimpMessagingTemplate template;

    @EventListener
    public void on(SessionSubscribeEvent event) {
        var accessor = StompHeaderAccessor.wrap(event.getMessage());
        var dest = accessor.getDestination();
        if (dest == null) {
            sendErrMsg(accessor.getSessionId(), "Null destination");
            throw new WsSubRoomChangeFailed();
        }
        var roomId = extractRoomId(dest);
        if (roomId == null) {
            return;
        }
        var principal = (UsernamePasswordAuthenticationToken) accessor.getUser();
        if (principal == null) {
            sendErrMsg(accessor.getSessionId(), "User is not authenticated");
            throw new WsSubRoomChangeFailed();
        }
        var user = (CustomUserDetails) principal.getPrincipal();
        if (!roomService.isRoomMember(roomId, user.getId())) {
            sendErrMsg(accessor.getSessionId(), "Not a room member");
            throw new WsSubRoomChangeFailed();
        }
    }

    private Long extractRoomId(String destination) {
        var segments = destination.split("/");
        try {
            return Long.parseLong(segments[segments.length - 1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void sendErrMsg(String sessionId, String msg) {
        template.convertAndSendToUser(
                sessionId,
                WebSocketConfig.DEST_ERR,
                msg
        );
    }
}
