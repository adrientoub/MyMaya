package script.lexer;

import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class NewlineToken extends Token {
    public NewlineToken(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String toString() {
        return "NewlineToken{line=" + lineNumber + "}";
    }
}
