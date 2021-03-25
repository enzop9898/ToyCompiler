package Node;

import Visitor.Visitor;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class ReturnExprs extends Node {

    private ArrayList<Expr> listaExpr;

    public ReturnExprs(String nomeNode) {
        super(nomeNode);
    }

    public ReturnExprs(String nomeNode, ArrayList<Expr> listaExpr) {
        super(nomeNode);
        this.listaExpr = listaExpr;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<Expr> getListaExpr() {
        return listaExpr;
    }

    public void setListaExpr(ArrayList<Expr> listaExpr) {
        this.listaExpr = listaExpr;
    }
}
