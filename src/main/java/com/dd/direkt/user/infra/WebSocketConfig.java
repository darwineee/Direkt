package com.dd.direkt.user.infra;

import com.dd.direkt.shared_kernel.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.messaging.access.intercept.AuthorizationChannelInterceptor;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String DEST_ROOM_EVENT = "/sub/room.change";
    public static final String DEST_ERR = "/sub/err.queue";

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final ApplicationContext context;

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(DEST_ROOM_EVENT, DEST_ERR);
        registry.setApplicationDestinationPrefixes("/send");
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/v1/message");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        AuthorizationManager<Message<?>> msgAuthManager = (authentication, message) -> {
            var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                List<String> headers = accessor.getNativeHeader("Authorization");
                if (headers != null && !headers.isEmpty()) {
                    String bearer = headers.getFirst();
                    var credentials = jwtHelper.extractCredentials(bearer);
                    if (credentials != null && jwtHelper.validateToken(credentials.token())) {
                        var user = userDetailsService.loadUserByUsername(credentials.email());
                        var principal = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                        accessor.setUser(principal);
                        return new AuthorizationDecision(true);
                    }
                }
            }
            return new AuthorizationDecision(authentication.get() != null);
        };
        var authChannelInterceptor = new AuthorizationChannelInterceptor(msgAuthManager);
        var publisher = new SpringAuthorizationEventPublisher(context);
        authChannelInterceptor.setAuthorizationEventPublisher(publisher);
        registration.interceptors(
                new SecurityContextChannelInterceptor(),
                authChannelInterceptor
        );
    }
}
