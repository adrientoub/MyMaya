package script;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Adrien on 25/12/2016.
 */
public class ToPolish {
    // Implements the Shunting-yard algorithm
    private static int getPrecedence(String operator) {
        switch (operator) {
            case "(":
            case ")":
                return 0;
            case "==":
            case "!=":
                return 2;
            case "&&":
            case "||":
                return 3;
            case "!":
                return 4;
            case "+":
            case "-":
                return 5;
            case "*":
            case "/":
            case "%":
                return 6;
            default:
                return 1;
        }
    }

    private static boolean isOperator(String string) {
        String[] operators = { "==", "!=", "&&", "||", "!", "+", "-", "*", "/", "%" };
        for (String operator: operators) {
            if (operator.equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> convert(List<String> tokens) {
        int pos = 0;

        List<String> output = new ArrayList<>();
        Deque<String> operators = new ArrayDeque<>();

        while (pos < tokens.size()) {
            String tok = tokens.get(pos++);
            if (tok.equals("(")) {
                operators.push(tok);
            } else if (tok.equals(")")) {
                while (operators.peek() != null && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (operators.peek() == null || !operators.peek().equals("(")) {
                    System.err.println("Error: no left parentheses to match.");
                    return null;
                } else {
                    operators.pop();
                }
            } else if (isOperator(tok)) {
                String top;
                while ((top = operators.peek()) != null) {
                    if (getPrecedence(tok) <= getPrecedence(top)) {
                        output.add(operators.pop());
                    } else {
                        break;
                    }
                }
                operators.push(tok);
            } else {
                output.add(tok);
            }
        }
        while (operators.size() != 0) {
            if (operators.peek().equals("(")) {
                System.err.println("Error: missing right parentheses.");
                return null;
            } else {
                output.add(operators.pop());
            }
        }
        System.out.println(output);
        return output;
    }
}
