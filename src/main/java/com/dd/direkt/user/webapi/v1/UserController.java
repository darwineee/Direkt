package com.dd.direkt.user.webapi.v1;

import com.dd.direkt.user.app.dto.UserInfoResponse;
import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    ResponseEntity<UserInfoResponse> getUser(
            @PathVariable long id
    ) {
        UserInfoResponse response = userService.find(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserInfoResponse> updateUser(
            @RequestBody
            @Valid
            UpdateUserRequest request,

            @PathVariable(name = "id") long userId
    ) {
        UserInfoResponse response = userService.update(request, userId);
        return ResponseEntity.ok(response);
    }
}
