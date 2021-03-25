package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class WhileStat extends Stat {

    private ArrayList<Stat> statList1;
    private Expr espressione;
    private ArrayList<Stat> statList2;

    public WhileStat(String nomeNode, Expr espressione, ArrayList<Stat> statList1) {
        super(nomeNode);
        this.espressione = espressione;
        this.statList2 = statList1;

    }

    public WhileStat(String nomeNode, ArrayList<Stat> statList1, Expr espressione, ArrayList<Stat> statList2) {
        super(nomeNode);
        this.statList1 = statList1;
        this.espressione = espressione;
        this.statList2 = statList2;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<Stat> getStatList1() {
        return statList1;
    }

    public Expr getEspressione() {
        return espressione;
    }

    public ArrayList<Stat> getStatList2() {
        return statList2;
    }
}
