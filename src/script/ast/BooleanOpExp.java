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
        DIFFERENT,
        LTHAN,
        GTHAN,
        LEQ,
        GEQ
    }

    private ArithmeticExp lhs;
    private ArithmeticExp rhs;
    private Operator op;

    public BooleanOpExp(ArithmeticExp lhs, ArithmeticExp rhs, Operator op) {
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

    public ArithmeticExp getLhs() {
        return lhs;
    }

    public ArithmeticExp getRhs() {
        return rhs;
    }

    public Operator getOp() {
        return op;
    }
}
