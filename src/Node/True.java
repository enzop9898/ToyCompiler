package Node;

import Visitor.Visitor;

public class True extends Expr {

    private boolean value ;

    public True(boolean val) {
        super("TrueOP");
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
