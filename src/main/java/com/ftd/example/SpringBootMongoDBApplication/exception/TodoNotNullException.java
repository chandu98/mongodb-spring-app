package com.ftd.example.SpringBootMongoDBApplication.exception;

public class TodoNotNullException extends Exception{
    private static final long serialVersionUID = -5804721362521450313L;

    public TodoNotNullException(String message) {
        super(message);
    }


    public static String TodoIsNullOrEmpty(){
        return "Todo cannot be empty or null";
    }

}
