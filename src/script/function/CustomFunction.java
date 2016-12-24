package script.function;

import script.Execution;
import script.ast.ArgumentListDef;
import script.ast.SeqExp;
import script.lexer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adrien on 19/12/2016.
 */
public class CustomFunction extends Function {
    private SeqExp exps;

    public CustomFunction(ArgumentListDef arguments, SeqExp exps) {
        super(arguments);
        this.exps = exps;
    }

    @Override
    public void apply(List<Token> arguments) {
        List<String> variables = getArguments().getVariableNames();
        Execution.newScope();

        for (int i = 0; i < variables.size(); i++) {
            Execution.getVariablesMap().put(variables.get(i), arguments.get(i));
        }
        exps.accept(Execution.getInstance());

        Execution.endScope();
    }
}
