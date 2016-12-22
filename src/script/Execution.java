package script;

import script.ast.CallExp;
import script.ast.FunctionDef;
import script.ast.VarDef;
import script.function.CustomFunction;
import script.function.Function;
import script.function.RemoveFunction;
import script.lexer.StringToken;
import script.lexer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Execution extends Visitor {
    private static Map<String, Function> functionMap;
    private static Map<String, Token> variablesMap = new HashMap<>();
    private static Execution instance = new Execution();

    public static Execution getInstance() {
        return instance;
    }

    private Execution() {
    }

    public static Map<String, Function> getFunctionMap() {
        if (functionMap == null) {
            functionMap = new HashMap<>();
            functionMap.put("remove", new RemoveFunction());
        }
        return functionMap;
    }

    public static Map<String, Token> getVariablesMap() {
        return variablesMap;
    }

    public static void setVariablesMap(Map<String, Token> variablesMap) {
        Execution.variablesMap = variablesMap;
    }

    @Override
    public void visit(CallExp callExp) {
        Function function = getFunctionMap().get(callExp.getFunctionName());
        List<Token> arguments = callExp.getArguments();
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i) instanceof StringToken) {
                Token tok = getVariable((StringToken) arguments.get(i));
                if (tok == null) {
                    return;
                }
                arguments.set(i, tok);
            }
        }
        if (function == null) {
            System.err.println("No function named " + callExp.getFunctionName() + " found");
        } else if (!function.getArguments().getType().compatibleWith(callExp.getArgumentType())) {
            System.err.println("Wrong parameter type, expecting " + function.getArguments().getArgumentListType() + " got " + callExp.getArgumentType());
        } else {
            function.apply(arguments);
        }
    }

    private Token getVariable(StringToken token) {
        Token t = variablesMap.get(token.getString());
        if (t == null) {
            System.err.println("No variable named " + token.getString());
        }
        return t;
    }

    @Override
    public void visit(VarDef varDef) {
        Token value = varDef.getValue();
        if (value instanceof StringToken) {
            value = getVariable(((StringToken) value));
            if (value == null) {
                return;
            }
        }
        variablesMap.put(varDef.getName(), value);
    }

    @Override
    public void visit(FunctionDef functionDef) {
        getFunctionMap().put(functionDef.getFunctionName(),
                new CustomFunction(functionDef.getArguments(), functionDef.getExps()));
    }
}
