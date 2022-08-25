package com.textcalculator;

import java.util.regex.Pattern;

public class OperatorUtils {
    public static Operator parseOperator(String sequence, int offset){
        if(offset < sequence.length()){
            char operator = sequence.charAt(offset);
            switch (operator){
                case '+': return Operator.ADD;
                case '-': return Operator.SUBTRACT;
                case '*': return Operator.MULTIPLY;
                case '/': return Operator.DIVIDE;
            }
        }
        return Operator.BLANK;
    }

    public static boolean isOperator(String s) {
        return (Pattern.matches("\\+", s))
                || Pattern.matches("-", s)
                || Pattern.matches("\\*", s)
                || Pattern.matches("/", s);
    }

    public static int applyOperation(int left, Operator operator, int right){
        switch (operator){
            case ADD: return left + right;
            case SUBTRACT: return left - right;
            case MULTIPLY: return left * right;
            case DIVIDE: return left / right;
            default: return right;
        }

    }

    public static int priorityOfOperator(Operator operator){
        switch (operator){
            case ADD: return 1;
            case SUBTRACT: return 1;
            case MULTIPLY: return 2;
            case DIVIDE: return 2;
            case BLANK: return 0;
        }
        return 0;
    }
}
