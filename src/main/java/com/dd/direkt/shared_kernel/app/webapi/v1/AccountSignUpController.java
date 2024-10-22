package com.dd.direkt.shared_kernel.app.webapi.v1;

import com.dd.direkt.shared_kernel.app.service.BasicAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountSignUpController {

    private final BasicAuthService basicAuthService;
    private final MessageSource messageSource;

    @GetMapping("/confirm")
    ResponseEntity<String> verifyAccount(
            @RequestParam(name = "id") long id,
            @RequestParam(name = "token") String token
    ) {
        basicAuthService.confirm(id, token);
        return ResponseEntity.ok(
                messageSource.getMessage("err.acc.confirmed", null, LocaleContextHolder.getLocale())
        );
    }
}
