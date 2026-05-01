
import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<AST_Node> parse() {
        List<AST_Node> list = new ArrayList<>();

        while (!isAtEnd()) {
            try {
                list.add(statement());
            } catch (Exception e) {
                System.out.println("Parser Error: " + e.getMessage());
                synchronize();
            }
        }

        return list;
    }

    private AST_Node statement() {
        consume(Token_Type.DHORO,"Missing ধরো");

        Token typeTok = advance();

if (typeTok.type != Token_Type.INT_TYPE && typeTok.type != Token_Type.FLOAT_TYPE) {
    throw new RuntimeException("Expected type after ধরো");
}

String type = typeTok.type == Token_Type.INT_TYPE ? "int" : "float";

        Token name = consume(Token_Type.ID,"Missing variable");

        consume(Token_Type.ASSIGN,"Missing =");

        AST_Node expr = expression();

        consume(Token_Type.SEMI,"Missing ;");

        return new AssignNode(name.lexeme,type,expr);
    }

    private AST_Node expression() {
        AST_Node node = term();

        while (match(Token_Type.PLUS,Token_Type.MINUS)) {
            Token op = previous();
            node = new BinOpNode(node,op,term());
        }
        return node;
    }

    private AST_Node term() {
        AST_Node node = factor();

        while (match(Token_Type.MUL,Token_Type.DIV)) {
            Token op = previous();
            node = new BinOpNode(node,op,factor());
        }
        return node;
    }

private AST_Node factor() {
    if (match(Token_Type.NUMBER)) return new NumberNode(previous().lexeme);
    if (match(Token_Type.ID)) return new VarNode(previous().lexeme);

    // Add this block:
    if (match(Token_Type.LPAREN)) {
        AST_Node expr = expression();
        consume(Token_Type.RPAREN, "Missing )");
        return expr;
    }

    throw new RuntimeException("Invalid expression");
}

    private void synchronize() {
        while (!isAtEnd()) {
            if (previous().type == Token_Type.SEMI) return;
            advance();
        }
    }

    private boolean match(Token_Type... types) {
        for (Token_Type t : types) {
            if (check(t)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(Token_Type t,String msg) {
        if (check(t)) return advance();
        throw new RuntimeException(msg);
    }

    private boolean check(Token_Type t) {
        return !isAtEnd() && peek().type == t;
    }

    private Token advance() { return tokens.get(pos++); }
    private Token previous() { return tokens.get(pos-1); }
    private Token peek() { return tokens.get(pos); }
    private boolean isAtEnd() { return peek().type == Token_Type.EOF; }
}