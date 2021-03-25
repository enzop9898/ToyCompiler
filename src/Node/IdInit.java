package Node;

import Visitor.Visitor;

public class IdInit extends Node{

    private Identifier id;
    private Expr espressione;

    public IdInit(String nomeNode, Identifier id) {
        super(nomeNode);
        this.id = id;
    }

    public IdInit(String nomeNode, Identifier id, Expr espressione) {
        super(nomeNode);
        this.id = id;
        this.espressione = espressione;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public Expr getEspressione() {
        return espressione;
    }

    public void setEspressione(Expr espressione) {
        this.espressione = espressione;
    }


}
