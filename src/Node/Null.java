package Node;

import Visitor.Visitor;

public class Null  extends Expr{

    private Object value = null;

    public Null(String nomeNode) {
        super(nomeNode);
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Null{" +
                "value=" + value +
                ", name='" + super.getNomeNode() + '\''+
                '}';
    }

    @Override
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return null;
    }
}
