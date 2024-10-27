package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.app.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class MessagingController {

    private final MessageService messageService;

    @PostMapping("/sendText/{roomId}")
    ResponseEntity<Void> sendTextMsg(
            @PathVariable
            long roomId,

            @AuthenticationPrincipal
            CustomUserDetails userDetails,

            @RequestBody
            @Valid
            SendTextMsgRequest request
    ) {
        request.setFrom(userDetails.getId());
        request.setSenderEmail(userDetails.getUsername());
        request.setTo(roomId);
        messageService.sendTextMessage(request);
        return ResponseEntity.ok().build();
    }
}
