package com.textcalculator.exceptions;


public class UndeclaredVariableException extends RuntimeException {
    public UndeclaredVariableException(String errorMessage){
        super(errorMessage);
    }
}
