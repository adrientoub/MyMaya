package script;

import script.ast.*;

import java.util.List;

/**
 * Created by Adrien on 25/12/2016.
 */
public class ArithmeticParser {
    private List<String> tokens;
    private int position;
    private boolean fail = false;

    public ArithmeticParser(List<String> tokens) {
        this.tokens = tokens;
        this.position = tokens.size() - 1;
    }

    private BooleanOpExp.Operator getOperator(String token) {
        switch (token) {
            case "==":
                return BooleanOpExp.Operator.EQUAL;
            case "!=":
                return BooleanOpExp.Operator.DIFFERENT;
            case "<":
                return BooleanOpExp.Operator.LTHAN;
            case "<=":
                return BooleanOpExp.Operator.LEQ;
            case ">=":
                return BooleanOpExp.Operator.GEQ;
            case ">":
                return BooleanOpExp.Operator.GTHAN;
            case "&&":
                return BooleanOpExp.Operator.AND;
            case "||":
                return BooleanOpExp.Operator.OR;
            default:
                return null;
        }
    }


    private NumericOpExp.Operator getValueOperator(String token) {
        switch (token) {
            case "-":
                return NumericOpExp.Operator.MINUS;
            case "+":
                return NumericOpExp.Operator.PLUS;
            case "*":
                return NumericOpExp.Operator.TIMES;
            case "/":
                return NumericOpExp.Operator.DIVIDE;
            case "%":
                return NumericOpExp.Operator.MODULUS;
            default:
                return null;
        }
    }

    private ArithmeticExp parse() {
        if (position < 0) {
            fail = true;
            System.err.println("Error: Missing a token.");
            return null;
        }
        String token = tokens.get(position--);

        try {
            double val = Double.valueOf(token);
            return new NumericExp(val);
        } catch (NumberFormatException ignored) {}

        BooleanOpExp.Operator bop = getOperator(token);
        NumericOpExp.Operator nop = getValueOperator(token);
        if (bop != null || nop != null) {
            ArithmeticExp rhs = parse();
            if (rhs == null) {
                return null;
            }
            ArithmeticExp lhs = parse();
            if (lhs == null) {
                return null;
            }
            if (bop != null) {
                return new BooleanOpExp(lhs, rhs, bop);
            } else {
                return new NumericOpExp(lhs, rhs, nop);
            }
        }

        switch (token) {
            case "!":
                ArithmeticExp arithmeticExp = parse();
                if (arithmeticExp instanceof BooleanExp) {
                    return new BooleanNotExp((BooleanExp) arithmeticExp);
                } else {
                    System.err.println("Error: expecting BooleanExp");
                    return null;
                }
            case "true":
            case "false":
                return new BooleanExp(token.equals("true"));
            default:
                return new NameExp(token);
        }
    }

    public static ArithmeticExp parse(List<String> tokens) {
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ArithmeticExp arithmetic = parser.parse();

        return parser.fail ? null: arithmetic;
    }
}
