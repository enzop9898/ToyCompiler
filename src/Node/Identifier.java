package Node;

import Visitor.Visitor;

public class Identifier extends Expr{
    private String val;

    public Identifier(String nomeNode, String val) {
        super(nomeNode);
        this.val = val;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "val='" + val + '\'' +
                '}';
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public String getVal() {
        return val;
    }
}
