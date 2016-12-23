package script.function;

import model.SceneModel;
import script.ast.ArgumentListDef;
import script.lexer.DoubleToken;
import script.lexer.NumberToken;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.DoubleType;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 23/12/2016.
 */
public class TranslateFunction extends Function {
    public TranslateFunction() {
        super(new ArgumentListDef("name", "x", "y", "z"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
        getArguments().getArgumentListType().getTypes().set(1, DoubleType.getInstance());
        getArguments().getArgumentListType().getTypes().set(2, DoubleType.getInstance());
        getArguments().getArgumentListType().getTypes().set(3, DoubleType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken name = (QuotedStringToken) arguments.get(0);
        NumberToken x = (NumberToken) arguments.get(1);
        NumberToken y = (NumberToken) arguments.get(2);
        NumberToken z = (NumberToken) arguments.get(3);
        System.out.println("Translating " + name + " to (" + x + ", " + y + ", " + z + ")");
        SceneModel.moveBy(name.getString(), x.getValue(), y.getValue(), z.getValue());
    }
}
