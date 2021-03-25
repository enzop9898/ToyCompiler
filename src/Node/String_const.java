package Node;

import Visitor.Visitor;

public class String_const extends Expr{
    private String val;

    public String_const(String nomeNode, String val) {
        super(nomeNode);
        this.val = val;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public String getVal() {
        return val;
    }
}
