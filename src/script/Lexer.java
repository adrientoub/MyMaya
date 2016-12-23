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
        for (String line: str.split("\n")) {
            Scanner scanner = new Scanner(line);
            scanner.useLocale(Locale.US);
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int i = scanner.nextInt();
                    tokens.add(new IntegerToken(i));
                } else if (scanner.hasNextDouble()) {
                    double d = scanner.nextDouble();
                    tokens.add(new DoubleToken(d));
                } else if (scanner.hasNext(quotedString)) {
                    String str = scanner.next(quotedString);
                    tokens.add(new QuotedStringToken(str));
                } else {
                    String str = scanner.next();
                    if (str.equals(":=")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.ASSIGN));
                    } else if (str.equals("+")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.PLUS));
                    } else if (str.equals("-")) {
                        tokens.add(new OperatorToken(OperatorToken.Operator.MINUS));
                    } else {
                        tokens.add(new StringToken(str));
                    }
                }
            }
            tokens.add(new NewlineToken());
        }
        return tokens;
    }
}
