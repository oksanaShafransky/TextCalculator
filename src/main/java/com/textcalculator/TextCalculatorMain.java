package com.textcalculator;


import com.textcalculator.exceptions.InvalidExpressionException;
import com.textcalculator.exceptions.UndeclaredVariableException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This text calculator calculates multiple expressions.
 * The expressions should contain on the left side a variable (can be string [a-z]), assignment (=,+=,-=)
 * and on the left any expression using existing variables, pre-post-fix (++,--) and numerics ([0-9]).
 * Supported operations: addition, subtraction, multiplication, division, pre-increment, post-increment, pre-decrement, post-decrement.
 */
public class TextCalculatorMain {
    private static final Logger logger = Logger.getLogger(TextCalculatorMain.class.toString());
    private static final String INPUT_PATH = "src/main/resources/input.txt";
    public static void main(String[] args) {
        try {
            //read expression from the input file
            Scanner scanner = new Scanner(new File(INPUT_PATH));
            List<String> expressions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String token = scanner.nextLine();
                expressions.add(token);
            }

            System.out.println("Starting text calculator for the following expressions: " + expressions);
            Calculator calculator = new Calculator();
            calculator.parseExpressions(expressions);
            System.out.println("The result of the expressions calculation is: ");
            System.out.println(calculator.getExpressionResults());
        } catch (UndeclaredVariableException | InvalidExpressionException | FileNotFoundException e){
            logger.log(Level.SEVERE,"Error on text calculator: " + e.getMessage());
        }
    }
}
