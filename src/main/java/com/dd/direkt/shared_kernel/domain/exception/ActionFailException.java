package com.dd.direkt.shared_kernel.domain.exception;

import com.dd.direkt.shared_kernel.util.constant.ErrCode;

/**
 * This action will be thrown in case: an action should be always success unless server has an unknown error.
 */
public abstract class ActionFailException extends ApiException {
    @Override
    public int getErrCode() {
        return ErrCode.UNKNOWN;
    }
}
