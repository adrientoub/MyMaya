package script;

import model.Object3D;
import script.ast.CallExp;
import script.ast.FunctionDef;
import script.ast.SeqExp;
import script.function.CustomFunction;
import script.function.Function;
import script.function.RemoveFunction;
import script.lexer.Token;

import java.util.HashMap;
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
        if (function == null) {
            System.err.println("No function named " + callExp.getFunctionName() + " found");
        } else if (!function.getArguments().getType().compatibleWith(callExp.getArgumentType())) {
            System.err.println("Wrong parameter type, expecting " + function.getArguments().getArgumentListType() + " got " + callExp.getArgumentType());
        } else {
            function.apply(callExp.getArguments());
        }
    }

    @Override
    public void visit(FunctionDef functionDef) {
        getFunctionMap().put(functionDef.getFunctionName(),
                new CustomFunction(functionDef.getArguments(), functionDef.getExps()));
    }
}
