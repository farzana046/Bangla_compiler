abstract class AST_Node {
    public abstract String visualize(int indent);

    protected String getIndent(int indent) {
        return "  ".repeat(indent);
    }
}

class AssignNode extends AST_Node {
    public final String name;
    public final String type;
    public final AST_Node expr;

    public AssignNode(String name, String type, AST_Node expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
    }

    public String visualize(int indent) {
        return getIndent(indent) + "Assign (" + type + ") " + name + "\n" +
                expr.visualize(indent + 1);
    }
}

class BinOpNode extends AST_Node {
    public final AST_Node left;
    public final Token op;
    public final AST_Node right;

    public BinOpNode(AST_Node l, Token o, AST_Node r) {
        left = l;
        op = o;
        right = r;
    }

    public String visualize(int indent) {
        return getIndent(indent) + "Op: " + op.lexeme + "\n" +
                left.visualize(indent + 1) + "\n" +
                right.visualize(indent + 1);
    }
}

class VarNode extends AST_Node {
    public final String name;

    public VarNode(String name) { this.name = name; }

    public String visualize(int indent) {
        return getIndent(indent) + "Var: " + name;
    }
}

class NumberNode extends AST_Node {
    public final String value;

    public NumberNode(String v) { value = v; }

    public String visualize(int indent) {
        return getIndent(indent) + "Number: " + value;
    }
}