package com.dd.direkt.shared_kernel.domain.exception;


import com.dd.direkt.shared_kernel.util.constant.ErrCode;

/**
 * This action will be thrown in case: the client wanted resource can not be found.
 */
public abstract class ResourceNotFoundException extends ApiException {
    @Override
    public int getErrCode() {
        return ErrCode.BAD_PARAMETER;
    }
}