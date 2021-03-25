package Node;

import Visitor.Visitor;

public class MINUS extends Expr{
    private Expr left;
    private Expr Right;

    public MINUS(String nomeNode, Expr left, Expr right) {
        super(nomeNode);
        this.left = left;
        Right = right;
    }

    public MINUS(String nomeNode, Expr right) {
        super(nomeNode);

        Right = right;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return Right;
    }
}
