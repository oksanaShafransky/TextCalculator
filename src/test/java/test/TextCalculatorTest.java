package test;

import com.textcalculator.Calculator;
import com.textcalculator.exceptions.UndeclaredVariableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextCalculatorTest {
    Calculator calculator = new Calculator();
    List<String> expressionList = new ArrayList<>();

    @BeforeEach
    void beforeTest(){
        expressionList.clear();
    }

    @DisplayName("test assignment")
    @Test
    void testBasicAssignment() throws Exception {
        String expression = "i = 0";
        expressionList.add(expression);
        calculator.parseExpressions(expressionList);
        assertEquals("{i=0}",calculator.getExpressionResults());
    }

    @DisplayName("test addition")
    @Test
    void testAddition() throws Exception {
        String expression = "i = 5 + 6";
        expressionList.add(expression);
        calculator.parseExpressions(expressionList);
        assertEquals("{i=11}",calculator.getExpressionResults());
    }

    @DisplayName("test subtraction")
    @Test
    void testSubtraction() throws Exception {
        String expression = "i = 6 - 5";
        expressionList.add(expression);
        calculator.parseExpressions(expressionList);
        assertEquals("{i=1}",calculator.getExpressionResults());
    }

    @DisplayName("test multiplication")
    @Test
    void testMultiplication() throws Exception {
        String expression = "i = 6 * 5";
        expressionList.add(expression);
        calculator.parseExpressions(expressionList);
        assertEquals("{i=30}",calculator.getExpressionResults());
    }

    @DisplayName("test division")
    @Test
    void testDivision() throws Exception {
        String expression = "i = 6 / 2";
        expressionList.add(expression);
        calculator.parseExpressions(expressionList);
        assertEquals("{i=3}",calculator.getExpressionResults());
    }

    @DisplayName("test pre-increment")
    @Test
    void testPreIncrement() throws Exception {
        expressionList.add("i = 0");
        expressionList.add("j = ++i");
        calculator.parseExpressions(expressionList);
        assertEquals("{i=1,j=1}",calculator.getExpressionResults());
    }

    @DisplayName("test post-increment")
    @Test
    void testPostIncrement() throws Exception {
        expressionList.add("i = 0");
        expressionList.add("x = i++ + 5");
        calculator.parseExpressions(expressionList);
        assertEquals("{x=5,i=1}",calculator.getExpressionResults());
    }

    @DisplayName("test addition-assignment")
    @Test
    void testAdditionAssignment() throws Exception {
        expressionList.add("i = 0");
        expressionList.add("y = 5 + 3 * 10");
        expressionList.add("i += y");
        calculator.parseExpressions(expressionList);
        assertEquals("{i=35,y=35}",calculator.getExpressionResults());
    }

    @DisplayName("test subtraction-assignment")
    @Test
    void testSubtractionAssignment() throws Exception {
        expressionList.add("i = 1");
        expressionList.add("y = 5 + 3 * 10");
        expressionList.add("i -= y");
        calculator.parseExpressions(expressionList);
        assertEquals("{i=34,y=35}",calculator.getExpressionResults());
    }
}
