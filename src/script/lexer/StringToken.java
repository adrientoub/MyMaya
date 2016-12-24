package script.lexer;

import script.Execution;
import script.types.BooleanType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class StringToken extends Token {
    private String string;

    public StringToken(String str, int lineNumber) {
        super(lineNumber);
        string = str;
    }

    public String getString() {
        return string;
    }

    public Type getType() {
        if (string.equals("true") || string.equals("false")) {
            return BooleanType.getInstance();
        }
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
