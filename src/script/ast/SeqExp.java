package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class SeqExp extends AstNode {
    private List<AstNode> exps;

    public SeqExp(List<AstNode> exps) {
        this.exps = exps;
    }

    @Override
    public Type getType() {
        return VoidType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public void add(AstNode astNode) {
        exps.add(astNode);
    }

    @Override
    public String toString() {
        return "SeqExp{" +
                "exps=" + exps +
                '}';
    }

    public List<AstNode> getExps() {
        return exps;
    }
}
