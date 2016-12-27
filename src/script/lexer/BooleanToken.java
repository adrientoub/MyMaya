package script.lexer;

import script.types.BooleanType;
import script.types.Type;

/**
 * Created by Adrien on 25/12/2016.
 */
public class BooleanToken extends Token {
    private boolean value;

    public BooleanToken(int lineNumber, boolean value) {
        super(lineNumber);
        this.value = value;
    }

    @Override
    public Type getType() {
        return BooleanType.getInstance();
    }

    public boolean getBooleanValue() {
        return value;
    }
}
