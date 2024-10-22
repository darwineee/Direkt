package com.dd.direkt.shared_kernel.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ActionFailException;
import com.dd.direkt.shared_kernel.util.ErrCode;

public class AccountAlreadyConfirmed extends ActionFailException {
    @Override
    public String getMsgKey() {
        return "err.acc.confirmed";
    }

    @Override
    public int getErrCode() {
        return ErrCode.ACCOUNT_CONFIRMED;
    }
}
