package script.lexer;

import script.types.Type;

/**
 * Created by Adrien on 22/12/2016.
 */
public class OperatorToken extends Token {
    public Operator getOperator() {
        return operator;
    }

    public enum Operator {
        ASSIGN,
        PLUS,
        MINUS;

        @Override
        public String toString() {
            if (this == ASSIGN) {
                return ":=";
            } else if (this == PLUS) {
                return "+";
            } else {
                return "-";
            }
        }
    }

    private Operator operator;

    public OperatorToken(Operator operator, int lineNumber) {
        super(lineNumber);
        this.operator = operator;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String toString() {
        return "OperatorToken{" + operator + '}';
    }
}
