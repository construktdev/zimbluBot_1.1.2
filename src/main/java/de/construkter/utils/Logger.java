package de.construkter.utils;

public class Logger extends JavaUtils{
    public static void event(String message) {
        println(ColorManager.CYAN + "[" + ColorManager.BLUE + Timestamp.getTime() + ColorManager.CYAN + "] " + ColorManager.GREEN + message);
    }

    public static void error(String message) {
        println(ColorManager.RED + " [!] " + ColorManager.GREEN + message);
    }
}
