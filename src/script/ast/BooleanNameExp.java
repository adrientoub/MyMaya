package script.ast;

import script.Visitor;

/**
 * Created by Adrien on 25/12/2016.
 */
public class BooleanNameExp extends BooleanExp {
    private String name;

    public BooleanNameExp(String name) {
        super(false);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
