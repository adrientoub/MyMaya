package script;

import script.ast.*;
import script.function.*;
import script.lexer.BooleanToken;
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
            functions.put("exec", new LaunchScriptFunction());
            functions.put("save", new SaveFunction());
            functions.put("save_next", new SaveNextFunction());
            functions.put("sphere", new ObjectFunction(ObjectFunction.ObjectType.SPHERE));
            functions.put("point_light", new ObjectFunction(ObjectFunction.ObjectType.POINT_LIGHT));
            functions.put("directional_light", new ObjectFunction(ObjectFunction.ObjectType.DIRECTIONAL_LIGHT));
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
    public void visit(LoopExp loopExp) {
        loopExp.getNumericExp().accept(this);
        int times = (int) loopExp.getNumericExp().getValue();
        for (int i = 0; i < times; i++) {
            loopExp.getBody().accept(this);
        }
    }

    @Override
    public void visit(IfExp ifExp) {
        ifExp.getBooleanExp().accept(this);
        if (ifExp.getBooleanExp().getValue()) {
            ifExp.getIfClause().accept(this);
        } else {
            if (ifExp.getElseClause() != null) {
                ifExp.getElseClause().accept(this);
            }
        }
    }

    public void visit(WhileExp whileExp) {
        while (true) {
            whileExp.getBooleanExp().accept(this);
            if (!whileExp.getBooleanExp().getValue()) {
                break;
            }

            whileExp.getBody().accept(this);
        }
    }

    @Override
    public void visit(NumericOpExp numericOpExp) {
        numericOpExp.getLhs().accept(this);
        numericOpExp.getRhs().accept(this);
        switch (numericOpExp.getOp()) {
            case PLUS:
                numericOpExp.setValue(numericOpExp.getLhs().getValue() + numericOpExp.getRhs().getValue());
                break;
            case MINUS:
                numericOpExp.setValue(numericOpExp.getLhs().getValue() - numericOpExp.getRhs().getValue());
                break;
            case TIMES:
                numericOpExp.setValue(numericOpExp.getLhs().getValue() * numericOpExp.getRhs().getValue());
                break;
            case DIVIDE:
                numericOpExp.setValue(numericOpExp.getLhs().getValue() / numericOpExp.getRhs().getValue());
                break;
            case MODULUS:
                numericOpExp.setValue(numericOpExp.getLhs().getValue() % numericOpExp.getRhs().getValue());
                break;
        }
    }

    @Override
    public void visit(BooleanExp booleanExp) {
    }

    @Override
    public void visit(BooleanNotExp booleanNotExp) {
        booleanNotExp.getBooleanExp().accept(this);
        booleanNotExp.setValue(!booleanNotExp.getBooleanExp().getValue());
    }

    @Override
    public void visit(BooleanOpExp booleanOpExp) {
        booleanOpExp.getLhs().accept(this);
        booleanOpExp.getRhs().accept(this);
        switch (booleanOpExp.getOp()) {
            case OR:
                booleanOpExp.setValue(booleanOpExp.getLhs().getValue() || booleanOpExp.getRhs().getValue());
                break;
            case AND:
                booleanOpExp.setValue(booleanOpExp.getLhs().getValue() && booleanOpExp.getRhs().getValue());
                break;
            case EQUAL:
                booleanOpExp.setValue(booleanOpExp.getLhs().getValue() == booleanOpExp.getRhs().getValue());
                break;
            case DIFFERENT:
                booleanOpExp.setValue(booleanOpExp.getLhs().getValue() != booleanOpExp.getRhs().getValue());
                break;
        }
    }

    @Override
    public void visit(BooleanOpNumericExp booleanOpNumericExp) {
        booleanOpNumericExp.getLhs().accept(this);
        booleanOpNumericExp.getRhs().accept(this);
        switch (booleanOpNumericExp.getOp()) {
            case EQUAL:
                booleanOpNumericExp.setValue(booleanOpNumericExp.getLhs().getValue() == booleanOpNumericExp.getRhs().getValue());
                break;
            case DIFFERENT:
                booleanOpNumericExp.setValue(booleanOpNumericExp.getLhs().getValue() != booleanOpNumericExp.getRhs().getValue());
                break;
        }
    }

    @Override
    public void visit(BooleanNameExp booleanNameExp) {
         Token tok = getVariablesMap().get(booleanNameExp.getName());
         if (tok == null) {
             System.err.println("No variable named " + booleanNameExp.getName());
         } else if (tok instanceof BooleanToken) {
             booleanNameExp.setValue(((BooleanToken) tok).isValue());
         } else {
             System.err.println("The variable named " + booleanNameExp.getName() + " is not a boolean.");
         }
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
