package script.function;

import model.SceneModel;
import script.ast.ArgumentListDef;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 23/12/2016.
 */
public class RenameFunction extends Function {
    public RenameFunction() {
        super(new ArgumentListDef("name1", "name2"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
        getArguments().getArgumentListType().getTypes().set(1, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        Token from = arguments.get(0);
        Token to = arguments.get(1);
        System.out.println("Renaming " + from + " to " + to);
        QuotedStringToken fromToken = (QuotedStringToken) from;
        QuotedStringToken toToken = (QuotedStringToken) to;
        SceneModel.rename(fromToken.getString(), toToken.getString());
    }
}
