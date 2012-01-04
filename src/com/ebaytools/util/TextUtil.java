package com.ebaytools.util;

public class TextUtil {
    public static boolean isNotNull(Object text) {
        return text != null && !text.toString().isEmpty();
    }

    public static Integer getIntegerOrNull(String text) {
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
