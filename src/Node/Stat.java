package Node;


import Visitor.Visitor;

public abstract class Stat extends Node {


    public Stat(String nomeNode) {
        super(nomeNode);

    }
    public abstract <T> T accept(Visitor<T> syntaxVisitor);


}
