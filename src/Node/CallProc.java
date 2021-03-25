package Node;

import Visitor.*;

import java.util.ArrayList;

public class CallProc extends Expr {

    private Identifier id;
    private ArrayList<Expr> exprList;


    public CallProc(String nomeNode, Identifier id, ArrayList<Expr> exprList) {
        super(nomeNode);
        this.id = id;
        this.exprList = exprList;
    }

    public CallProc(String nomeNode, Identifier id) {
        super(nomeNode);
        this.id = id;
    }

    public void setTypes(ArrayList t) {
        try {
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i) instanceof String)
                    this.types.add(SymbolTable.StringToType((String)t.get(i)));
                else this.types.add((SymbolTable.ValueType) t.get(i));
            }
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
    }

    public void setType(String t) {
        try {
            this.types.add(SymbolTable.StringToType(t));
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public Identifier getId() {
        return id;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }
}
