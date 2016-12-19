package script.ast;

import script.Visitor;
import script.types.BooleanType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class BooleanExp extends AstNode {
    @Override
    public Type getType() {
        return BooleanType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
