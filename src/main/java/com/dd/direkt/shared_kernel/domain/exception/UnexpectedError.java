package com.dd.direkt.shared_kernel.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ActionFailException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnexpectedError extends ActionFailException {

    private final int errCode;

    @Override
    public String getMsgKey() {
        return "err.unknown";
    }

    @Override
    public int getErrCode() {
        return errCode;
    }
}
