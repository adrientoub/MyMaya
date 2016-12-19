package script;

import model.Object3D;
import script.ast.CallExp;
import script.ast.FunctionDef;
import script.ast.SeqExp;
import script.function.CustomFunction;
import script.function.Function;
import script.function.RemoveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Execution extends Visitor {
    private static Map<String, Function> functionMap;
    private static Map<String, Object> variablesMap;

    public static Map<String, Function> getFunctionMap() {
        if (functionMap == null) {
            functionMap = new HashMap<>();
            functionMap.put("remove", new RemoveFunction());
        }
        return functionMap;
    }

    @Override
    public void visit(CallExp callExp) {
        Function function = getFunctionMap().get(callExp.getFunctionName());
        if (function == null) {
            System.err.println("No function named " + callExp.getFunctionName() + " found");
        } else if (function.getArity() != callExp.getArguments().size()) {
            System.err.println("Wrong parameter count, expecting " + function.getArity() + " got " + callExp.getArguments().size());
        } else {
            function.apply(callExp.getArguments());
        }
    }

    @Override
    public void visit(FunctionDef functionDef) {
        // TODO: save the variables name so that when the function is called the right variables are set
        getFunctionMap().put(functionDef.getFunctionName(),
                new CustomFunction(functionDef.getArguments().size(), new SeqExp(functionDef.getExps())));
    }
}
