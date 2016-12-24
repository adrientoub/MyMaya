package script.lexer;

/**
 * Created by Adrien on 23/12/2016.
 */
public abstract class NumberToken extends Token {
    public NumberToken(int lineNumber) {
        super(lineNumber);
    }

    abstract public double getValue();
}
