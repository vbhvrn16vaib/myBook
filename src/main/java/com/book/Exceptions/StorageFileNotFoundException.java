package com.book.Exceptions;

/**
 * Created by vaibhav.rana on 1/10/17.
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
