package com.dd.direkt.management.webapi.v1;

import com.dd.direkt.management.app.dto.RequestCreateUser;
import com.dd.direkt.management.app.dto.RequestUpdateUser;
import com.dd.direkt.management.app.dto.ResponseGetUser;
import com.dd.direkt.management.app.service.IUserService;
import com.dd.direkt.shared_kernel.util.constant.RequestCst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{id}")
    ResponseEntity<ResponseGetUser> getUser(
            @PathVariable long id
    ) {
        ResponseGetUser response = userService.find(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<Page<ResponseGetUser>> getUsers(
            @RequestParam(name = RequestCst.CLIENT_ID) long clientId,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "email", defaultValue = "") String email,

            @ParameterObject
            @PageableDefault(size = 20)
            Pageable pageable
    ) {
        Page<ResponseGetUser> page = userService.findAll(clientId, name, email, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    ResponseEntity<ResponseGetUser> createUser(
            @RequestParam(name = RequestCst.CLIENT_ID) long clientId,
            @RequestBody
            @Valid
            RequestCreateUser request
    ) {
        ResponseGetUser response = userService.create(request, clientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseGetUser> updateUser(
            @RequestParam(name = RequestCst.CLIENT_ID) long clientId,
            @RequestBody
            @Valid
            RequestUpdateUser request,

            @PathVariable(name = "id") long userId
    ) {
        ResponseGetUser response = userService.update(request, clientId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(
            @PathVariable long id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
