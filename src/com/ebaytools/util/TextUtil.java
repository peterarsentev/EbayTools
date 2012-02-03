package com.ebaytools.util;

public class TextUtil {
    public static boolean isNotNull(Object text) {
        return text != null && !text.toString().isEmpty();
    }

    public static Integer getIntegerOrNull(String text) {
        try {
            if (TextUtil.isNotNull(text)) {
                return Integer.valueOf(text);
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer convertDayToHours(String text) {
        Integer days = getIntegerOrNull(text);
        return days != null ? 24 * days : null;
    }

    /**
     * This method parses srting and trys to convert it in float
     * @param text parse string
     * @return float or zero
     */
    public static float getFloarOrZero(String text) {
        try {
            if (TextUtil.isNotNull(text)) {
                return Float.valueOf(text);
            } else {
                return 0f;
            }
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
}
