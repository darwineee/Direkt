package com.dd.direkt.user.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.WsException;
import com.dd.direkt.shared_kernel.util.ErrCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotJoinedRoom extends WsException {
    private final String userEmail;

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String getMsgKey() {
        return "err.chat.send.outside.room";
    }

    @Override
    public int getErrCode() {
        return ErrCode.WS_SUB_ERR;
    }
}
