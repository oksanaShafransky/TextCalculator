package com.textcalculator.exceptions;


public class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException(String errorMessage){
        super(errorMessage);
    }
}
