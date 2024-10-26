package com.dd.direkt.user.infra;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.shared_kernel.util.Stringx;
import com.dd.direkt.user.app.service.RoomService;
import com.dd.direkt.user.domain.exception.NotJoinedRoom;
import com.dd.direkt.user.domain.exception.SubscribeWrongDestination;
import com.dd.direkt.user.domain.exception.UserNotAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class MessagingSocketEventListener {

    private final RoomService roomService;

    @EventListener
    public void on(SessionSubscribeEvent event) {
        var accessor = StompHeaderAccessor.wrap(event.getMessage());
        var user = requirePrincipal(accessor);
        var dest = requireDest(accessor, user);
        if (dest.startsWith(WebSocketConfig.DEST_ROOM_EVENT)) {
            handleRoomSubscription(dest, user);
        }
    }

    private @NonNull CustomUserDetails requirePrincipal(StompHeaderAccessor accessor) {
        var principal = (UsernamePasswordAuthenticationToken) accessor.getUser();
        if (principal == null) {
            throw new UserNotAuthenticated(null);
        }
        return (CustomUserDetails) principal.getPrincipal();
    }

    private @NonNull String requireDest(StompHeaderAccessor accessor, CustomUserDetails user) {
        var dest = accessor.getDestination();
        if (Stringx.isNullOrBlank(dest) || !dest.contains(WebSocketConfig.SUB_PREFIX)) {
            throw new SubscribeWrongDestination(user.getUsername());
        }
        return dest;
    }

    private void handleRoomSubscription(String dest, CustomUserDetails user) {
        long roomId;
        try {
            roomId = Long.parseLong(dest.replaceFirst(WebSocketConfig.DEST_ROOM_EVENT, ""));
        } catch (NumberFormatException e) {
            throw new SubscribeWrongDestination(user.getUsername());
        }
        if (!roomService.isRoomMember(roomId, user.getId())) {
            throw new NotJoinedRoom(user.getUsername());
        }
    }
}
