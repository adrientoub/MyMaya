package script.function;

import model.SceneModel;
import script.ast.ArgumentListDef;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class RemoveFunction extends Function {
    public RemoveFunction() {
        super(new ArgumentListDef("name"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        Token t = arguments.get(0);
        System.out.println("Removing " + t);
        QuotedStringToken quotedStringToken = (QuotedStringToken) t;
        SceneModel.remove(quotedStringToken.getString());
    }
}
