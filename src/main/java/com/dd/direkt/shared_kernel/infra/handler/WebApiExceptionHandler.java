package com.dd.direkt.shared_kernel.infra.handler;

import com.dd.direkt.shared_kernel.domain.exception.ApiException;
import com.dd.direkt.shared_kernel.util.constant.ErrCode;
import com.dd.direkt.shared_kernel.domain.wrapper.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebApiExceptionHandler {
    private final MessageSource msgSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error<Map<String, String>>> handle(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            errors.put(field, message);
        });
        var body = new Error<>(ErrCode.FIELD_VALIDATION, errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Error<String>> handle(ApiException ex) {
        return ResponseEntity
                .badRequest()
                .body(new Error<>(
                        ex.getErrCode(),
                        msgSource.getMessage(ex.getMsgKey(), ex.getMsgParams(), LocaleContextHolder.getLocale())
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error<String>> handle(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(new Error<>(ErrCode.BAD_PARAMETER, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error<String>> handleDefaultException(Exception ignored) {
        return ResponseEntity
                .internalServerError()
                .body(new Error<>(
                        ErrCode.UNKNOWN,
                        msgSource.getMessage("err.unknown", null, LocaleContextHolder.getLocale())
                ));
    }
}
