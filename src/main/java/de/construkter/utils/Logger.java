package de.construkter.utils;

public class Logger {
    public static void event(String message) {
        System.out.println(ColorManager.CYAN + "[" + ColorManager.BLUE + Timestamp.getTime() + ColorManager.CYAN + "] " + ColorManager.GREEN + message);
    }
}
