package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 19/12/2016.
 */
public class IfExp extends AstNode {
    private BooleanExp booleanExp;
    private SeqExp ifClause;
    private SeqExp elseClause;

    public IfExp(BooleanExp booleanExp, SeqExp ifClause, SeqExp elseClause) {
        this.booleanExp = booleanExp;
        this.ifClause = ifClause;
        this.elseClause = elseClause;
    }

    public BooleanExp getBooleanExp() {
        return booleanExp;
    }

    public SeqExp getIfClause() {
        return ifClause;
    }

    public SeqExp getElseClause() {
        return elseClause;
    }

    @Override
    public Type getType() {
        return VoidType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
