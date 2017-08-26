package com.oktrueque.exceptions;

/**
 * Created by Facundo on 8/24/2017.
 */
public abstract class AbstractApplicationException extends RuntimeException {

    public AbstractApplicationException(String message){
        super(message);

    }
    public abstract int getCode();
}
