package script.lexer;

import script.types.IntegerType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class IntegerToken extends NumberToken {
    private int i;

    public IntegerToken(int i, int lineNumber) {
        super(lineNumber);
        this.i = i;
    }

    @Override
    public Type getType() {
        return IntegerType.getInstance();
    }

    @Override
    public String toString() {
        return "IntegerToken{" + i + '}';
    }

    @Override
    public double getValue() {
        return i;
    }
}
