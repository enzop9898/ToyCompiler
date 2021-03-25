package Node;

import Visitor.Visitor;

public class EQ extends Expr{
    private Expr left;
    private Expr Right;

    public EQ(String nomeNode, Expr left, Expr right) {
        super(nomeNode);
        this.Right= right;
        this.left = left;
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
