# TextCalculator
Calculate mathematical expressions

Objective: Implement a text based calculator application.
Input: The input is a series of assignment expressions. The syntax is a subset of Java numeric expressions and operators.
Output: At the end of evaluating the series, the value of each variable is printed out.

* This text calculator calculates multiple expressions.
* The expressions should contain on the left side a variable (can be string of [a-z]), assignment (=,+=,-=)
* and on the right side any expression using existing variables, pre-post-fix (++,--) and numerics ([0-9]).
* Supported operations: addition, subtraction, multiplication, division, pre-increment, post-increment, pre-decrement, post-decrement.


####Example
Following is a series of valid inputs for the program:
i = 0
j = ++ i
xx = i++ + 5
yy = 5 + 3 * 10
i += yy
Output:
(i=37,j=1,xx=6,yy=35)

To run the calculator:
1. Go to the input.txt file under resources folder and define expressions.
2. Run TextCalculatorMain.java.

To run the tests:
1. Go to the src/test/java/test.
2. Run TextCalculatorText.java

