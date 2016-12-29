package script.ast;

import script.Visitor;
import script.types.Type;
import script.types.VoidType;

/**
 * Created by Adrien on 19/12/2016.
 */
public class VarDef extends AstNode {
    private String name;
    private AstNode value;

    public String getName() {
        return name;
    }

    public AstNode getValue() {
        return value;
    }

    public VarDef(String name, AstNode value) {
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
