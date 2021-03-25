package Node;

import Visitor.Visitor;

import java.util.ArrayList;

public class WriteStat extends Stat {

    private ArrayList<Expr> exprLista;

    public WriteStat(String nomeNode, ArrayList<Expr> exprLista) {
        super(nomeNode);
        this.exprLista = exprLista;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<Expr> getExprLista() {
        return exprLista;
    }
}
