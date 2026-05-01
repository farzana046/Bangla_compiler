
import java.util.*;

public class Lexer {
    private final String src;
    private int pos = 0;
    private int line = 1;

    public Lexer(String src) {
        this.src = src;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < src.length()) {
            char c = peek();

            if (Character.isWhitespace(c)) {
                if (c == '\n') line++;
                advance();
            }
            else if (isBanglaLetter(c)) {
                tokens.add(identifier());
            }
            else if (isBanglaDigit(c)) {
                tokens.add(number());
            }
            else {
                switch (c) {
                    case '=': tokens.add(new Token(Token_Type.ASSIGN,"=",line)); break;
                    case '+': tokens.add(new Token(Token_Type.PLUS,"+",line)); break;
                    case '-': tokens.add(new Token(Token_Type.MINUS,"-",line)); break;
                    case '*': tokens.add(new Token(Token_Type.MUL,"*",line)); break;
                    case '/': tokens.add(new Token(Token_Type.DIV,"/",line)); break;
                    case '(': tokens.add(new Token(Token_Type.LPAREN,"(",line)); break;
                    case ')': tokens.add(new Token(Token_Type.RPAREN,")",line)); break;
                    case ';': tokens.add(new Token(Token_Type.SEMI,";",line)); break;
                    default:
                        throw new RuntimeException("Invalid char: "+c);
                }
                advance();
            }
        }

        tokens.add(new Token(Token_Type.EOF,"",line));
        return tokens;
    }

    private Token identifier() {
        StringBuilder sb = new StringBuilder();
        while (pos < src.length() && isBanglaLetter(peek())) {
            sb.append(advance());
        }

        String word = sb.toString();

        if (word.equals("ধরো")) return new Token(Token_Type.DHORO, word, line);
        if (word.equals("সংখ্যা")) return new Token(Token_Type.INT_TYPE, word, line);
        if (word.equals("বাস্তব")) return new Token(Token_Type.FLOAT_TYPE, word, line);

        return new Token(Token_Type.ID, word, line);
    }

    private Token number() {
        StringBuilder sb = new StringBuilder();
        while (pos < src.length() && isBanglaDigit(peek())) {
            sb.append(advance());
        }
        return new Token(Token_Type.NUMBER, sb.toString(), line);
    }

private boolean isBanglaLetter(char c) {
    return ((c >= '\u0980' && c <= '\u09FF') || Character.isLetter(c))
            && !isBanglaDigit(c);
}
    private boolean isBanglaDigit(char c) {
        return c >= '০' && c <= '৯';
    }

    private char peek() { return src.charAt(pos); }
    private char advance() { return src.charAt(pos++); }
}