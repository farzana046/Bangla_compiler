    import java.util.*;

public class Semantic_Analyzer {

    private final Symbol_Table table;

    public Semantic_Analyzer(Symbol_Table t) {
        table = t;
    }

    public void analyze(List<AST_Node> nodes) {
        for (AST_Node n : nodes) visit(n);
    }

    private String visit(AST_Node node) {

        if (node instanceof AssignNode a) {

            String exprType = visit(a.expr);

            if (!a.type.equals(exprType)) {
                throw new RuntimeException("Type mismatch for " + a.name);
            }

            table.declare(a.name,a.type);
            return a.type;
        }

        if (node instanceof BinOpNode b) {
            String l = visit(b.left);
            String r = visit(b.right);

            if (!l.equals(r)) {
                throw new RuntimeException("Type mismatch in expression");
            }
            return l;
        }

        if (node instanceof VarNode v) {
            return table.get(v.name);
        }

        if (node instanceof NumberNode) {
            return "int";
        }

        throw new RuntimeException("Unknown node");
    }
}

