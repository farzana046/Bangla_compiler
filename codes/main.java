import java.util.*;

public class Main {
    public static void main(String[] args) {

        String code = """
        ধরো সংখ্যা ক = ১০০;
        ধরো সংখ্যা খ = ২০;
        ধরো সংখ্যা ফলাফল = ক - খ;
        """;

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        System.out.println("TOKENS:");
        tokens.forEach(System.out::println);

        Parser parser = new Parser(tokens);
        List<AST_Node> ast = parser.parse();

        System.out.println("\nAST:");
        ast.forEach(n -> System.out.println(n.visualize(0)));

        Symbol_Table table = new Symbol_Table();
        Semantic_Analyzer sem = new Semantic_Analyzer(table);
        sem.analyze(ast);

        System.out.println("\nSYMBOL TABLE:");
        table.getAll().forEach((k,v)-> System.out.println(k+" : "+v));
    }
}
