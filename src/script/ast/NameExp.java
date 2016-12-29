package script.ast;

import script.Execution;
import script.Visitor;
import script.lexer.Token;
import script.types.Type;

/**
 * Created by Adrien on 25/12/2016.
 */
public class NameExp extends ArithmeticExp {
    private String name;

    public NameExp(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        Token var = Execution.getInstance().getVariableToken(name);
        if (var == null) {
            return null;
        }
        return var.getType();
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
