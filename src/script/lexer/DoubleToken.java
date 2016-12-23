package script.lexer;

import script.types.DoubleType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class DoubleToken extends NumberToken {
    private double d;

    public DoubleToken(double d) {
        this.d = d;
    }

    @Override
    public Type getType() {
        return DoubleType.getInstance();
    }

    @Override
    public String toString() {
        return "DoubleToken{" + d + '}';
    }

    public double getValue() {
        return d;
    }
}
