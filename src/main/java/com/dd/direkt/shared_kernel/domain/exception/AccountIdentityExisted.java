package com.dd.direkt.shared_kernel.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ActionFailException;
import com.dd.direkt.shared_kernel.util.ErrCode;

public class AccountIdentityExisted extends ActionFailException {
    @Override
    public int getErrCode() {
        return ErrCode.BAD_PARAMETER;
    }

    @Override
    public String getMsgKey() {
        return "err.acc.exist";
    }
}
