package script.ast;

import script.Visitor;
import script.types.BooleanType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class BooleanExp extends AstNode {
    protected boolean value;

    public BooleanExp(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return BooleanType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanExp{" + value + '}';
    }
}
