package script;

import script.lexer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Adrien on 19/12/2016.
 */
public class Lexer {
    private String str;

    public Lexer(String str) {
        this.str = str;
    }

    public List<Token> getTokens() {
        List<Token> tokens = new ArrayList<>();
        Pattern quotedString = Pattern.compile("\"(.+)\"");

        String[] lines = str.split("\n");
        for (int line = 0; line < lines.length; line++) {
            Scanner scanner = new Scanner(lines[line]);
            scanner.useLocale(Locale.US);
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int i = scanner.nextInt();
                    tokens.add(new IntegerToken(i, line));
                } else if (scanner.hasNextDouble()) {
                    double d = scanner.nextDouble();
                    tokens.add(new DoubleToken(d, line));
                } else if (scanner.hasNext(quotedString)) {
                    String str = scanner.next(quotedString);
                    tokens.add(new QuotedStringToken(str, line));
                } else {
                    String str = scanner.next();
                    if (str.equals(":=")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.ASSIGN, line));
                    } /*else if (str.equals("+")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.PLUS, line));
                    } else if (str.equals("-")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.MINUS, line));
                    } */else {
                        tokens.add(new StringToken(str, line));
                    }
                }
            }
            if (tokens.size() > 0 && !(tokens.get(tokens.size() - 1) instanceof NewlineToken))
                tokens.add(new NewlineToken(line));
        }
        return tokens;
    }
}
