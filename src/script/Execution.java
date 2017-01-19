package script;

import script.ast.*;
import script.function.*;
import script.lexer.*;
import script.types.BooleanType;
import script.types.DoubleType;
import script.types.IntegerType;
import script.types.NumericType;

import java.util.*;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Execution extends Visitor {
    private static Deque<Map<String, Function>> functionScopes = new ArrayDeque<>();
    private static Deque<Map<String, Object>> variableScopes = new ArrayDeque<>();
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
            functions.put("box", new ObjectFunction(ObjectFunction.ObjectType.BOX));
            functions.put("point_light", new ObjectFunction(ObjectFunction.ObjectType.POINT_LIGHT));
            functions.put("directional_light", new ObjectFunction(ObjectFunction.ObjectType.DIRECTIONAL_LIGHT));
            functions.put("mesh", new ObjectFunction(ObjectFunction.ObjectType.MESH));
            functions.put("echo", new EchoFunction());
            functionScopes.addLast(functions);
        }
        return functionScopes.getLast();
    }

    public static void setVariable(String name, Object value) {
        getVariablesMap().put(name, value);
    }

    private static Map<String, Object> getVariablesMap() {
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
                Token value = getVariableToken((StringToken) arguments.get(i));
                if (value == null) {
                    return;
                }
                arguments.set(i, value);
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
        if (numericOpExp.getLhs().getType() instanceof NumericType && numericOpExp.getRhs().getType() instanceof NumericType) {
            if (numericOpExp.getLhs().getType() instanceof IntegerType && numericOpExp.getLhs().getType() instanceof IntegerType) {
                visitIntegerOpExp(numericOpExp);
            } else /* If the expressions are different types or double type */ {
                visitDoubleOpExp(numericOpExp);
            }
        } else {
            System.err.println("Error: Trying to use operator " + numericOpExp.getOp() + " on expressions not numeric");
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

    private int getIntegerValue(ArithmeticExp arithmeticExp) {
        if (arithmeticExp instanceof NameExp) {
            return (int) getVariable(((NameExp) arithmeticExp).getName());
        } else {
            return (int) ((NumericExp) arithmeticExp).getValue();
        }
    }

    private boolean getBooleanValue(ArithmeticExp arithmeticExp) {
        if (arithmeticExp instanceof NameExp) {
            return (boolean) getVariable(((NameExp) arithmeticExp).getName());
        } else {
            return ((BooleanExp) arithmeticExp).getValue();
        }
    }

    private double getDoubleValue(ArithmeticExp arithmeticExp) {
        if (arithmeticExp instanceof NameExp) {
            return (double) getVariable(((NameExp) arithmeticExp).getName());
        } else {
            return ((NumericExp) arithmeticExp).getValue();
        }
    }

    private void visitDoubleOpExp(NumericOpExp numericOpExp) {
        double lhsDoubleValue = getDoubleValue(numericOpExp.getLhs());
        double rhsDoubleValue = getDoubleValue(numericOpExp.getRhs());

        switch (numericOpExp.getOp()) {
            case PLUS:
                numericOpExp.setValue(lhsDoubleValue + rhsDoubleValue);
                break;
            case MINUS:
                numericOpExp.setValue(lhsDoubleValue - rhsDoubleValue);
                break;
            case TIMES:
                numericOpExp.setValue(lhsDoubleValue * rhsDoubleValue);
                break;
            case DIVIDE:
                numericOpExp.setValue(lhsDoubleValue / rhsDoubleValue);
                break;
            case MODULUS:
                numericOpExp.setValue(lhsDoubleValue % rhsDoubleValue);
                break;
        }
    }

    private void visitIntegerOpExp(NumericOpExp numericOpExp) {
        int lhsIntegerValue = getIntegerValue(numericOpExp.getLhs());
        int rhsIntegerValue = getIntegerValue(numericOpExp.getRhs());

        switch (numericOpExp.getOp()) {
            case PLUS:
                numericOpExp.setValue(lhsIntegerValue + rhsIntegerValue);
                break;
            case MINUS:
                numericOpExp.setValue(lhsIntegerValue - rhsIntegerValue);
                break;
            case TIMES:
                numericOpExp.setValue(lhsIntegerValue * rhsIntegerValue);
                break;
            case DIVIDE:
                numericOpExp.setValue(lhsIntegerValue / rhsIntegerValue);
                break;
            case MODULUS:
                numericOpExp.setValue(lhsIntegerValue % rhsIntegerValue);
                break;
        }
    }

    private void visitIntegerBooleanOpExp(BooleanOpExp booleanOpExp) {
        int lhsIntegerValue = getIntegerValue(booleanOpExp.getLhs());
        int rhsIntegerValue = getIntegerValue(booleanOpExp.getRhs());

        switch (booleanOpExp.getOp()) {
            case EQUAL:
                booleanOpExp.setValue(lhsIntegerValue == rhsIntegerValue);
                break;
            case DIFFERENT:
                booleanOpExp.setValue(lhsIntegerValue != rhsIntegerValue);
                break;
            case LTHAN:
                booleanOpExp.setValue(lhsIntegerValue < rhsIntegerValue);
                break;
            case GTHAN:
                booleanOpExp.setValue(lhsIntegerValue > rhsIntegerValue);
                break;
            case LEQ:
                booleanOpExp.setValue(lhsIntegerValue <= rhsIntegerValue);
                break;
            case GEQ:
                booleanOpExp.setValue(lhsIntegerValue >= rhsIntegerValue);
                break;
            default:
                System.err.println("Error: trying to use operator " + booleanOpExp.getOp() + " on integers");
        }
    }

    private void visitDoubleBooleanOpExp(BooleanOpExp booleanOpExp) {
        double lhsDoubleValue = getDoubleValue(booleanOpExp.getLhs());
        double rhsDoubleValue = getDoubleValue(booleanOpExp.getRhs());

        switch (booleanOpExp.getOp()) {
            case EQUAL:
                booleanOpExp.setValue(lhsDoubleValue == rhsDoubleValue);
                break;
            case DIFFERENT:
                booleanOpExp.setValue(lhsDoubleValue != rhsDoubleValue);
                break;
            case LTHAN:
                booleanOpExp.setValue(lhsDoubleValue < rhsDoubleValue);
                break;
            case GTHAN:
                booleanOpExp.setValue(lhsDoubleValue > rhsDoubleValue);
                break;
            case LEQ:
                booleanOpExp.setValue(lhsDoubleValue <= rhsDoubleValue);
                break;
            case GEQ:
                booleanOpExp.setValue(lhsDoubleValue >= rhsDoubleValue);
                break;
            default:
                System.err.println("Error: trying to use operator " + booleanOpExp.getOp() + " on doubles");
        }
    }

    public void visitBooleanBooleanOpExp(BooleanOpExp booleanOpExp) {
        boolean lhsBooleanValue = getBooleanValue(booleanOpExp.getLhs());
        boolean rhsBooleanValue = getBooleanValue(booleanOpExp.getRhs());
        switch (booleanOpExp.getOp()) {
            case EQUAL:
                booleanOpExp.setValue(lhsBooleanValue == rhsBooleanValue);
                break;
            case DIFFERENT:
                booleanOpExp.setValue(lhsBooleanValue != rhsBooleanValue);
                break;
            case OR:
                booleanOpExp.setValue(lhsBooleanValue || rhsBooleanValue);
                break;
            case AND:
                booleanOpExp.setValue(lhsBooleanValue && rhsBooleanValue);
                break;
            default:
                System.err.println("Error: trying to use operator " + booleanOpExp.getOp() + " on booleans");
        }
    }

    @Override
    public void visit(BooleanOpExp booleanOpExp) {
        ArithmeticExp rhs = booleanOpExp.getRhs();
        ArithmeticExp lhs = booleanOpExp.getLhs();
        lhs.accept(this);
        rhs.accept(this);
        if (rhs.getType() instanceof NumericType && lhs.getType() instanceof NumericType) {
            if (lhs.getType() instanceof IntegerType && rhs.getType() instanceof IntegerType) {
                visitIntegerBooleanOpExp(booleanOpExp);
            } else /* If the expressions are different types or double type */ {
                visitDoubleBooleanOpExp(booleanOpExp);
            }
        } else if (rhs.getType() instanceof BooleanType && lhs.getType() instanceof BooleanType) {
            visitBooleanBooleanOpExp(booleanOpExp);
        } else {
            if (booleanOpExp.getOp() == BooleanOpExp.Operator.EQUAL) {
                booleanOpExp.setValue(false);
            } else if (booleanOpExp.getOp() == BooleanOpExp.Operator.DIFFERENT) {
                booleanOpExp.setValue(true);
            } else {
                System.err.println("Error: cannot do operator " + booleanOpExp.getOp() + " between two expressions of different types (" + lhs + " and " + rhs + ")");
            }
        }
    }

    @Override
    public void visit(NameExp nameExp) {
         Object value = getVariablesMap().get(nameExp.getName());
         if (value == null) {
             System.err.println("No variable named " + nameExp.getName());
         }
    }

    public Token getVariableToken(StringToken token) {
        return getVariableToken(token.getString());
    }

    public Object getVariable(StringToken token) {
        return getVariable(token.getString());
    }

    public Token getVariableToken(String token) {
        Object obj = getVariable(token);
        if (obj instanceof String) {
            return new QuotedStringToken((String) obj, 0);
        } else if (obj instanceof Double) {
            return new DoubleToken((Double) obj, 0);
        } else if (obj instanceof Integer) {
            return new IntegerToken((Integer) obj, 0);
        } else if (obj instanceof Boolean) {
            return new BooleanToken((Boolean) obj, 0);
        } else {
            return null;
        }
    }

    public Object getVariable(String token) {
        Object value = getVariablesMap().get(token);
        if (value == null) {
            System.err.println("No variable named " + token);
        }
        return value;
    }

    @Override
    public void visit(VarDef varDef) {
        AstNode varDefValue = varDef.getValue();
        Object value;

        varDefValue.accept(this);
        if (varDefValue instanceof StringExp) {
            value = ((StringExp) varDefValue).getValue();
        } else if (varDefValue instanceof ArithmeticExp) {
            ArithmeticExp arithmeticExp = (ArithmeticExp) varDefValue;
            if (arithmeticExp.getType() instanceof IntegerType) {
                value = getIntegerValue(arithmeticExp);
            } else if (arithmeticExp.getType() instanceof DoubleType) {
                value = getDoubleValue(arithmeticExp);
            } else if (arithmeticExp.getType() instanceof BooleanType) {
                value = getBooleanValue(arithmeticExp);
            } else {
                System.err.println("Error: not handled arithmetic expression type.");
                return;
            }
        } else {
            System.err.println("Wrong variable value");
            return;
        }
        setVariable(varDef.getName(), value);

    }

    @Override
    public void visit(FunctionDef functionDef) {
        getFunctionMap().put(functionDef.getFunctionName(),
                new CustomFunction(functionDef.getArguments(), functionDef.getExps()));
    }

    public static void newScope() {
        Map<String, Function> functions = new HashMap<>(getFunctionMap());
        Map<String, Object> variables = new HashMap<>(getVariablesMap());

        functionScopes.addLast(functions);
        variableScopes.addLast(variables);
    }


    public static void endScope() {
        functionScopes.removeLast();
        variableScopes.removeLast();
    }
}
