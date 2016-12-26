package script.ast;

import script.Visitor;

/**
 * Created by Adrien on 26/12/2016.
 */
public class BooleanOpNumericExp extends BooleanExp {
    public enum Operator {
        EQUAL,
        DIFFERENT
    }

    private NumericExp lhs;
    private NumericExp rhs;
    private Operator op;

    public BooleanOpNumericExp(NumericExp lhs, NumericExp rhs, Operator op) {
        super(false);
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "BooleanOpNumericExp{" + lhs + ", " + op + ", rhs=" + rhs + '}';
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
