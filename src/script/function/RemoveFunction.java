package script.function;

import script.lexer.Token;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class RemoveFunction extends Function {
    public RemoveFunction() {
        super(1);
    }

    @Override
    public void apply(List<Token> arguments) {
        Token t = arguments.get(0);
        System.out.println("Removing " + t);
    }
}
