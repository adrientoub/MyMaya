package script.ast;

import script.Visitor;
import script.lexer.Token;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 19/12/2016.
 */
public class VarDef extends AstNode {
    private String name;
    private Token value;

    public String getName() {
        return name;
    }

    public Token getValue() {
        return value;
    }

    public VarDef(String name, Token value) {
        this.name = name;
        this.value = value;
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
        return "var " + name + " := " + value;
    }
}
