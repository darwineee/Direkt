package com.dd.direkt.user.domain.exception;

import com.dd.direkt.shared_kernel.domain.exception.base.ActionFailException;
import com.dd.direkt.shared_kernel.util.ErrCode;

public class WsSubRoomChangeFailed extends ActionFailException {
    @Override
    public String getMsgKey() {
        return "err.chat.sub.roomChange";
    }

    @Override
    public int getErrCode() {
        return ErrCode.WS_SUB_ERR;
    }
}
