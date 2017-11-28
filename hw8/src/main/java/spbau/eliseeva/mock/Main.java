package spbau.eliseeva.mock;

import java.util.Scanner;

/**
 * Reads the expression, converts in to polish notation and
 * writes with the answer. If wrong expression -- tells it.
 * */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;
        Calculator calculator = new Calculator(new MyStack<>(), new MyStack<>());

        for (;;) {
            boolean success = true;
            System.out.println("Write an expression: ");
            expression = scanner.nextLine();
            if (!Calculator.check(expression)) {
                System.out.println("Wrong symbols in the expression.");
                success = false;
            }
            if (success) {
                try {
                    String polish = calculator.toPolish(expression);
                    System.out.print(polish + "= " + calculator.evaluate(polish));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    success = false;
                }
                if (success) {
                    break;
                }
            }
            System.out.println("Try again.");
        }
    }
}
