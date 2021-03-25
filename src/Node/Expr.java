package Node;

import Visitor.*;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.ArrayList;

public abstract class Expr extends Stat {

    public ArrayList<SymbolTable.ValueType> types = new ArrayList<>();


    public Expr(String nomeNode) {
        super(nomeNode);

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

    public void setType(SymbolTable.ValueType t) {
        try {
            this.types.add(t);
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
    }

    public abstract <T> T accept(Visitor<T> syntaxVisitor);

}
