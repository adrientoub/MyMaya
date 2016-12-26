package script;

import script.ast.*;

import java.util.List;

/**
 * Created by Adrien on 25/12/2016.
 */
public class ArithmeticParser {
    private enum Operator {
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULUS,
        EQUAL,
        DIFFERENT,
        OR,
        AND
    }

    private List<String> tokens;
    private int position;
    private boolean fail = false;

    public ArithmeticParser(List<String> tokens) {
        this.tokens = tokens;
        this.position = tokens.size() - 1;
    }

    private Operator getOperator(String token) {
        switch (token) {
            case "==":
                return Operator.EQUAL;
            case "!=":
                return Operator.DIFFERENT;
            case "&&":
                return Operator.AND;
            case "||":
                return Operator.OR;
            case "-":
                return Operator.MINUS;
            case "+":
                return Operator.PLUS;
            case "*":
                return Operator.TIMES;
            case "/":
                return Operator.DIVIDE;
            case "%":
                return Operator.MODULUS;
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

        Operator op = getOperator(token);

        if (op == null) {
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
                    // TODO: allow NumericNameExp
                    return new BooleanNameExp(token);
            }
        }

        ArithmeticExp rhs = parse();
        if (rhs == null) {
            return null;
        }
        ArithmeticExp lhs = parse();
        if (lhs == null) {
            return null;
        }
        if (rhs instanceof NumericExp && lhs instanceof NumericExp) {
            NumericExp nlhs = (NumericExp) lhs;
            NumericExp nrhs = (NumericExp) rhs;
            if (op == Operator.EQUAL) {
                return new BooleanOpNumericExp(nlhs, nrhs, BooleanOpNumericExp.Operator.EQUAL);
            } else if (op == Operator.DIFFERENT) {
                return new BooleanOpNumericExp(nlhs, nrhs, BooleanOpNumericExp.Operator.DIFFERENT);
            } else if (op == Operator.PLUS) {
                return new NumericOpExp(nlhs, nrhs, NumericOpExp.Operator.PLUS);
            } else if (op == Operator.MINUS) {
                return new NumericOpExp(nlhs, nrhs, NumericOpExp.Operator.MINUS);
            } else if (op == Operator.TIMES) {
                return new NumericOpExp(nlhs, nrhs, NumericOpExp.Operator.TIMES);
            } else if (op == Operator.DIVIDE) {
                return new NumericOpExp(nlhs, nrhs, NumericOpExp.Operator.DIVIDE);
            } else if (op == Operator.MODULUS) {
                return new NumericOpExp(nlhs, nrhs, NumericOpExp.Operator.MODULUS);
            } else {
                System.err.println("Error: trying to use operator " + op + " on numerics");
                return null;
            }
        } else if (rhs instanceof BooleanExp && lhs instanceof BooleanExp) {
            BooleanExp blhs = (BooleanExp) lhs;
            BooleanExp brhs = (BooleanExp) rhs;
            BooleanOpExp.Operator bop;
            switch (op) {
                case EQUAL:
                    bop = BooleanOpExp.Operator.EQUAL;
                    break;
                case DIFFERENT:
                    bop = BooleanOpExp.Operator.DIFFERENT;
                    break;
                case OR:
                    bop = BooleanOpExp.Operator.OR;
                    break;
                case AND:
                    bop = BooleanOpExp.Operator.AND;
                    break;
                default:
                    System.err.println("Error: trying to use operator " + op + " on booleans");
                    return null;
            }
            return new BooleanOpExp(blhs, brhs, bop);
        } else {
            if (op == Operator.EQUAL) {
                return new BooleanExp(false);
            } else if (op == Operator.DIFFERENT) {
                return new BooleanExp(true);
            } else {
                System.err.println("Error: cannot do operator " + op + " between two expressions of different types (" + lhs + " and " + rhs + ")");
                return null;
            }
        }
    }

    public static ArithmeticExp parse(List<String> tokens) {
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ArithmeticExp arithmetic = parser.parse();

        return parser.fail ? null: arithmetic;
    }
}
