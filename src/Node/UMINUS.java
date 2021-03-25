package Node;

import Visitor.Visitor;

public class UMINUS extends Expr{
    private Expr child;


    public UMINUS(String nomeNode, Expr child) {
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
