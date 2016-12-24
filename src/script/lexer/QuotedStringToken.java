package script.lexer;

import script.types.StringType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class QuotedStringToken extends Token {
    private String string;

    public String getString() {
        return string;
    }

    public QuotedStringToken(String quotedString, int lineNumber) {
        super(lineNumber);
        this.string = quotedString.substring(1, quotedString.length() - 1);
    }

    @Override
    public Type getType() {
        return StringType.getInstance();
    }

    @Override
    public String toString() {
        return "QuotedStringToken{line=" + lineNumber + " " + string + '}';
    }
}
