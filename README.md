# TextCalculator
Calculate mathematical expressions

Objective: Implement a text based calculator application.
Input: The input is a series of assignment expressions. The syntax is a subset of Java numeric expressions and operators.
Output: At the end of evaluating the series, the value of each variable is printed out.

* This text calculator calculates multiple expressions.
* The expressions should contain on the left side a variable (can be string [a-z]), assignment (=,+=,-=)
* and on the left any expression using existing variables, pre-post-fix (++,--) and numerics ([0-9]).
* Supported operations: addition, subtraction, multiplication, division, pre-increment, post-increment, pre-decrement, post-decrement.


####Example
Following is a series of valid inputs for the program:
i = 0
j = ++ i
x = i++ + 5
y = 5 + 3 * 10
i += y
Output:
(i=37,j=1,x=6,y=35)

