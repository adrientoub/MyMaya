package script;

import script.ast.AstNode;
import script.ast.CallExp;
import script.ast.FunctionDef;
import script.ast.SeqExp;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Visitor {
    public void visit(AstNode astNode) {
        System.err.println(astNode);
        System.err.println(this.getClass().getCanonicalName());
        System.exit(1);
    }

    public void visit(SeqExp seqExp) {
        for (AstNode astNode: seqExp.getExps()) {
            astNode.accept(this);
        }
    }

    public void visit(CallExp callExp) {
    }

    public void visit(FunctionDef functionDef) {
        functionDef.getExps().accept(this);
    }
}
