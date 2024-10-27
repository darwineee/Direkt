package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.app.service.MessageService;
import com.dd.direkt.user.domain.model.RoomEvent;
import com.dd.direkt.user.infra.WebSocketConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WsMessagingController {

    private final MessageService messageService;

    @MessageMapping("/room/{roomId}")
    @SendTo(WebSocketConfig.DEST_ROOM_EVENT + "{roomId}")
    public RoomEvent sendRawTextMsg(
            @DestinationVariable long roomId,
            @Valid @Payload SendTextMsgRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        request.setFrom(userDetails.getId());
        request.setSenderEmail(userDetails.getUsername());
        request.setTo(roomId);
        return messageService.processTextMsg(request);
    }
}
