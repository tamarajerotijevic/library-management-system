package util;

import java.text.SimpleDateFormat;

public class Utility {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static boolean isStringNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
