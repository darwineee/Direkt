package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.shared_kernel.domain.model.CustomUserDetails;
import com.dd.direkt.user.app.dto.CreateRoomRequest;
import com.dd.direkt.user.app.dto.CreateRoomResponse;
import com.dd.direkt.user.app.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<CreateRoomResponse> createRoom(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,

            @RequestBody
            @Valid
            CreateRoomRequest request
    ) {
        var response = roomService.createRoom(
                request,
                userDetails.getId()
        );
        return ResponseEntity.ok(response);
    }
}
