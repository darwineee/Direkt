package com.dd.direkt.management.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.ActionFailException;
import com.dd.direkt.shared_kernel.util.constant.ErrCode;

public class UserIdentityExisted extends ActionFailException {
    @Override
    public int getErrCode() {
        return ErrCode.BAD_PARAMETER;
    }

    @Override
    public String getMsgKey() {
        return "err.invalid.user";
    }
}
