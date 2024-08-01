package com.JobAppBackend.JobFreakBackend.exceptions;

public class ApiException extends RuntimeException{
    String message;
    public ApiException(String message){
        super(message);
        this.message = message;
    }

}
