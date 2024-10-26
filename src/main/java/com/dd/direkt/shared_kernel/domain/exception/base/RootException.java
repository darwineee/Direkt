package com.dd.direkt.shared_kernel.domain.exception.base;

/**
 * Base type of all pre-defined checked exception in all services.
 */
public abstract class RootException extends RuntimeException {
    public abstract int getErrCode();
    public abstract String getMsgKey();
    public Object[] getMsgParams() {
        return null;
    }
}
