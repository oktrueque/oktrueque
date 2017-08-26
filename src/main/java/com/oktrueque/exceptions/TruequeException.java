package com.oktrueque.exceptions;

/**
 * Created by Facundo on 8/24/2017.
 */
public class TruequeException extends AbstractApplicationException{

    private static final Integer ERROR_CODE = 1;
    private static final String ERROR_MESSAGE = "No se pudo realizar el trueque, Intente nuevamente";

    public TruequeException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public int getCode() {
        return ERROR_CODE;
    }
}
