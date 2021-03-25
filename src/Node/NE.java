package Node;

import Visitor.Visitor;

public class NE extends Expr{
    private Expr left;
    private Expr Right;

    public NE(String nomeNode, Expr left, Expr right) {
        super(nomeNode);
        this.left = left;
        this.Right = right;
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
