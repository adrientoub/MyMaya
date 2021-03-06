package script.function;

import script.Execution;
import script.Parser;
import script.ast.ArgumentListDef;
import script.ast.AstNode;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Created by Adrien on 24/12/2016.
 */
public class LaunchScriptFunction extends Function {
    public LaunchScriptFunction() {
        super(new ArgumentListDef("path"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken t = (QuotedStringToken) arguments.get(0);
        System.out.println("Launching " + t);
        Parser parser = new Parser();
        try {
            AstNode ast = parser.parse(new File(t.getString()));
            if (ast != null) {
                ast.accept(Execution.getInstance());
            }
        } catch (NoSuchFileException e) {
            System.err.println(t.getString() + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
