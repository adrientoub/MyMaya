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
public class SaveNextFunction extends Function {
    @Override
    public void apply(List<Token> arguments) {
        System.out.println("Saving to next path");
        ExportSceneModel.exportScene(null);
    }
}
