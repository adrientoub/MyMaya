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
public class ScaleFunction extends Function {
    public ScaleFunction() {
        super(new ArgumentListDef("name", "scale"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
        getArguments().getArgumentListType().getTypes().set(1, DoubleType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken name = (QuotedStringToken) arguments.get(0);
        NumberToken scale = (NumberToken) arguments.get(1);
        System.out.println("Scaling " + name + " by " + scale);
        SceneModel.scaleBy(name.getString(), scale.getValue());
    }
}
