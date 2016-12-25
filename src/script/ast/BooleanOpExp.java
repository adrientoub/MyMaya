package script.ast;

import script.Visitor;

/**
 * Created by Adrien on 25/12/2016.
 */
public class BooleanOpExp extends BooleanExp {
    public enum Operator {
        OR,
        AND,
        EQUAL,
        DIFFERENT
    }

    private BooleanExp lhs;
    private BooleanExp rhs;
    private Operator op;

    public BooleanOpExp(BooleanExp lhs, BooleanExp rhs, Operator op) {
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
        return "BooleanOpExp{" + lhs + ", " + op + ", rhs=" + rhs + '}';
    }

    public BooleanExp getLhs() {
        return lhs;
    }

    public BooleanExp getRhs() {
        return rhs;
    }

    public Operator getOp() {
        return op;
    }
}
