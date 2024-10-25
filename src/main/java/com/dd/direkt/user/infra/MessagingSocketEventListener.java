package com.dd.direkt.user.infra;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.shared_kernel.util.Stringx;
import com.dd.direkt.user.app.service.RoomService;
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
        var user = requirePrincipal(accessor);
        if (user == null) return;
        var dest = requireDest(accessor, user);
        if (dest == null) return;
        if (dest.startsWith(WebSocketConfig.DEST_ROOM_EVENT)) {
            handleRoomSubscription(accessor, dest, user);
        }
    }

    private CustomUserDetails requirePrincipal(StompHeaderAccessor accessor) {
        var principal = (UsernamePasswordAuthenticationToken) accessor.getUser();
        if (principal == null) {
            denySubscription(accessor);
            return null;
        }
        return (CustomUserDetails) principal.getPrincipal();
    }

    private String requireDest(StompHeaderAccessor accessor, CustomUserDetails user) {
        var dest = accessor.getDestination();
        if (Stringx.isNullOrBlank(dest) || !dest.contains(WebSocketConfig.SUB_PREFIX)) {
            sendErrMsgAndTerminate(accessor, user.getUsername(), "Wrong destination");
            return null;
        }
        return dest;
    }

    private void handleRoomSubscription(StompHeaderAccessor accessor, String dest, CustomUserDetails user) {
        long roomId;
        try {
            roomId = Long.parseLong(dest.replaceFirst(WebSocketConfig.DEST_ROOM_EVENT, ""));
        } catch (NumberFormatException e) {
            sendErrMsgAndTerminate(accessor, user.getUsername(), "Invalid room");
            return;
        }
        if (!roomService.isRoomMember(roomId, user.getId())) {
            sendErrMsgAndTerminate(accessor, user.getUsername(), "Not a room member");
        }
    }

    private void sendErrMsgAndTerminate(StompHeaderAccessor accessor, String user, String msg) {
        template.convertAndSendToUser(
                user,
                WebSocketConfig.DEST_ERR,
                msg
        );
        denySubscription(accessor);
    }

    private void denySubscription(StompHeaderAccessor accessor) {
        accessor.setHeader("subscription-denied", true);
    }
}
