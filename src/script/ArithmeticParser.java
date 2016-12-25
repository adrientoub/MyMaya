package script;

import script.ast.*;
import script.lexer.StringToken;

import java.util.List;

/**
 * Created by Adrien on 25/12/2016.
 */
public class ArithmeticParser {
    private List<String> tokens;
    private int position;
    private boolean fail = false;

    public ArithmeticParser(List<String> tokens) {
        this.tokens = tokens;
        this.position = tokens.size() - 1;
    }

    private BooleanExp parse() {
        if (position < 0) {
            fail = true;
            return null;
        }
        String token = tokens.get(position--);
        switch (token) {
            case "==":
                return new BooleanOpExp(parse(), parse(), BooleanOpExp.Operator.EQUAL);
            case "!=":
                return new BooleanOpExp(parse(), parse(), BooleanOpExp.Operator.DIFFERENT);
            case "&&":
                return new BooleanOpExp(parse(), parse(), BooleanOpExp.Operator.AND);
            case "||":
                return new BooleanOpExp(parse(), parse(), BooleanOpExp.Operator.OR);
            case "!":
                return new BooleanNotExp(parse());
            case "true":
            case "false":
                return new BooleanExp(token.equals("true"));
            default:
                return new BooleanNameExp(token);
        }
    }

    public static BooleanExp parse(List<String> tokens) {
        ArithmeticParser parser = new ArithmeticParser(tokens);
        BooleanExp arithmetic = parser.parse();

        return parser.fail ? null: arithmetic;
    }
}
