package script.ast;

import script.Visitor;
import script.types.IntegerType;
import script.types.Type;

/**
 * Created by Adrien on 26/12/2016.
 */
public class NumericExp extends ArithmeticExp {
    protected double value;

    public NumericExp(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return IntegerType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
