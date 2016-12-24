package script.ast;

import script.Visitor;
import script.lexer.Token;
import script.types.Type;
import script.types.VoidType;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class FunctionDef extends AstNode {
    private String functionName;
    private ArgumentListDef arguments;
    private SeqExp exps;

    public FunctionDef(String functionName, List<String> arguments, SeqExp exps) {
        this.functionName = functionName;
        this.arguments = new ArgumentListDef(arguments);
        this.exps = exps;
    }

    public String getFunctionName() {
        return functionName;
    }

    public SeqExp getExps() {
        return exps;
    }

    public ArgumentListDef getArguments() {
        return arguments;
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
        return "FunctionDef{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                ", exps=" + exps +
                '}';
    }
}
