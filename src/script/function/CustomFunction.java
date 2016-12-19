package script.function;

import script.ast.SeqExp;
import script.lexer.Token;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class CustomFunction extends Function {
    private SeqExp exps;

    public CustomFunction(int arity, SeqExp exps) {
        super(arity);
        this.exps = exps;
    }

    @Override
    public void apply(List<Token> arguments) {
        System.err.println("TODO");
        System.exit(1);
    }
}
