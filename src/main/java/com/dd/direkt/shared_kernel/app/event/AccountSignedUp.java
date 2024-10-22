package com.dd.direkt.shared_kernel.app.event;

import com.dd.direkt.shared_kernel.domain.entity.Account;

public record AccountSignedUp(
        Account info
) {
}
