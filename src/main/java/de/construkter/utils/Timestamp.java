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
}
