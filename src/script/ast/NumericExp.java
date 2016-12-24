package script.ast;

import script.Visitor;
import script.types.DoubleType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class NumericExp extends AstNode {
    // TODO: handle both int and double
    int value;

    public NumericExp(int value) {
        this.value = value;
    }

    public NumericExp(double value) {
        this.value = (int) value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return DoubleType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
