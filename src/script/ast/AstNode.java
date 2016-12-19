package script.ast;

import script.Visitor;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class AstNode {
    public abstract Type getType();

    public abstract void accept(Visitor v);
}
