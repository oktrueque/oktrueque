package com.oktrueque.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.TransactionalException;

/**
 * Created by Facundo on 8/24/2017.
 */

@ControllerAdvice
public class ApplicationExceptionHandlerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandlerAdvice.class);

    @ExceptionHandler(TransactionalException.class)
    public String handleTransactionalException(TransactionalException ex)  {
        LOGGER.error("Error al procesar la transaccion: ",ex);
        return "error_500";
    }

}
