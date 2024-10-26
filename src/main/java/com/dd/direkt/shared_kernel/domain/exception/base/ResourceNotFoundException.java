package com.dd.direkt.shared_kernel.domain.exception.base;


import com.dd.direkt.shared_kernel.util.ErrCode;

/**
 * This action will be thrown in case: the client wanted resource can not be found.
 */
public abstract class ResourceNotFoundException extends RootException {
    @Override
    public int getErrCode() {
        return ErrCode.RESOURCE_NOT_FOUND;
    }
}