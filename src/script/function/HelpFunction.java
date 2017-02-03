package script.function;

import model.HistoryModel;
import script.Execution;
import script.ast.ArgumentListDef;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.StringType;
import script.types.Type;
import view.AttributesView;

import java.util.List;

/**
 * Created by Adrien on 03/02/2017.
 */
public class HelpFunction extends Function {
    public HelpFunction() {
        super(new ArgumentListDef("name"));
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken t = (QuotedStringToken) arguments.get(0);
        Function fun = Execution.getFunctionMap().get(t.getString());
        if (fun == null) {
            AttributesView.getInstance().addLine("No function named '" + t.getString() + "'");
            return;
        }
        List<Type> types = fun.getArguments().getArgumentListType().getTypes();
        List<String> variableNames = fun.getArguments().getVariableNames();
        String line = t.getString();

        for (int i = 0; i < variableNames.size(); i++) {
            line += " " + variableNames.get(i) + ": " + types.get(i);
        }
        AttributesView.getInstance().addLine(line);
    }
}
