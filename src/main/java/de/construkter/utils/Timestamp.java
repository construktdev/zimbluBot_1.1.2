package de.construkter.utils;


import de.construkter.Main;

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
        } catch (DateTimeException e) {
            return "[!] Time Error:\n" + e.getMessage();
        }
    }
}
