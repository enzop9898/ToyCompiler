package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class Else extends Node {

    private ArrayList<Stat> statList;

    public Else(String nomeNode, ArrayList<Stat> statList) {
        super(nomeNode);
        this.statList = statList;
    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
    public ArrayList<Stat> getStatList() {
        return statList;
    }
}
