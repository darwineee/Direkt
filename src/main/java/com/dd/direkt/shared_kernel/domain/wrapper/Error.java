package com.dd.direkt.shared_kernel.domain.wrapper;

public record Error<T>(
        int statusCode,
        T metaData
) {
}
