package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 19/12/2016.
 */
public class VarDef extends AstNode {
    @Override
    public Type getType() {
        return VoidType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
