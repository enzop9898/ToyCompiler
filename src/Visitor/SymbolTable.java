package Visitor;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SymbolTable extends Hashtable<String, SymbolTabRow> {

    public SymbolTable padre;
    public String nomeTab;

    public void setPadre(SymbolTable padre) {
        this.padre = padre;
    }

    public void setNomeTab(String nomeTab) {
        this.nomeTab = nomeTab;
    }

    public SymbolTable getPadre() {
        return padre;
    }

    public String getNomeTab() {
        return nomeTab;
    }

    public boolean haTabellaPadre() {
        if (padre != null) return true; else return false;
    }

    public void creaEntryVar(String id, String tipo) throws Exception {
        if (super.containsKey(id)) throw new Exception("Semantic error in " + nomeTab + ": identifier '" + id + "'variable already declared in the actual scope");
        else super.put(id, new SymbolTabRow(id, StringToType(tipo)));
    }

    public void creaEntryFunction(String id, ArrayList<ValueType> inputPar, ArrayList<ValueType> outputPar) throws Exception {
        if (super.containsKey(id)) throw new Exception("Semantic error in " + nomeTab + ": identifier '" + id + "' function already declared in the actual scope");
        super.put(id, new SymbolTabRow(id, inputPar, outputPar));
    }

    public SymbolTabRow containsEntry(String id) throws Exception {
        SymbolTabRow ste = null;
        if (super.containsKey(id)) {
            ste = super.get(id);
        } else if (haTabellaPadre()) {
            ste = getPadre().containsEntry(id);
        } else {
            System.err.println("Semantic error: variable " + id + " not declared");
            System.exit(0);
        }
        return ste;
    }

    public Boolean containsKey(String id) throws Exception {
        if (super.containsKey(id)) {
            return true;
        } else if (haTabellaPadre()) {
            return getPadre().containsKey(id);
        } else {
            System.err.println("Semantic error: variable " + id + " not declared");
            System.exit(0);
        }
        return null;
    }

    public ArrayList<SymbolTabRow> getFunctions() {
        ArrayList<SymbolTabRow> listOfEntries = new ArrayList<>();
        for (SymbolTabRow ste: super.values())
            if (!ste.isVariable() && !ste.id.equalsIgnoreCase("main"))
                listOfEntries.add(ste);
        if (listOfEntries.size() == 0) return null; else return listOfEntries;
    }

    @Override
    public String toString() {
        String str = nomeTab + "\n";
        for (SymbolTabRow ste : super.values())
            str += (ste.toString() + "\n");
        return str;
    }

    public static enum Kind {
        Variable,
        Function,
    }

    public static enum ValueType {
        Integer,
        String,
        Float,
        Boolean,
        Null,
        Void,
    }

    public static ValueType StringToType(String type) throws Exception {
        if (type.equalsIgnoreCase("int")) return ValueType.Integer;
        if (type.equalsIgnoreCase("string")) return ValueType.String;
        if (type.equalsIgnoreCase("float")) return ValueType.Float;
        if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) return ValueType.Boolean;
        if (type.equalsIgnoreCase("null")) return ValueType.Null;
        if (type.equalsIgnoreCase("void")) return ValueType.Void;
        throw new Exception("Semantic error: type " + type + " does not exists");
    }



}
