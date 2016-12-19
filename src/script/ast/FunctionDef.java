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
    private List<Token> arguments;
    private List<AstNode> exps;

    public FunctionDef(String functionName, List<Token> arguments, List<AstNode> exps) {
        this.functionName = functionName;
        this.arguments = arguments;
        this.exps = exps;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Token> getArguments() {
        return arguments;
    }

    public List<AstNode> getExps() {
        return exps;
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
