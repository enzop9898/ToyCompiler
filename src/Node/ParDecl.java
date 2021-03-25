package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class ParDecl extends Node {

    private Type tipo;
    private ArrayList<Identifier> listaId;

    public ParDecl(String nomeNode, Type tipo, ArrayList<Identifier> listaId) {
        super(nomeNode);
        this.tipo = tipo;
        this.listaId = listaId;
    }

    public ParDecl(String nomeNode, Type tipo) {
        super(nomeNode);
        this.tipo = tipo;
    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ParDecl(String nomeNode, ArrayList<Identifier> listaId) {
        super(nomeNode);
        this.listaId = listaId;
    }

    public Type getTipo() {
        return tipo;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Identifier> getListaId() {
        return listaId;
    }

    public void setListaId(ArrayList<Identifier> listaId) {
        this.listaId = listaId;
    }
}
