package com.dd.direkt.user.infra;

import com.dd.direkt.shared_kernel.domain.exception.base.WsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
@RequiredArgsConstructor
public class MessagingSocketErrorHandler extends StompSubProtocolErrorHandler {

    private final MessageSource messageSource;

    @Override
    @Nullable
    public Message<byte[]> handleClientMessageProcessingError(
            @Nullable Message<byte[]> clientMessage,
            @NonNull Throwable ex
    ) {
        if (ex instanceof WsException error) {
            String errMsg = messageSource.getMessage(
                    error.getMsgKey(),
                    null,
                    LocaleContextHolder.getLocale()
            );
            StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
            accessor.setMessage(errMsg);
            accessor.setLeaveMutable(true);
            if (clientMessage != null) {
                StompHeaderAccessor clientHeaderAccessor = MessageHeaderAccessor.getAccessor(
                        clientMessage,
                        StompHeaderAccessor.class
                );
                if (clientHeaderAccessor != null) {
                    String receiptId = clientHeaderAccessor.getReceipt();
                    if (receiptId != null) {
                        accessor.setReceiptId(receiptId);
                    }
                    String subscriptionId = clientHeaderAccessor.getSubscriptionId();
                    if (subscriptionId != null) {
                        accessor.setSubscriptionId(subscriptionId);
                    }
                }
            }
            return MessageBuilder.createMessage(
                    error.getMessage().getBytes(),
                    accessor.getMessageHeaders()
            );
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }
}
