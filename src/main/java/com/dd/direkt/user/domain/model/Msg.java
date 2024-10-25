package com.dd.direkt.user.domain.model;

public sealed interface Msg permits
        Msg.Text,
        Msg.Image,
        Msg.File {
    record Text(String text) implements Msg {
    }

    record Image(String url, String name, String type, int size) implements Msg {
    }

    record File(String url, String name, String type, int size) implements Msg {
    }
}
