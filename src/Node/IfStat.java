package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class IfStat extends Stat {

    private Expr espressione;
    private ArrayList<Stat> listaStat;
    private ArrayList<Elif> listaElif;
    private Else els;

    public IfStat(String nomeNode, Expr espressione, ArrayList<Stat> listaStat, ArrayList<Elif> listaElif, Else els) {
        super(nomeNode);
        this.espressione = espressione;
        this.listaStat = listaStat;
        this.listaElif = listaElif;
        this.els = els;
    }

    public IfStat(String nomeNode, Expr espressione, ArrayList<Stat> listaStat, ArrayList<Elif> listaElif) {
        super(nomeNode);
        this.espressione = espressione;
        this.listaStat = listaStat;
        this.listaElif = listaElif;
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

    public ArrayList<Stat> getListaStat() {
        return listaStat;
    }

    public void setListaStat(ArrayList<Stat> listaStat) {
        this.listaStat = listaStat;
    }

    public ArrayList<Elif> getListaElif() {
        return listaElif;
    }

    public void setListaElif(ArrayList<Elif> listaElif) {
        this.listaElif = listaElif;
    }

    public Else getEls() {
        return els;
    }

    public void setEls(Else els) {
        this.els = els;
    }

    public IfStat(String nomeNode) {
        super(nomeNode);
    }
}
