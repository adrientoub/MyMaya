package script;

import script.ast.*;
import script.lexer.NewlineToken;
import script.lexer.OperatorToken;
import script.lexer.StringToken;
import script.lexer.Token;

import java.io.*;
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
            if (realToken.equals("var")) {
                return parseVarDef();
            } else if (realToken.equals("function")) {
                return parseFunctionDec();
            } else if (realToken.equals("if")) {
                return new IfExp();
            } else if (realToken.equals("while")) {
                return new WhileExp();
            } else {
                return parseCallExp(realToken);
            }
        } else {
            System.err.println("Parse error near token `" + token + "`");
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
        t = getToken();
        NewlineToken nl = getNewlineToken();
        if (nl == null) {
            return (AstNode) parseError("No end of line after vardec " + varNameToken);
        }

        return new VarDef(varNameToken.getString(), t);
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

    private AstNode parseFunctionDec() {
        StringToken functionNameToken = getStringToken();
        if (functionNameToken == null) {
            return null;
        }
        List<String> arguments = getArgumentList();
        if (arguments == null) {
            return null;
        }
        List<AstNode> exps = new ArrayList<>();

        Token tok = null;

        while (cursor < tokens.size()) {
            tok = getToken();
            if (tok instanceof StringToken && ((StringToken) tok).getString().equals("end"))
                break;

            exps.add(parseExp(tok));
        }

        if (cursor >= tokens.size()) {
            return null;
        }

        if (((StringToken) tok).getString().equals("end")) {
            cursor++;
        }

        return new FunctionDef(functionNameToken.getString(), arguments, exps);
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
