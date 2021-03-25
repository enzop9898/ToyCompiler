package Node;

import Visitor.Visitor;

public class False extends Expr {

    private boolean value;

    public False(boolean val) {
        super("falseOP");
        this.value = val;
    }


    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
}
