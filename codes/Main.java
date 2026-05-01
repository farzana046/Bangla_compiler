import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("PROGRAM STARTED");

        try {
            String code = Files.readString(
                    Paths.get("input.bn"),
                    StandardCharsets.UTF_8
            );

            System.out.println("INPUT:\n" + code);

            Lexer lexer = new Lexer(code);
            List<Token> tokens = lexer.tokenize();

            System.out.println("TOKENS:");
            tokens.forEach(System.out::println);

            System.out.println("TOKENS SIZE: " + tokens.size());

            Parser parser = new Parser(tokens);
            List<AST_Node> ast = parser.parse();

            System.out.println("\nAST:");
            ast.forEach(n -> System.out.println(n.visualize(0)));

            Symbol_Table table = new Symbol_Table();
            Semantic_Analyzer sem = new Semantic_Analyzer(table);
            sem.analyze(ast);

            System.out.println("\nSYMBOL TABLE:");
            table.getAll().forEach((k,v)-> System.out.println(k+" : "+v));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}