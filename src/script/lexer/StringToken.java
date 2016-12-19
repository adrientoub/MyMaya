package script.lexer;

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

    @Override
    public String toString() {
        return "StringToken{" +
                "string='" + string + '\'' +
                '}';
    }
}
