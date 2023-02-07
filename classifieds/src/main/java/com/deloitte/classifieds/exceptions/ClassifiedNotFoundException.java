package com.deloitte.classifieds.exceptions;

public class ClassifiedNotFoundException extends RuntimeException{
    public ClassifiedNotFoundException(String message){
        super(message);
    }
}
