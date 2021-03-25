package Node;

import Visitor.Visitor;

public class Float_const extends Expr{
    private float val;

    public Float_const(String nomeNode, float val) {
        super(nomeNode);
        this.val = val;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public float getVal() {
        return val;
    }
}
