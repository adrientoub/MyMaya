package script;

import script.ast.*;
import script.lexer.NewlineToken;
import script.lexer.StringToken;
import script.lexer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Parser {
    private int cursor;
    private List<Token> tokens;

    private StringToken getStringToken() {
        Token tok = tokens.get(cursor);
        if (tok instanceof StringToken) {
            cursor++;
            return (StringToken) tok;
        }
        System.err.println("Expected StringToken got " + tok);
        return null;
    }

    private List<Token> getArguments() {
        List<Token> arguments = new ArrayList<>();
        for (; cursor < tokens.size() && !(tokens.get(cursor) instanceof NewlineToken); cursor++) {
            arguments.add(tokens.get(cursor));
        }
        cursor++;
        return arguments;
    }

    private AstNode parseExp() {
        Token token = tokens.get(cursor);
        if (token instanceof StringToken) {
            StringToken stringToken = (StringToken) token;
            String realToken = stringToken.getString();
            if (realToken.equals("var")) {
                return new VarDef();
            } else if (realToken.equals("function")) {
                return parseFunctionDec();
            } else if (realToken.equals("if")) {
                return new IfExp();
            } else if (realToken.equals("while")) {
                return new WhileExp();
            } else {
                return parseCallExp();
            }
        } else {
            System.err.println("Parse error near token `" + token + "`");
            return null;
        }
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
        cursor++;
        StringToken functionNameToken = getStringToken();
        if (functionNameToken == null) {
            return null;
        }
        List<Token> arguments = getArguments();
        List<AstNode> exps = new ArrayList<>();

        while (!((tokens.get(cursor) instanceof StringToken) && ((StringToken)tokens.get(cursor)).getString().equals("end"))) {
            exps.add(parseExp());
        }

        if (((StringToken)tokens.get(cursor)).getString().equals("end")) {
            cursor += 2;
        }

        return new FunctionDef(functionNameToken.getString(), arguments, exps);
    }

    private AstNode parseCallExp() {
        StringToken functionNameToken = getStringToken();
        if (functionNameToken == null) {
            return null;
        }
        String functionName = functionNameToken.getString();
        List<Token> arguments = getArguments();

        return new CallExp(functionName, arguments);
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
        System.out.println(astNode);
        return astNode;
    }
}
