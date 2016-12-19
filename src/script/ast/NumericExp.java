package script.ast;

import script.Visitor;
import script.types.DoubleType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class NumericExp extends AstNode {
    @Override
    public Type getType() {
        return DoubleType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
