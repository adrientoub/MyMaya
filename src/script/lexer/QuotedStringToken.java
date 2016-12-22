package script.lexer;

import script.types.StringType;
import script.types.Type;

/**
 * Created by Adrien on 19/12/2016.
 */
public class QuotedStringToken extends Token {
    private String string;

    public QuotedStringToken(String quotedString) {
        this.string = quotedString;
    }

    @Override
    public Type getType() {
        return StringType.getInstance();
    }

    @Override
    public String toString() {
        return "QuotedStringToken{" + string + '}';
    }
}
