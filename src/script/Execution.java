package script;

import script.ast.*;
import script.function.*;
import script.lexer.StringToken;
import script.lexer.Token;

import java.util.*;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Execution extends Visitor {
    private static Deque<Map<String, Function>> functionScopes = new ArrayDeque<>();
    private static Deque<Map<String, Token>> variableScopes = new ArrayDeque<>();
    private static Execution instance = new Execution();

    public static Execution getInstance() {
        return instance;
    }

    private Execution() {
    }

    public static Map<String, Function> getFunctionMap() {
        if (functionScopes.isEmpty()) {
            Map<String, Function> functions = new HashMap<>();
            functions.put("remove", new RemoveFunction());
            functions.put("rename", new RenameFunction());
            functions.put("translate", new TranslateFunction());
            functions.put("scale", new ScaleFunction());
            functionScopes.addLast(functions);
        }
        return functionScopes.getLast();
    }

    public static Map<String, Token> getVariablesMap() {
        if (variableScopes.isEmpty()) {
            variableScopes.addLast(new HashMap<>());
        }

        return variableScopes.getLast();
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

    @Override
    public void visit(IfExp ifExp) {
        ifExp.getBooleanExp().accept(this);
        if (ifExp.getBooleanExp().getValue()) {
            ifExp.getIfClause().accept(this);
        } else {
            ifExp.getElseClause().accept(this);
        }
    }

    @Override
    public void visit(BooleanExp booleanExp) {
        // TODO: do something when booleanExp is composed and store result in value
    }

    private Token getVariable(StringToken token) {
        Token t = getVariablesMap().get(token.getString());
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
        getVariablesMap().put(varDef.getName(), value);
    }

    @Override
    public void visit(FunctionDef functionDef) {
        getFunctionMap().put(functionDef.getFunctionName(),
                new CustomFunction(functionDef.getArguments(), functionDef.getExps()));
    }

    public static void newScope() {
        Map<String, Function> functions = new HashMap<>(getFunctionMap());
        Map<String, Token> variables = new HashMap<>(getVariablesMap());

        functionScopes.addLast(functions);
        variableScopes.addLast(variables);
    }


    public static void endScope() {
        functionScopes.removeLast();
        variableScopes.removeLast();
    }
}
