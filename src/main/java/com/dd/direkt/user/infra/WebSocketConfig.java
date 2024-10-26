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
    public static final String SUB_PREFIX = "/sub";
    public static final String DEST_ROOM_EVENT = SUB_PREFIX + "/room.change";
    public static final String DEST_ERR = SUB_PREFIX + "/err.queue";
    public static final String SEND_PREFIX = "/send";
    public static final String WS_ENDPOINT = "/ws/v1/message";

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final ApplicationContext context;
    private final MessagingSocketErrorHandler messagingSocketErrorHandler;

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(DEST_ROOM_EVENT, DEST_ERR);
        registry.setApplicationDestinationPrefixes(SEND_PREFIX);
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint(WS_ENDPOINT);
        registry.setErrorHandler(messagingSocketErrorHandler);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        var authChannelInterceptor = createAuthChannelInterceptor();
        var publisher = new SpringAuthorizationEventPublisher(context);
        authChannelInterceptor.setAuthorizationEventPublisher(publisher);

        registration.interceptors(
                new SecurityContextChannelInterceptor(),
                authChannelInterceptor
        );
    }

    private AuthorizationChannelInterceptor createAuthChannelInterceptor() {
        return new AuthorizationChannelInterceptor(createAuthManager());
    }

    private AuthorizationManager<Message<?>> createAuthManager() {
        return (authentication, message) -> {
            var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (isConnectCommand(accessor)) {
                return validateJwtAndCreatePrincipal(accessor);
            }
            return new AuthorizationDecision(authentication.get() != null);
        };
    }

    private boolean isConnectCommand(StompHeaderAccessor accessor) {
        return accessor != null && StompCommand.CONNECT.equals(accessor.getCommand());
    }

    private AuthorizationDecision validateJwtAndCreatePrincipal(StompHeaderAccessor accessor) {
        List<String> headers = accessor.getNativeHeader("Authorization");
        if (headers == null || headers.isEmpty()) {
            return new AuthorizationDecision(false);
        }

        String bearer = headers.getFirst();
        var credentials = jwtHelper.extractCredentials(bearer);
        if (credentials == null || !jwtHelper.validateToken(credentials.token())) {
            return new AuthorizationDecision(false);
        }

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

