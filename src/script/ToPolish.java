package script;

import script.lexer.StringToken;
import script.lexer.Token;

import java.util.*;

/**
 * Created by Adrien on 25/12/2016.
 */
public class ToPolish {
    // Implements the Shunting-yard algorithm
    public static int getPrecedence(String operator) {
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
            default:
                return 1;
        }
    }

    private static boolean isOperator(String string) {
        switch (string) {
            case "==":
            case "!=":
            case "||":
            case "&&":
            case "!":
                return true;
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
                    System.err.println("ERROR");
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
                System.err.println("ERROR");
                return null;
            } else {
                output.add(operators.pop());
            }
        }
        System.out.println(output);
        return output;
    }
}
