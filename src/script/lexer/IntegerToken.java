package script.lexer;

import script.types.IntegerType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class IntegerToken extends Token {
    private int i;

    public IntegerToken(int i) {
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
}