package Node;

import Visitor.Visitor;

public class Int_const extends Expr{
    private int val;

    public Int_const(String nomeNode, int val) {
        super(nomeNode);
        this.val = val;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public int getVal() {
        return val;
    }
}
