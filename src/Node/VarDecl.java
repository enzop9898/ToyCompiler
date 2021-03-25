package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class VarDecl extends Node{
    private Type tipo;
    private ArrayList<IdInit> idlista;

    public VarDecl(String nomeNode) {
        super(nomeNode);
    }

    public VarDecl(String nomeNode, Type tipo, ArrayList<IdInit> idlista) {
        super(nomeNode);
        this.tipo = tipo;
        this.idlista = idlista;
    }
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public Type getTipo() {
        return tipo;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public ArrayList<IdInit> getIdlista() {
        return idlista;
    }

    public void setIdlista(ArrayList<IdInit> idlista) {
        this.idlista = idlista;
    }
}
