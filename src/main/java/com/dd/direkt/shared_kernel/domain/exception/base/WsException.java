package com.dd.direkt.shared_kernel.domain.exception.base;

import org.springframework.lang.Nullable;

public abstract class WsException extends RootException {
    public abstract @Nullable String getUserEmail();
}
