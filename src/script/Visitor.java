package script;

import script.ast.*;

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

    public void visit(LoopExp loopExp) {
    }

    public void visit(VarDef varDef) {
    }

    public void visit(CallExp callExp) {
    }

    public void visit(IfExp ifExp) {
    }

    public void visit(NumericExp numericExp) {
    }

    public void visit(BooleanOpNumericExp booleanOpNumericExp) {
    }

    public void visit(NumericOpExp numericOpExp) {
    }

    public void visit(WhileExp whileExp) {
        whileExp.getBooleanExp().accept(this);
        whileExp.getBody().accept(this);
    }

    public void visit(BooleanOpExp booleanOpExp) {
        booleanOpExp.getLhs().accept(this);
        booleanOpExp.getRhs().accept(this);
    }

    public void visit(BooleanNameExp booleanNameExp) {
    }

    public void visit(BooleanNotExp booleanNotExp) {
    }

    public void visit(BooleanExp booleanExp) {
    }

    public void visit(FunctionDef functionDef) {
        functionDef.getExps().accept(this);
    }
}
