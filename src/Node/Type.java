package Node;

import Visitor.Visitor;

public class Type extends Node {



    public Type(String nomeNode) {
        super(nomeNode);

    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
}
