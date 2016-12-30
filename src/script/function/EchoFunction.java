package script.function;

import script.ast.ArgumentListDef;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 30/12/2016.
 */
public class EchoFunction extends Function {
    public EchoFunction() {
        super(new ArgumentListDef("text"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken t = (QuotedStringToken) arguments.get(0);
        System.out.println(t.getString());
    }
}
