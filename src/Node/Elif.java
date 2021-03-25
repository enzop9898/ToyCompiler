package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class Elif extends Node {

    private Expr espressione;
    private ArrayList<Stat> listaStat;

    public Elif(String nomeNode, Expr espressione, ArrayList<Stat> listaStat) {
        super(nomeNode);
        this.espressione = espressione;
        this.listaStat = listaStat;
    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
    public Expr getEspressione() {
        return espressione;
    }
    public ArrayList<Stat> getListaStat() {
        return listaStat;
    }
}
