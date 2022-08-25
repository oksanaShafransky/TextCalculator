package com.textcalculator;

import com.textcalculator.exceptions.InvalidExpressionException;
import com.textcalculator.exceptions.UndeclaredVariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Calculator {
    private static final Logger logger = Logger.getLogger(Calculator.class.toString());
    public HashMap<String, Integer> variablesMap = new HashMap<>();

    public void parseExpressions(List<String> expressions) throws UndeclaredVariableException {
        try {
            //build and calc expression one by one
            expressions.stream().forEach(expr -> {
                buildAndCalcExpression(expr);
            });
        } catch (InvalidExpressionException | UndeclaredVariableException e){
            throw e;
        }
    }

    private void buildAndCalcExpression(String expr){
        String expression = expr.replaceAll("\\s", "");
        String left = getLeftPartOfExpression(expression);
        if (!variablesMap.containsKey(left)) {
            variablesMap.put(left, 0);
        }
        String right = replaceAssignment(expression, expr, left);

        //only numeric values on the right
        if (Pattern.matches("[0-9]+", right)) {
            variablesMap.put(left, Integer.valueOf(right));
        } else { //numeric expressions
            AtomicReference<String> newExpression = new AtomicReference<>(right);
            //replace all variables with their values and post/pre increment/decrement
            variablesMap.forEach((key, value) -> {
                newExpression.set(replaceAllIncrementDecrement(newExpression.get()));
            });

            int res = compute(newExpression.get());
            variablesMap.put(left, res);
        }
    }

    private String getLeftPartOfExpression(String expression){
        return expression.contains("+=") || expression.contains("-=") ?
                (expression.contains("+=") ?
                expression.substring(0, expression.indexOf("+=")) : expression.substring(0, expression.indexOf("-="))) :
                expression.substring(0, expression.indexOf("="));
    }

    private String replaceAssignment(String wholeExpression, String expr, String left){
        String right = wholeExpression.substring(wholeExpression.indexOf("=") + 1);
        //+= case
        if (expr.contains("+=")) {
            right = right + "+" + variablesMap.get(left);
        }
        //-=
        if (expr.contains("-=")) {
            right = right + "-" + variablesMap.get(left);
        }
        return right;
    }

    public String replaceAllIncrementDecrement(String expr) throws UndeclaredVariableException{
        StringBuilder newRightExpression = new StringBuilder();
        boolean isPrefix = false;
        for(int i = 0; i < expr.length(); i++){
            StringBuilder varStr = new StringBuilder();
            //is numeric values
            if(Pattern.matches("[0-9]+", String.valueOf(expr.charAt(i)))){
                newRightExpression.append(expr.charAt(i));
            } else if (Pattern.matches("[a-z]", String.valueOf(expr.charAt(i)))){ //is variables
                varStr.append(expr.charAt(i));
                int offset = i;
                while(i < expr.length()-1 && variablesMap.get(varStr.toString()) == null){
                    varStr.append(expr.charAt(++i));
                }
                if(varStr.toString().isEmpty() || variablesMap.get(varStr.toString())==null){
                    throw new UndeclaredVariableException("Invalid variable " + varStr);
                }
                if(isPrefix){
                    calcPrefix(expr, varStr.toString(), offset);
                }

                newRightExpression.append(variablesMap.get(varStr.toString()));

                i = calcPostfix(expr, varStr.toString(), i);

            } else if(OperatorUtils.isOperator(String.valueOf(expr.charAt(i)))){ //is operator
                if(expr.charAt(i) == expr.charAt(i+1)){
                    isPrefix = true;
                    i++;
                } else {
                    newRightExpression.append(expr.charAt(i));
                }
            }
        }
        return newRightExpression.toString();
    }

    private void calcPrefix(String expr, String varStr, int offset){
        //pre-increment
        if(expr.charAt(offset-1)=='+' && expr.charAt(offset-2)=='+'){
            variablesMap.put(varStr, variablesMap.get(varStr)+1);
        }
        //pre-decrement
        if(expr.charAt(offset-1)=='-' && expr.charAt(offset-2)=='-'){
            variablesMap.put(varStr, variablesMap.get(varStr)-1);
        }
    }

    private int calcPostfix(String expr, String varStr, int i){
        //post-increment
        if((i + 2) < expr.length() && expr.charAt(i+1)=='+' && expr.charAt(i+2)=='+'){
            variablesMap.put(varStr, variablesMap.get(varStr)+1);
            i+=2;
        }
        //post-decrement
        if((i + 2) < expr.length() && expr.charAt(i+1)=='-' && expr.charAt(i+2)=='-'){
            variablesMap.put(varStr, variablesMap.get(varStr)-1);
            i+=2;
        }
        return i;
    }

    public int compute(String sequence){
        Stack<Integer> numberStack = new Stack();
        Stack<Operator> operatorStack = new Stack();
        for(int i = 0; i < sequence.length(); i++){
            try{
                int number = parseNumber(sequence, i);
                numberStack.push(number);

                i += Integer.toString(number).length();
                if(i >= sequence.length()){
                    break;
                }

                Operator op = OperatorUtils.parseOperator(sequence, i);
                collapseTop(numberStack, operatorStack, op);
                operatorStack.push(op);
            } catch(NumberFormatException ex){
                throw new InvalidExpressionException("The number format is incorrect: " + ex.getMessage());
            }
        }
        collapseTop(numberStack, operatorStack, Operator.BLANK);
        if(numberStack.size() == 1 && operatorStack.size() == 0){
            return numberStack.pop();
        }
        return 0;
    }

    private void collapseTop(Stack<Integer> numberStack, Stack<Operator> operatorStack, Operator futureTop){
        while(numberStack.size() >= 2 && operatorStack.size() >= 1){
            if(OperatorUtils.priorityOfOperator(futureTop) <= OperatorUtils.priorityOfOperator(operatorStack.peek())){
                int second = numberStack.pop();
                int first = numberStack.pop();
                Operator op = operatorStack.pop();
                int result = OperatorUtils.applyOperation(first, op, second);
                numberStack.push(result);
            } else{
                break;
            }
        }
    }

    private int parseNumber(String sequence, int offset){
        StringBuilder sb = new StringBuilder();
        while(offset < sequence.length() && Character.isDigit(sequence.charAt(offset))){
            sb.append(sequence.charAt(offset));
            offset++;
        }
        return Integer.parseInt(sb.toString());
    }

    public String getExpressionResults(){
        StringBuilder result = new StringBuilder();
        List<String> resultsList = new ArrayList<>();
        result.append("{");
        variablesMap.forEach((key, value)->{
            resultsList.add(key + "=" + value);
        });
        result.append(String.join(",",resultsList));
        result.append("}");
        return result.toString();
    }
}