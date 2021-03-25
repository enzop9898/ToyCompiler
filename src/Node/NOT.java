package Node;

import Visitor.Visitor;

public class NOT extends Expr{
    private Expr child;

    public NOT(String nomeNode, Expr child) {
        super(nomeNode);
        this.child = child;
    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
    public Expr getChild() {
        return child;
    }
}
