package Node;

import Visitor.Visitor;

public class IFEXPR extends Expr {
    private Expr cond;
    private Expr severa;
    private Expr sefalsa;

    public IFEXPR(String nomeNode) {
        super(nomeNode);
    }

    public IFEXPR(String nomeNode, Expr cond, Expr severa, Expr sefalsa) {
        super(nomeNode);
        this.cond = cond;
        this.severa = severa;
        this.sefalsa = sefalsa;
    }

    public Expr getCond() {
        return cond;
    }

    public void setCond(Expr cond) {
        this.cond = cond;
    }

    public Expr getSevera() {
        return severa;
    }

    public void setSevera(Expr severa) {
        this.severa = severa;
    }

    public Expr getSefalsa() {
        return sefalsa;
    }

    public void setSefalsa(Expr sefalsa) {
        this.sefalsa = sefalsa;
    }

    @Override
    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }
}
