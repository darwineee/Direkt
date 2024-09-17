package com.dd.direkt.management.app.dto;

public record ResponseGetUser(
        long id,
        String name,
        String email
) {
}
