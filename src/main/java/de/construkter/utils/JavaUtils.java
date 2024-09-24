package de.construkter.utils;

import java.io.IOException;
import java.util.Scanner;

public class JavaUtils {
    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void err(Object obj) {
        System.err.println(obj);
    }

    public static String input(String text){
        Scanner scanner = new Scanner(System.in);
        print(text);
        return scanner.next();
    }

    public static void exit(int status){
        System.exit(status);
    }

    public static void errAppend(String err){
        System.err.append(err);
    }

    public static void errWrite(String err) {
       try {
           System.err.write(err.getBytes());
       } catch (IOException e) {
           err(e.toString());
       }
    }
}
