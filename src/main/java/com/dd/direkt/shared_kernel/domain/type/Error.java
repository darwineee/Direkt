package com.dd.direkt.shared_kernel.domain.type;

public record Error<T>(
        int statusCode,
        T metaData
) {
}
