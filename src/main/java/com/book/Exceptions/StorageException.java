package com.book.Exceptions;

/**
 * Created by vaibhav.rana on 1/10/17.
 */
public class StorageException extends RuntimeException{

    public StorageException(String message){
        super(message);
    }

    public StorageException(String message, Throwable cause){
        super(message, cause);
    }
}
