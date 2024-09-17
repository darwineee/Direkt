package com.dd.direkt.customer.webapi.v1;

import com.dd.direkt.customer.app.dto.RequestCreateClient;
import com.dd.direkt.customer.app.dto.RequestUpdateClient;
import com.dd.direkt.customer.app.dto.ResponseGetClient;
import com.dd.direkt.customer.app.service.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;

    @GetMapping("/{id}")
    ResponseEntity<ResponseGetClient> getClient(
            @PathVariable long id
    ) {
        ResponseGetClient response = clientService.find(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<Page<ResponseGetClient>> getClients(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id")
            Pageable pageable,

            @RequestParam(value = "name", defaultValue = "") String name
    ) {
        Page<ResponseGetClient> response = clientService.findAll(pageable, name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<ResponseGetClient> createClient(
            @RequestBody
            @Valid
            RequestCreateClient request
    ) {
        ResponseGetClient response = clientService.create(request);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseGetClient> updateClient(
            @RequestBody
            @Valid
            RequestUpdateClient request,
            @PathVariable long id
    ) {
        ResponseGetClient response = clientService.update(request, id);
        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteClient(
            @PathVariable long id
    ) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
