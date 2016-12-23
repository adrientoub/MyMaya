package script.ast;

import script.Visitor;
import script.lexer.Token;
import script.types.ArgumentListType;
import script.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class CallExp extends AstNode {
    private String functionName;
    private List<Token> arguments;

    public CallExp(String functionName, List<Token> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Type getType() {
        // TODO: handle return types
        return null;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "CallExp{" + functionName + " arguments=" + arguments + '}';
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Token> getArguments() {
        return arguments;
    }

    public Type getArgumentType() {
        List<Type> types = new ArrayList<>();
        for (int i = 0; i < arguments.size(); i++) {
            types.add(arguments.get(i).getType());
        }
        return new ArgumentListType(types);
    }
}
