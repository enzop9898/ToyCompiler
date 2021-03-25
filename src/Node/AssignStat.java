package Node;

import Visitor.Visitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssignStat extends Stat {

    private ArrayList<Identifier> idList;
    private ArrayList<Expr> exprList;

    public AssignStat(String nomeNode, ArrayList<Identifier> idList, ArrayList<Expr> exprList) {
        super(nomeNode);
        this.idList = idList;
        this.exprList = exprList;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<Identifier> getIdList() {
        return idList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }
}
