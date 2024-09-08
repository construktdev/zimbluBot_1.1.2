package de.construkter.utils;

public class FakeJSONResponse {
    public static String setResponse(int code, String name, String message){
        return "{\"code\":" + code + " , \"name\":\"" + name + "\" , \"message\":\"" + message + "\"}";
    }
}
