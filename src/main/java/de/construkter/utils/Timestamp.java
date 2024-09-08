package de.construkter.utils;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    public Timestamp() {
    }

    public static String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalDateTime.now().format(formatter);
        } catch (DateTimeException var2) {
            DateTimeException e = var2;
            return "[!] Time Error:\n" + e.getMessage();
        }
    }

    public static String getTimestamp(boolean formatted, boolean colored) {
        String var10000;
        if (formatted && colored) {
            var10000 = ColorManager.CYAN;
            return var10000 + "[" + ColorManager.BLUE + getTime() + ColorManager.CYAN + "]";
        } else if (formatted && !colored) {
            return "[" + getTime() + "]";
        } else if (!formatted && colored) {
            var10000 = ColorManager.BLUE;
            return var10000 + getTime();
        } else {
            return getTime();
        }
    }
}
