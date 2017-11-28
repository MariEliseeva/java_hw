package spbau.eliseeva.mock;

/**
 * The class converts an expression into Reversed Polish Notation and
 * evaluates it. Has two stacks inside -- for numbers and for operations,
 * stacks' implementations are given in constructor. The class uses shunting-yard
 * algorithm for converting to polish notation. Also has a static method 'check'
 * for finding if an expression has correct symbols.
 */
public class Calculator {
    /** Stack for numbers, which are met inside the expression */
    private Stack<Integer> stackNumbers;
    /**
     * Stack for operations, which are met inside the expression.
     * They can be '+', '-', '*', '/' or '(' and ')'.
     */
    private Stack<Character> stackOperands;

    /**
     * Creates calculator with two given stacks for numbers and for operations.
     * @param stackNumbers stack for numbers.
     * @param stackOperands stack for operations
     */
    Calculator(Stack<Integer> stackNumbers, Stack<Character> stackOperands) {
        this.stackNumbers = stackNumbers;
        this.stackOperands = stackOperands;
    }

    /**
     * Evaluates the expression in a polish notation.
     * Goes through all the elements in the expression, if a number -- puts in
     * on stack, if an operation -- takes two numbers from the stack,
     * operate on them and puts the result back on stack. After all
     * the result in on the stack of numbers top.
     * @param expression expression in a polish notation to evaluate
     * @return result of the expression's evaluation
     * @throws IllegalArgumentException thrown if incorrect expression's form or division by zero
     */
    public int evaluate(String expression) throws IllegalArgumentException {
        stackNumbers.clear();
        for (int pos = 0; pos < expression.length();) {
            if (isOperation(expression.charAt(pos))) {
                calcOneOperation(expression.charAt(pos));
                pos += 2;
            } else {
                pos = parseNumber(expression, pos) + 1;
            }
        }
        return stackNumbers.top();
    }

    /**
     * Converts an expression into the Reversed Polish Notation.
     * Goes through all the elements in the expression, if a number -- puts in
     * the answer, if an operation -- while operations on stack has greater
     * priority than the priority of the current -- puts them in the answer.
     * Then if current operation is ')' -- puts in the answer all before '(' in stack.
     * Else -- puts current operation in stack.
     * @param expression expression to convert
     * @return expression in the polish notation
     * @throws IllegalArgumentException thrown if wrong brackets
     */
    public String toPolish(String expression) throws IllegalArgumentException {
        StringBuilder answer = new StringBuilder();
        stackOperands.push('(');
        expression += ')';
        for (int pos = 0; pos < expression.length();) {
            if (isOperation(expression.charAt(pos))) {
                while (!stackOperands.isEmpty() && goodPriority(expression.charAt(pos))
                        && (stackOperands.top() != '(')) {
                    answer.append(stackOperands.top()).append(" ");
                    stackOperands.pop();
                }
                if (expression.charAt(pos) == ')') {
                    while (stackOperands.top() != '(') {
                        answer.append(stackOperands.top()).append(" ");
                        stackOperands.pop();
                        if (stackOperands.isEmpty()) {
                            throw new IllegalArgumentException("Wrong brackets in expression.");
                        }
                    }
                    stackOperands.pop();
                } else {
                    stackOperands.push(expression.charAt(pos));
                }
                pos++;
            } else {
                pos = parseNumber(expression, pos);
                answer.append(stackNumbers.top().toString()).append(" ");
            }
        }
        while (!stackOperands.isEmpty()) {
            if (stackOperands.top() == '(' || stackOperands.top() == ')') {
                throw new IllegalArgumentException("Wrong brackets in expression.");
            }
            answer.append(stackOperands.top()).append(" ");
            stackOperands.pop();
        }
        return answer.toString();
    }

    /**
     * Checks if an expression contains only numbers and appropriate operations
     * @param expression expression to check
     * @return true if no wrong symbols, otherwise false
     */
    public static boolean check(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            if (!isOperation(expression.charAt(i)) && !isDigit(expression.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads an elements from the string while they are digits, convert them from
     * string ro integer number.
     * @param expression expression to find a number
     * @param pos position to start searching from
     * @return position after searching
     */
    private int parseNumber(String expression, int pos) {
        Integer number = 0;
        while (expression.length() > pos && isDigit(expression.charAt(pos))) {
            number *= 10;
            number += (expression.charAt(pos) - '0');
            pos++;
        }
        stackNumbers.push(number);
        return pos;
    }

    /**
     * Do one operation -- takes two elements from the stack,
     * do an operation and puts the result back on the stack.
     * @param operation operation to apply
     * @throws IllegalArgumentException thrown if wrong expression
     */
    private void calcOneOperation(char operation) throws IllegalArgumentException {
        if (stackNumbers.isEmpty()) {
            throw new IllegalArgumentException("Expression is not correct.");
        }
        Integer rightValue = stackNumbers.top();
        stackNumbers.pop();
        if (stackNumbers.isEmpty()) {
            throw new IllegalArgumentException("Expression is not correct.");
        }
        Integer leftValue = stackNumbers.top();
        stackNumbers.pop();
        if (operation == '-') {
            leftValue -= rightValue;
        } else if (operation == '+') {
            leftValue += rightValue;
        } else if (operation == '*') {
            leftValue *= rightValue;
        } else if (operation == '/') {
            if (rightValue == 0) {
                throw new IllegalArgumentException("Division by 0.");
            }
            leftValue /= rightValue;
        }
        stackNumbers.push(leftValue);
    }

    /**
     * Compares the priority of the given operation and
     * the operation on the top of the stack.
     * @param c operation to check
     * @return true if operation on the stack has greater priority
     */
    private boolean goodPriority(char c) {
        return operationPriority(c) <= operationPriority(stackOperands.top());
    }

    /**
     * Checks if an element is a digit or not.
     * @param c element
     * @return true if digit, false if not
     */
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Checks if an element is an operation.
     * @param c element
     * @return true if operation, false if not
     */
    private static boolean isOperation(char c) {
        return c == '(' || c == ')' || c == '+' ||
                c == '-' || c == '*' || c == '/';
    }

    /**
     * Returns the priority of the operation.
     * We can add more operations there if needed.
     * @param c an operation
     * @return its priority
     */
    private static int operationPriority(char c) {
        switch (c) {
            case ')' : return 0;
            case '-' : return 1;
            case '+' : return 1;
            case '*' : return 2;
            case '/' : return 2;
        }
        return 3;
    }

}
