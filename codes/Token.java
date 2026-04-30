public class Token {
    public final Token_Type type;
    public final String lexeme;
    public final int line;

    public Token(Token_Type type, String lexeme, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    public String toString() {
        return "Token{" + type + ", '" + lexeme + "'}";
    }
}