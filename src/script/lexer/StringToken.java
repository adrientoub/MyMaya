package script.lexer;

import script.Execution;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class StringToken extends Token {
    private String string;

    public StringToken(String str) {
        string = str;
    }

    public String getString() {
        return string;
    }

    public Type getType() {
        Token token = Execution.getVariablesMap().get(string);
        if (token == null) {
            return null;
        }
        return token.getType();
    }

    @Override
    public String toString() {
        return "StringToken{" + string + '}';
    }
}
