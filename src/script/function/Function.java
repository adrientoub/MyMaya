package script.function;

import script.ast.ArgumentListDef;
import script.lexer.Token;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Function {
    private ArgumentListDef arguments;

    public Function(ArgumentListDef arguments) {
        this.arguments = arguments;
    }

    public ArgumentListDef getArguments() {
        return arguments;
    }

    public abstract void apply(List<Token> arguments);
}
