package com.ftd.example.SpringBootMongoDBApplication.exception;

public class TodoCollectionException extends Exception{

    private static final long serialVersionUID = 2614529057622876291L;

    public TodoCollectionException(String message) {
        super(message);

    }

    public static String NotFoundException(String id){

        return "Todo with id "+id+" not Found";
    }

    public static String TodoAlreadyExists(){
        return "Todo with given name is already exists";
    }



}
