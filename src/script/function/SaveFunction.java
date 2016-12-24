package script.function;

import model.ExportSceneModel;
import script.ast.ArgumentListDef;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 24/12/2016.
 */
public class SaveFunction extends Function {
    public SaveFunction() {
        super(new ArgumentListDef("path"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken t = (QuotedStringToken) arguments.get(0);
        System.out.println("Saving to " + t);
        ExportSceneModel.exportScene(t.getString());
    }
}
