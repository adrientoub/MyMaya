package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 24/12/2016.
 */
public class LoopExp extends AstNode {
    private NumericExp numericExp;
    private SeqExp body;

    public NumericExp getNumericExp() {
        return numericExp;
    }

    public SeqExp getBody() {
        return body;
    }

    public LoopExp(NumericExp numericExp, SeqExp body) {
        this.numericExp = numericExp;
        this.body = body;
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
