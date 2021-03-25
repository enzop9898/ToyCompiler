package Node;

import Visitor.Visitor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import Visitor.SymbolTable;

public class Proc extends Node {

   public SymbolTable symbolTable;

   private Identifier ID;
   private ArrayList<ParDecl> pardec;
   private ArrayList<Type> resdec;
   private ArrayList<VarDecl> vardec;
   private ArrayList<Stat> statementlist;
   private ReturnExprs retexpr;

   public Proc(String nomeNode, Identifier ID) {
      super(nomeNode);
      this.ID = ID;
   }

   public Proc(String nomeNode, Identifier ID, ArrayList<ParDecl> pardec, ArrayList<Type> resdec, ArrayList<VarDecl> vardec, ArrayList<Stat> statementlist, ReturnExprs retexpr) {
      super(nomeNode);
      this.ID = ID;
      this.pardec = pardec;
      this.resdec = resdec;
      this.vardec = vardec;
      this.statementlist = statementlist;
      this.retexpr = retexpr;
   }

   public SymbolTable initSymbolTable() {
      this.symbolTable = new SymbolTable();
      this.symbolTable.nomeTab = "Function " + ID + " scope";
      return this.symbolTable;
   }

   public <T> T accept(Visitor<T> syntaxVisitor) {
      return syntaxVisitor.visit(this);
   }

   public Identifier getID() {
      return ID;
   }

   public void setID(Identifier ID) {
      this.ID = ID;
   }

   public ArrayList<ParDecl> getPardec() {
      return pardec;
   }

   public void setPardec(ArrayList<ParDecl> pardec) {
      this.pardec = pardec;
   }

   public ArrayList<Type> getResdec() {
      return resdec;
   }

   public void setResdec(ArrayList<Type> resdec) {
      this.resdec = resdec;
   }

   public ArrayList<VarDecl> getVardec() {
      return vardec;
   }

   public void setVardec(ArrayList<VarDecl> vardec) {
      this.vardec = vardec;
   }

   public ArrayList<Stat> getStatementlist() {
      return statementlist;
   }

   public void setStatementlist(ArrayList<Stat> statementlist) {
      this.statementlist = statementlist;
   }

   public ReturnExprs getRetexpr() {
      return retexpr;
   }

   public void setRetexpr(ReturnExprs retexpr) {
      this.retexpr = retexpr;
   }
}
