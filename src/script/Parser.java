package script;

import script.ast.*;
import script.lexer.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Parser {
    private int cursor;
    private List<Token> tokens;

    private enum Keyword {
        DO, THEN, TIMES;

        public static Keyword getKeyword(String str) {
            if (str.equals("do")) {
                return DO;
            } else if (str.equals("then")) {
                return THEN;
            } else if (str.equals("times")) {
                return TIMES;
            }
            return null;
        }
    }

    private Object parseError(Token tok) {
        System.err.println("Parse error near token " + tok);
        return null;
    }

    private Object parseError(String error) {
        System.err.println(error);
        return null;
    }

    private Object parseError(Token tok, String expectedType) {
        System.err.println("Expected " + expectedType + ", but got " + tok);
        return null;
    }

    private Object parseError(OperatorToken.Operator operator, OperatorToken.Operator expected) {
        System.err.println("Expected `" + expected + "', but got `" + operator + "'");
        return null;
    }


    private Token getToken() {
        Token tok = tokens.get(cursor);
        cursor++;
        return tok;
    }

    private OperatorToken getOperatorToken() {
        Token t = getToken();
        if (t instanceof OperatorToken) {
            return (OperatorToken) t;
        } else {
            return (OperatorToken) parseError(t, "OperatorToken");
        }
    }

    private OperatorToken getOperatorToken(OperatorToken.Operator op) {
        OperatorToken operatorToken = getOperatorToken();
        if (operatorToken == null) {
            return null;
        } else if (operatorToken.getOperator() != op) {
            return (OperatorToken) parseError(operatorToken.getOperator(), op);
        }
        return operatorToken;
    }

    private StringToken getKeywordToken(Keyword keyword) {
        StringToken stringToken = getStringToken();
        if (stringToken == null) {
            return null;
        } else if (Keyword.getKeyword(stringToken.getString()) != keyword) {
            return (StringToken) parseError(stringToken);
        }
        return stringToken;
    }

    private NewlineToken getNewlineToken() {
        Token tok = getToken();
        if (tok instanceof NewlineToken) {
            return (NewlineToken) tok;
        }

        return (NewlineToken) parseError(tok, "NewlineToken");
    }

    private StringToken getStringToken() {
        Token tok = getToken();
        if (tok instanceof StringToken) {
            return (StringToken) tok;
        }

        return (StringToken) parseError(tok, "StringToken");
    }

    private List<String> getArgumentList() {
        List<String> arguments = new ArrayList<>();
        Token t;
        for (; cursor < tokens.size() && !((t = getToken()) instanceof NewlineToken); ) {
            if (t instanceof StringToken) {
                arguments.add(((StringToken) t).getString());
            } else {
                System.err.println("Parse error in argument list, on token " + t);
                return null;
            }
        }
        return arguments;
    }

    private List<Token> getArguments() {
        List<Token> arguments = new ArrayList<>();
        Token t;
        for (; cursor < tokens.size() && !((t = getToken()) instanceof NewlineToken); ) {
            if (t instanceof OperatorToken) {
                System.err.println("Argument of function call cannot be an OperatorToken");
                return null;
            }
            arguments.add(t);
        }
        return arguments;
    }

    private AstNode parseExp() {
        Token token = getToken();
        return parseExp(token);
    }

    private AstNode parseExp(Token token) {
        if (token instanceof StringToken) {
            StringToken stringToken = (StringToken) token;
            String realToken = stringToken.getString();
            switch (realToken) {
                case "var":
                    return parseVarDef();
                case "function":
                    return parseFunctionDec();
                case "if":
                    return parseIfExp();
                case "loop":
                    return parseLoopExp();
                case "while":
                    return parseWhileExp();
                default:
                    return parseCallExp(realToken);
            }
        } else {
            return (AstNode) parseError(token);
        }
    }

    private LoopExp parseLoopExp() {
        NumericExp numericExp = parseNumericExp();
        if (numericExp == null) {
            return null;
        }
        Token t = getKeywordToken(Keyword.TIMES);
        if (t == null) {
            return null;
        }
        cursor++;

        SeqExp body = parseCommandList("end");
        if (body == null) {
            return null;
        }
        cursor += 2;

        return new LoopExp(numericExp, body);
    }

    private NumericExp parseNumericExp() {
        ArithmeticExp arithmeticExp = parseArithmeticExp();
        if (arithmeticExp instanceof NumericExp) {
            return (NumericExp) arithmeticExp;
        } else {
            System.err.println("Error: not a NumericExp");
            return null;
        }
    }

    private ArithmeticExp parseArithmeticExp() {
        List<String> numericExpTokens = new ArrayList<>();

        int i = cursor;
        for (; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t instanceof StringToken) {
                StringToken stringToken = (StringToken) t;
                if (stringToken.getString().equals("do") || stringToken.getString().equals("then")
                        || stringToken.getString().equals("times")) {
                    break;
                }
                numericExpTokens.add(stringToken.getString());
            } else if (t instanceof IntegerToken) {
                numericExpTokens.add(String.valueOf(((IntegerToken) t).getIntegerValue()));
            } else if (t instanceof DoubleToken) {
                numericExpTokens.add(String.valueOf(((DoubleToken) t).getValue()));
            } else {
                break;
            }
        }

        List<String> tokens = ToPolish.convert(numericExpTokens);
        if (tokens == null) {
            return null;
        }
        cursor = i;
        return ArithmeticParser.parse(tokens);
    }

    private WhileExp parseWhileExp() {
        BooleanExp booleanExp = parseBooleanExp();
        if (booleanExp == null) {
            return null;
        }
        Token t = getKeywordToken(Keyword.DO);
        if (t == null) {
            return null;
        }
        cursor++;

        SeqExp body = parseCommandList("end");
        if (body == null) {
            return null;
        }
        cursor += 2;

        return new WhileExp(booleanExp, body);
    }


    private IfExp parseIfExp() {
        BooleanExp booleanExp = parseBooleanExp();
        if (booleanExp == null) {
            return null;
        }
        Token t = getKeywordToken(Keyword.THEN);
        if (t == null) {
            return null;
        }
        cursor++;

        SeqExp ifClause = parseCommandList("else", "end");
        if (ifClause == null) {
            return null;
        }
        StringToken stringToken = getStringToken();
        if (stringToken == null) {
            return null;
        }
        cursor++;

        SeqExp elseClause = null;
        if (stringToken.getString().equals("else")) {
            elseClause = parseCommandList("end");
            if (elseClause == null) {
                return null;
            }
            cursor += 2;
        }

        return new IfExp(booleanExp, ifClause, elseClause);
    }

    private BooleanExp parseBooleanExp() {
        ArithmeticExp arithmeticExp = parseArithmeticExp();
        if (arithmeticExp instanceof BooleanExp) {
            return (BooleanExp) arithmeticExp;
        } else {
            System.err.println("Wrong expression type: expected Boolean, got Numeric");
            return null;
        }
    }

    private AstNode parseVarDef() {
        StringToken varNameToken = getStringToken();
        if (varNameToken == null) {
            return null;
        }
        Token t = getOperatorToken(OperatorToken.Operator.ASSIGN);
        if (t == null) {
            return null;
        }
        t = tokens.get(cursor);
        AstNode astNode;
        if (t instanceof QuotedStringToken) {
            cursor++;
            astNode = new StringExp(((QuotedStringToken) t).getString());
        } else {
            astNode = parseArithmeticExp();
        }

        if (astNode == null) {
            return null;
        }

        NewlineToken nl = getNewlineToken();
        if (nl == null) {
            return (AstNode) parseError("No end of line after vardec " + varNameToken);
        }

        return new VarDef(varNameToken.getString(), astNode);
    }

    private AstNode parseTokens() {
        if (tokens.size() == cursor) {
            return null;
        }
        if (tokens.get(cursor) instanceof NewlineToken) {
            cursor++;
            return parseTokens();
        }
        SeqExp seq = new SeqExp(new ArrayList<>());
        while (cursor < tokens.size()) {
            AstNode astNode = parseExp();
            if (astNode == null) {
                return null;
            }
            seq.add(astNode);
        }
        return seq;
    }

    private SeqExp parseCommandList(String... delimiters) {
        List<AstNode> exps = new ArrayList<>();

        while (cursor < tokens.size()) {
            Token tok = getToken();
            if (tok instanceof StringToken) {
                for (String delimiter: delimiters) {
                    if (((StringToken) tok).getString().equals(delimiter)) {
                        cursor--;
                        return new SeqExp(exps);
                    }
                }
            }
            exps.add(parseExp(tok));
        }

        return null;
    }

    private AstNode parseFunctionDec() {
        StringToken functionNameToken = getStringToken();
        if (functionNameToken == null) {
            return null;
        }
        List<String> arguments = getArgumentList();
        if (arguments == null) {
            return null;
        }

        SeqExp seqExp = parseCommandList("end");
        if (seqExp == null) {
            return null;
        } else {
            cursor += 2;
        }

        return new FunctionDef(functionNameToken.getString(), arguments, seqExp);
    }

    private AstNode parseCallExp(String functionName) {
        List<Token> arguments = getArguments();

        return new CallExp(functionName, arguments);
    }

    public AstNode parse(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        return parse(content);
    }

    public AstNode parse(String file) {
        cursor = 0;
        Lexer l = new Lexer(file);
        List<Token> tokens = l.getTokens();
        if (tokens.size() == 0) {
            return null;
        }
        this.tokens = tokens;
        AstNode astNode = parseTokens();
        // TODO: return other value if need to keep reading lines
        System.out.println(astNode);
        return astNode;
    }
}
