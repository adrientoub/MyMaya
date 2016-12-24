package script.lexer;

import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Token {
    protected int lineNumber;

    public Token(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public abstract Type getType();
}
