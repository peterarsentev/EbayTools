package util;

public class TextUtil {
    public static boolean isNotNull(Object text) {
        return text != null && !text.toString().isEmpty();
    }
}
