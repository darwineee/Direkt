package com.dd.direkt.email_service.internal;

import com.dd.direkt.shared_kernel.app.event.AccountSignedUp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${app.url.confirm}")
    private String appUrl;

    private final MessageSource msgSource;
//    private final JavaMailSender mailSender;

    @ApplicationModuleListener
    void on(AccountSignedUp event) {
        var user = event.info();
        var url = appUrl
        + "?id=" + user.getId()
        + "&token=" + user.getVerifyToken();
        var body = msgSource.getMessage("signup.mail.body", null, LocaleContextHolder.getLocale())
                + " " + url;
        var subject = msgSource.getMessage("signup.mail.subject", null, LocaleContextHolder.getLocale());
        sentEmail(user.getEmail(), subject, body);
    }

    public void sentEmail(String to, String subject, String body) {
        System.out.println(body);
    }
}
