package script.ast;

import script.Visitor;
import script.types.StringType;
import script.types.Type;

/**
 * Created by Adrien on 29/12/2016.
 */
public class StringExp extends AstNode {
    private String value;

    public StringExp(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return StringType.getInstance();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getValue() {
        return value;
    }
}
