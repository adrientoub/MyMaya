package script.lexer;

/**
 * Created by Adrien on 19/12/2016.
 */
public class QuotedStringToken extends Token {
    private String object;

    public QuotedStringToken(String quotedString) {
        this.object = quotedString;
    }
}
