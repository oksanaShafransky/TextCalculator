package com.textcalculator;

import java.util.logging.Logger;

public class TextCalculatorMain {
    private static Logger logger = Logger.getLogger(TextCalculatorMain.class.toString());
    private static String expression1 = "i = 0\n" +
            "j = ++i\n" +
            "x = i++ + 5\n" +
            "y = 5 + 3 * 10\n" +
            "i += y\n";
    private static String expression2 = "";

    public static void main(String[] args) {
        logger.info("Going to calc the expressions: " + expression1);
        CalculatorInputParser calculatorInputParser = new CalculatorInputParser();
        CalcExpression calcExpression = calculatorInputParser.parseExpression(expression1);
        String result = calcExpression.calculate();
        logger.info("Resul of the expressions: " + result);
    }

//    public static void main2(String[] args) {
//        while(true){
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//            try {
//                String s = br.readLine();
//
//                if (s.isEmpty()) break;
//
//                try {
//                    //calculator.evaluate(s);
//                } catch (InvalidStatementException e) {
//                    System.out.println("Invalid statement. Please try again.");
//                } catch (UndeclaredVariableException e) {
//                    System.out.println("You tried to use an undeclared variable. Please try again.");
//                } catch (UnsupportedOperationException e) {
//                    System.out.println("You used an unsupported operation. Please try again.");
//                }
//            } catch (IOException e){
//
//            }
//        }
//    }
}
