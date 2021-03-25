package Node;

import Visitor.Visitor;
import Visitor.SymbolTable;
import java.util.ArrayList;

public class Program extends Node {

    public SymbolTable symbolTable;

    private ArrayList<VarDecl> vardeclist;
    private ArrayList<Proc> proclist;

    public Program(String nomeNode, ArrayList<VarDecl> vardeclist, ArrayList<Proc> proclist) {
        super(nomeNode);
        this.vardeclist = vardeclist;
        this.proclist = proclist;
    }

    public SymbolTable initSymbolTable() {
        this.symbolTable = new SymbolTable();
        this.symbolTable.nomeTab = "Global scope";
        return this.symbolTable;
    }

    public <T> T accept(Visitor<T> syntaxVisitor) {
        return syntaxVisitor.visit(this);
    }

    public ArrayList<VarDecl> getVardeclist() {
        return vardeclist;
    }

    public ArrayList<Proc> getProclist() {
        return proclist;
    }
}
