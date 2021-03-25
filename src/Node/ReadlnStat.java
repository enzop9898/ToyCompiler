package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class ReadlnStat extends Stat {

    private ArrayList<Identifier> idLista;

    public ReadlnStat(String nomeNode, ArrayList<Identifier> idLista) {
        super(nomeNode);
        this.idLista = idLista;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<Identifier> getIdLista() {
        return idLista;
    }
}
