package Node;

import Visitor.Visitor;

public class GT extends Expr{
    private Expr left;
    private Expr Right;

    public GT(String nomeNode, Expr left,Expr right) {
        super(nomeNode);
        this.left = left;
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
