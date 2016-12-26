package script.ast;

import script.Visitor;
import script.types.DoubleType;
import script.types.Type;

/**
 * Created by Adrien on 26/12/2016.
 */
public class NumericOpExp extends NumericExp {
    public enum Operator {
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULUS;

        @Override
        public String toString() {
            String[] names = { "+", "-", "*", "/", "%" };
            return names[this.ordinal()];
        }
    }

    private NumericExp lhs;
    private NumericExp rhs;
    private Operator op;

    public NumericOpExp(NumericExp lhs, NumericExp rhs, Operator op) {
        super(0);
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public String toString() {
        return "NumericOpExp{" + lhs + op + rhs + '}';
    }

    @Override
    public Type getType() {
        return DoubleType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public NumericExp getLhs() {
        return lhs;
    }

    public NumericExp getRhs() {
        return rhs;
    }

    public Operator getOp() {
        return op;
    }
}
