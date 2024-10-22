package com.dd.direkt.shared_kernel.util;

public class Stringx {
    public static final String EMPTY = "";
    public static final String SPACE = " ";


    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public static boolean isValuable(String s) {
        return s != null && !s.isBlank();
    }
}
