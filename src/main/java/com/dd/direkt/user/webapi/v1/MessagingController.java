package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.app.service.MessageService;
import com.dd.direkt.user.domain.model.RoomEvent;
import com.dd.direkt.user.infra.WebSocketConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MessagingController {

    private final MessageService messageService;

    @MessageMapping("/room/{roomId}")
    @SendTo(WebSocketConfig.DEST_ROOM_EVENT + "/{roomId}")
    public RoomEvent sendRawTextMsg(
            @DestinationVariable long roomId,
            @Valid @Payload SendTextMsgRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        request.setFrom(userDetails.getId());
        request.setSenderEmail(userDetails.getUsername());
        request.setTo(roomId);
        request.setCreatedAt(LocalDateTime.now());
        return messageService.processTextMsg(request);
    }

    @PostMapping("/api/v1/message/sendText/{roomId}")
    @ResponseBody
    ResponseEntity<Void> sendTextMsg(
            @PathVariable long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid SendTextMsgRequest request
    ) {
        request.setFrom(userDetails.getId());
        request.setTo(roomId);
        messageService.sendTextMessage(request);
        return ResponseEntity.ok().build();
    }
}
