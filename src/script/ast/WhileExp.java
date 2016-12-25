package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 19/12/2016.
 */
public class WhileExp extends AstNode {
    private BooleanExp booleanExp;
    private SeqExp body;

    public WhileExp(BooleanExp booleanExp, SeqExp body) {
        this.booleanExp = booleanExp;
        this.body = body;
    }

    public BooleanExp getBooleanExp() {
        return booleanExp;
    }

    public SeqExp getBody() {
        return body;
    }

    @Override
    public Type getType() {
        return VoidType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "WhileExp{" + "booleanExp=" + booleanExp + ", body=" + body + '}';
    }
}
