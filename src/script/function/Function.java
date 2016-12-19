package script.function;

import script.lexer.Token;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Function {
    // TODO: Store argument types instead of arity

    private int arity;

    public Function(int arity) {
        this.arity = arity;
    }

    public int getArity() {
        return arity;
    }

    public abstract void apply(List<Token> arguments);
}
