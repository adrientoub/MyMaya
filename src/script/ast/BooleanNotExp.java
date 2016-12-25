package script.ast;

import script.Visitor;

/**
 * Created by Adrien on 25/12/2016.
 */
public class BooleanNotExp extends BooleanExp {
    private BooleanExp booleanExp;

    public BooleanNotExp(BooleanExp booleanExp) {
        super(false);
        this.booleanExp = booleanExp;
    }


    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "! " + booleanExp;
    }

    public BooleanExp getBooleanExp() {
        return booleanExp;
    }
}
