package script.function;

import model.SceneModel;
import script.ast.ArgumentListDef;
import script.lexer.NumberToken;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.DoubleType;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 23/12/2016.
 */
public class RotateFunction extends Function {
    public RotateFunction() {
        super(new ArgumentListDef("name", "x", "y"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
        getArguments().getArgumentListType().getTypes().set(1, DoubleType.getInstance());
        getArguments().getArgumentListType().getTypes().set(2, DoubleType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken name = (QuotedStringToken) arguments.get(0);
        NumberToken x = (NumberToken) arguments.get(1);
        NumberToken y = (NumberToken) arguments.get(2);
        System.out.println("Rotating " + name + " by (" + x + ", " + y + ")");
        SceneModel.rotate(name.getString(), x.getValue(), y.getValue());
    }
}
