package com.dd.direkt.shared_kernel.domain.exception.base;

import com.dd.direkt.shared_kernel.util.ErrCode;

/**
 * This action will be thrown in case: an action should be always success unless server has an unknown error.
 */
public abstract class ActionFailException extends RootException {
    @Override
    public int getErrCode() {
        return ErrCode.UNKNOWN;
    }
}
