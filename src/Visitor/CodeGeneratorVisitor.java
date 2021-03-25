package Visitor;

import Node.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CodeGeneratorVisitor implements Visitor<Object>{

    private File file = null;
    private SymbolTable roottable;
    StringBuilder code = new StringBuilder();
    private int statCount = 0;

    private void scriviSuFile(String msg) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(msg);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public File conversion(String filename, Program pnode) {

        try {
            file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
                if (file.delete()) {
                    System.out.println("Deleted the file: " + file.getName());
                    if (file.createNewFile()) {
                        System.out.println("File re-created: " + file.getName());
                    }
                } else {
                    System.out.println("Failed to delete the file.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred in file creation.");
            e.printStackTrace();
        }

        if (file == null) {
            System.err.println("Cannot generate the file.");
            System.exit(0);
        } else {
            String programm = "#include <stdio.h>\n#include <stdlib.h>\n#include <stdbool.h>\n#include <string.h>\n\n" + (String) visit(pnode);
            programm = programm.replaceAll("\n","\r\n");
            scriviSuFile(programm);
            try {
                System.out.println(file.getCanonicalPath());
                //Runtime.getRuntime().exec("astyle --style=google " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    @Override
    public Object visit(Program progOP) {
        String toWrite = "";
        roottable = progOP.symbolTable;

        ArrayList<SymbolTabRow> functionsEntry = roottable.getFunctions();
        if (functionsEntry != null) {
            toWrite += "// Structure for return type\n\n";
            for (SymbolTabRow stR : functionsEntry) {
                if (stR.outputParams.get(0) == SymbolTable.ValueType.Void) continue;
                toWrite += "struct str_" + stR.id +"{\n";
                int index = 1;
                for (SymbolTable.ValueType vtype : stR.outputParams) { // nomeFunc;  int -> int, int, float ... struct str_nomeFunc { int par1; int par2; int par2;};
                    switch (vtype) {
                        case Integer:
                            toWrite += ("int par"+index+";\n");
                            break;
                        case Float:
                            toWrite += ("float par"+index+";\n");
                            break;
                        case String:
                            toWrite += ("char par"+index+"[512];\n");
                            break;
                        case Boolean:
                            toWrite += ("bool par"+index+";\n");
                            break;
                    }
                    index++;

                }
                toWrite += "};\n\n";
            }
        }

        toWrite += "\n//Global Var\n\n";
        for(VarDecl vardec : progOP.getVardeclist()) {
            toWrite += vardec.accept(this) +"\n";
        }
        toWrite += "\n";

        //concat per somma tra due string

        toWrite += "\n\nchar* concat(char* first, char* second) {\n" +
                "    int i = 0, j = 0; \n" +
                "    char* str3 = malloc(" + 512 + " * sizeof(char));\n" +
                "    // Insert the first string in the new string \n" +
                "    while (first[i] != '\\0') { \n" +
                "        str3[j] = first[i]; \n" +
                "        i++; \n" +
                "        j++; \n" +
                "    }\n" +
                "  \n" +
                "    // Insert the second string in the new string \n" +
                "    i = 0; \n" +
                "    while (second[i] != '\\0') { \n" +
                "        str3[j] = second[i]; \n" +
                "        i++; \n" +
                "        j++; \n" +
                "    } \n" +
                "    str3[j] = '\\0'; \n" +
                "    return str3;\n" +
                "}\n\n";

        //end concat

        toWrite += "\n//Program Fuctions\n\n";

        for(Proc pr: progOP.getProclist()) {
            toWrite += pr.accept(this) + "\n";
        }
        toWrite += "\n";

        return toWrite;
    }

    @Override
    public Object visit(VarDecl vardeclOP) {
        String toWrite = "";

        //type
        toWrite += getCTypeFromTypeNodeValue(vardeclOP.getTipo().getNomeNode());

        for (int i=0; i<vardeclOP.getIdlista().size(); i++) {
            if (i < vardeclOP.getIdlista().size() -1) toWrite += vardeclOP.getIdlista().get(i).accept(this) + ", ";
            else toWrite += vardeclOP.getIdlista().get(i).accept(this) + ";";
        }

        return toWrite;
    }

    @Override
    public Object visit(Proc procOP) {
        String toWrite = "";
        String toRetValue = "";

        if (procOP.getID().getVal().equalsIgnoreCase("main")) {
            toWrite += "int main (";
        } else {
            String nameFunzione = "" + procOP.getID().accept(this);

            if (procOP.getResdec().get(0).getNomeNode().equalsIgnoreCase("void")) toWrite += "void "+ nameFunzione + "(";
            else {
                toWrite += "struct str_"+ nameFunzione + " " + nameFunzione + "(";
                toRetValue = "struct str_" + nameFunzione + " toReturn;\n";
            }
        }

        if (procOP.getPardec() != null && procOP.getPardec().size() > 0) {
            for (int i=0; i < procOP.getPardec().size(); i++) {
                if (i < procOP.getPardec().size() -1) toWrite += procOP.getPardec().get(i).accept(this) + ", ";
                else toWrite += procOP.getPardec().get(i).accept(this);
            }
        }

        if (toRetValue.equalsIgnoreCase("")) toWrite += "){\n";
        else toWrite += "){\n"+ "\t"+toRetValue+"\n";

        //body

        if (procOP.getVardec() != null && procOP.getVardec().size() > 0 ) {
            for (VarDecl vc: procOP.getVardec()) {
                toWrite += " "+ vc.accept(this)+"\n";
            }
        }

        if (procOP.getStatementlist() != null && procOP.getStatementlist().size() >0) {
            for (Stat vc: procOP.getStatementlist()) {
                if (vc instanceof CallProc) toWrite += ""+ vc.accept(this) + ";\n";
                else toWrite += ""+ vc.accept(this);
            }
        }


        if (procOP.getRetexpr() != null && procOP.getRetexpr().getListaExpr().size() > 0) {
            toWrite += procOP.getRetexpr().accept(this);
        } else if (procOP.getID().getVal().equalsIgnoreCase("main")) {
            toWrite += "return 0;\n";
        }

        //end body

        toWrite += "} \n";
        return toWrite;
    }

    @Override
    public Object visit(IfStat ifstatOP) { // .par1
        String toWrite = "";
        toWrite += "if (" +
                ((ifstatOP.getEspressione() instanceof CallProc) ? ifstatOP.getEspressione().accept(this) + ".par1" : ifstatOP.getEspressione().accept(this))
                + ") {\n";
        if (ifstatOP.getListaStat() != null && ifstatOP.getListaStat().size() > 0){
            for ( Stat s : ifstatOP.getListaStat()) {
                if (s instanceof CallProc)toWrite += s.accept(this)+";\n";
                else toWrite += s.accept(this);
            }
        }
        toWrite += "} \n";
        if (ifstatOP.getListaElif() != null && ifstatOP.getListaElif().size() > 0) {
            for ( Elif el : ifstatOP.getListaElif()) {
                toWrite += el.accept(this);
            }
        }
        if (ifstatOP.getEls() != null && ifstatOP.getEls().getStatList().size() > 0) {
            toWrite += "else {\n";
            for (Stat s : ifstatOP.getEls().getStatList()) {
                toWrite += s.accept(this);
            }
            toWrite += "}\n";
        }

        return toWrite;
    }

    @Override
    public Object visit(Type typeOP) {
        return "";
    }

    @Override
    public Object visit(IdInit idinitOP) {
        String toWrite = "";

        toWrite += idinitOP.getId().accept(this);
        if (idinitOP.getId().types.get(0) == SymbolTable.ValueType.String) toWrite += "[512]";
        if (idinitOP.getEspressione() != null ) {
            toWrite += " = " + idinitOP.getEspressione().accept(this);
        }
        return toWrite;
    }

    @Override
    public Object visit(ReturnExprs returnexprOP) {
        String toWrite = "";
        int indice = 0;

        for (Expr ex : returnexprOP.getListaExpr()) {
            if (ex instanceof CallProc) {
                toWrite += "struct str_"+((CallProc) ex).getId().accept(this)+ " strRet_"+  ((CallProc) ex).getId().accept(this)+ "_"+ indice+
                        " = "+ ex.accept(this) +";\n";
            }
        }

        indice = 0;
        int parIndice = 1;
        for (int i = 0; i < returnexprOP.getListaExpr().size(); i++, parIndice++) {
            if (returnexprOP.getListaExpr().get(i) instanceof CallProc) {
                indice++;
                try {
                    int dim = roottable.containsEntry(((CallProc) returnexprOP.getListaExpr().get(i)).getId().getVal()).outputParams.size();
                    for (int k = 0; k < dim; k++) {
                        toWrite += " toReturn.par"+parIndice+" = strRet_"+((CallProc) returnexprOP.getListaExpr().get(i)).getId().accept(this)+"_"+indice+".par"+(k+1)+";\n";
                        if (k < dim -1) parIndice++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else toWrite += " toReturn.par"+parIndice+" = "+ returnexprOP.getListaExpr().get(i).accept(this)+";\n";
        }
        toWrite += "return toReturn;\n";
        return toWrite;
    }

    @Override
    public Object visit(ParDecl pardeclOP) {
        String toWrite = "";
        String tipoPar = "";
        if (pardeclOP.getTipo().getNomeNode().equalsIgnoreCase("string")) tipoPar="char ";
        else tipoPar = pardeclOP.getTipo().getNomeNode()+" ";
        if (pardeclOP.getListaId() != null && pardeclOP.getListaId().size() > 0) {
            for (int i=0; i < pardeclOP.getListaId().size(); i++) {
                String id =(String) pardeclOP.getListaId().get(i).accept(this);
                if (i < pardeclOP.getListaId().size() - 1) {
                    if (tipoPar.equalsIgnoreCase("char ")) toWrite += tipoPar + id +"[512]"+", ";
                    else toWrite += tipoPar + id + ", ";
                } else {
                    if (tipoPar.equalsIgnoreCase("char ")) toWrite += tipoPar + id +"[512]";
                    else toWrite += tipoPar + id;
                }
            }
        }
        return toWrite;
    }

    @Override
    public Object visit(ReadlnStat readlnOP) {
        String toWrite = "";
        String tempvarbool = "";
        String tempvarboolAssign = "";
        String nome ="";

        for ( int i=0; i < readlnOP.getIdLista().size(); i++) {
            Identifier id = readlnOP.getIdLista().get(i);
            SymbolTable.ValueType tipoID = id.types.get(0);
            if (tipoID == SymbolTable.ValueType.Integer) toWrite += "scanf(\"%d\", &"+id.getVal()+");\n";
            else if (tipoID == SymbolTable.ValueType.Float) toWrite += "scanf(\"%f\", &"+id.getVal()+");\n";
            else if (tipoID == SymbolTable.ValueType.String) toWrite += "scanf(\"%s\", &"+id.getVal()+");\n";
            else if (tipoID == SymbolTable.ValueType.Boolean){
                nome += "tempBool_"+id.accept(this);
                tempvarbool += "int "+ nome+ ";\n";
                tempvarboolAssign += id.accept(this) +" = " + nome+ ";\n";
                toWrite += tempvarbool + tempvarboolAssign + "scanf(\"%d\", &"+nome+");\n";
            }
        } // int tempBool_primo;     primo = tempBool_primo;   scanf("%d", &tempBool_primo);

        return toWrite;
    }

    @Override
    public Object visit(WriteStat writestatOP) {
        String toWrite = "";
        String printString = "";
        String varabiliafineString = "";

        int indice = 0;
        int prev_stat_indice = statCount;


        for (Expr e : writestatOP.getExprLista()) {
            if (e instanceof CallProc) {
                indice++;
                statCount++;
                toWrite += "struct str_" + ((CallProc) e).getId().accept(this) + " strTmp_"+ ((CallProc) e).getId().accept(this) +
                            "_" + indice +"_"+ statCount + " =" + e.accept(this) + ";\n";
            }
        }

        indice = 0;
        statCount = prev_stat_indice;

        for (int i = 0; i < writestatOP.getExprLista().size(); i++) {
            if ( writestatOP.getExprLista().get(i) instanceof CallProc) {
                indice++;
                statCount++;
                try{
                    ArrayList<SymbolTable.ValueType> outPar = roottable.containsEntry(((CallProc) writestatOP.getExprLista().get(i)).getId().getVal()).outputParams;
                    for ( int j = 0; j< outPar.size(); j++) {
                        printString += getCScanPrintSymbolByToySemanticType(outPar.get(j));
                        varabiliafineString += "strTmp_"+((CallProc) writestatOP.getExprLista().get(i)).getId().accept(this)+ "_"+indice+"_"+statCount+".par"+(j+1);
                        printString += " ";
                        varabiliafineString += ", ";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            } else if (writestatOP.getExprLista().get(i) instanceof String_const) {
                printString += ((String) writestatOP.getExprLista().get(i).accept(this)).replace("\"", "");

            } else {
                printString += getCScanPrintSymbolByToySemanticType(writestatOP.getExprLista().get(i).types.get(0));
                varabiliafineString += "("+writestatOP.getExprLista().get(i).accept(this)+ ")";
                varabiliafineString += ", ";
            }
        }
        if (varabiliafineString.length() > 0) varabiliafineString = varabiliafineString.substring(0, varabiliafineString.length() -2);
        if (varabiliafineString.equalsIgnoreCase("")) toWrite += "printf(\""+ printString+"\");\n";
        else toWrite += "printf(\""+printString+"\", "+ varabiliafineString+ ");\n";
        return toWrite;
    }
    @Override
    public Object visit(IFEXPR efexpr) {
        String toWrite = "(";
        if (efexpr.getCond() != null)
            toWrite += "(" + efexpr.getCond().accept(this) + ")";
        if (efexpr.getSevera() != null)
            toWrite += " ? (" + efexpr.getSevera().accept(this) + ")";
        if (efexpr.getSefalsa() != null)
            toWrite += " : (" + efexpr.getSefalsa().accept(this) + ")";
        return toWrite + ")";
    }
    @Override
    public Object visit(AssignStat assignstatOP) {
        String toWrite = "";
        if (assignstatOP.getIdList() != null && assignstatOP.getIdList().size() >  0) {

            int indice = 0;
            int indexPrecedente = statCount;
            for (Expr e : assignstatOP.getExprList()) {
                if (e instanceof CallProc) {
                    indice++;
                    statCount++;
                    //mi creo la struttura per asegnare il callproc
                    toWrite += "struct str_" + ((CallProc) e).getId().accept(this) + " strTmp_"+((CallProc) e).getId().accept(this) +"_"+indice+"_"+statCount+" = "+ e.accept(this)+";\n";
                }
            }
            indice = 0;
            statCount = indexPrecedente;
            for (int i = 0, j = 0; i < assignstatOP.getIdList().size(); i++,j++) {
                if (assignstatOP.getExprList().get(j) instanceof CallProc) {
                    indice++;
                    statCount++;
                    try {
                        int dim = roottable.containsEntry(((CallProc) assignstatOP.getExprList().get(j)).getId().getVal()).outputParams.size();
                        for (int k = 0; k < dim; k++) {
                            toWrite += "" + assignstatOP.getIdList().get(i).accept(this) + " = strTmp_" + ((CallProc) assignstatOP.getExprList().get(j)).getId().getVal()+
                                        "_"+indice+"_"+statCount+".par"+(k+1)+";\n";
                            if (k < dim -1) i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if ( assignstatOP.getIdList().get(i).types.get(0) == SymbolTable.ValueType.String) {
                        toWrite += "strcpy("+assignstatOP.getIdList().get(i).accept(this)+", "+assignstatOP.getExprList().get(j).accept(this)+");\n";
                    }
                    else toWrite += " " + assignstatOP.getIdList().get(i).accept(this)+ " = " + assignstatOP.getExprList().get(j).accept(this)+ ";\n";
                }
            }

        }
        return toWrite;
    }

    @Override
    public Object visit(CallProc callprocOP) {
        String toWrite = "";
        toWrite += callprocOP.getId().accept(this)+ "(";
        if (callprocOP.getExprList() != null && callprocOP.getExprList().size() > 0 ) {
            for (int i = 0; i < callprocOP.getExprList().size(); i++) {
                if ( i < callprocOP.getExprList().size() -1 ) {
                    if (callprocOP.getExprList().get(i) instanceof  CallProc) {
                        String name = (String) callprocOP.getExprList().get(i).accept(this);
                        SymbolTabRow stR = null;
                        try {
                            stR = roottable.containsEntry(((CallProc) callprocOP.getExprList().get(i)).getId().getVal());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                        for (int j = 0; j < stR.outputParams.size();j++ ) {
                            toWrite += name + ".par"+ (j+1) + ",";
                        }
                    } else toWrite += callprocOP.getExprList().get(i).accept(this)+ ", ";
                } else {
                    if (callprocOP.getExprList().get(i) instanceof  CallProc) {
                        String name = (String) callprocOP.getExprList().get(i).accept(this);
                        SymbolTabRow stR = null;
                        try {
                            stR = roottable.containsEntry(((CallProc) callprocOP.getExprList().get(i)).getId().getVal());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                        for (int j = 0; j < stR.outputParams.size();j++ ) {
                            if (j < stR.outputParams.size() - 1) {
                                toWrite += name + ".par"+ (j+1) + ",";
                            } else toWrite += name + ".par" + (j+1);
                        }
                    } else toWrite += callprocOP.getExprList().get(i).accept(this);
                }
            }
        }
        toWrite += ")";
        return toWrite;
    }

    @Override
    public Object visit(WhileStat whilestatOP) {
        String toWrite = "";
        String preCond = "";

        if (whilestatOP.getStatList1() != null && whilestatOP.getStatList1().size() > 0) {
            for (Stat s : whilestatOP.getStatList1()) {
                preCond += s.accept(this)+ "\n";
            }
            toWrite += preCond;
        }

        toWrite += "while ("+
                ((whilestatOP.getEspressione() instanceof CallProc) ? whilestatOP.getEspressione().accept(this)+".par1" : whilestatOP.getEspressione().accept(this))
                + ") {\n";

        if (whilestatOP.getStatList2() != null && whilestatOP.getStatList2().size() > 0) {
            for (Stat s : whilestatOP.getStatList2()) {
                if (s instanceof CallProc) toWrite += s.accept(this)+ ";\n";
                else toWrite += s.accept(this)+ "\n";
            }
        }
        if (whilestatOP.getStatList1() != null && whilestatOP.getStatList1().size() > 0) {
                toWrite += preCond;
        }
        toWrite += "} \n";


        return toWrite;
    }

    @Override
    public Object visit(Else elseOP) {
        String toWrite = "";
        if (elseOP.getStatList() != null && elseOP.getStatList().size() > 0) {
            for (Stat s : elseOP.getStatList()) {
                if (s instanceof CallProc) toWrite += "" + s.accept(this)+ ";\n";
                else toWrite += s.accept(this);
            }
        }
        return toWrite;
    }

    @Override
    public Object visit(Elif elifOP) {
        String toWrite = "";
        toWrite += "else if (" +
                ((elifOP.getEspressione() instanceof CallProc) ? elifOP.getEspressione().accept(this) + ".par1" : elifOP.getEspressione().accept(this))
                + ") {\n";
        if (elifOP.getListaStat() != null && elifOP.getListaStat().size() > 0) {
            for (Stat s : elifOP.getListaStat()) {
                if (s instanceof CallProc) toWrite += s.accept(this) + ";\n";
                else toWrite += s.accept(this);
            }
        }
        toWrite += "}\n";

        return toWrite;
    }

    @Override
    public Object visit(Int_const intconstOP) {
        String toWrite = "";
        //List<String> l = new ArrayList<String>();
        toWrite += intconstOP.getVal();
        //l.add(toWrite);
        return toWrite;
    }

    @Override
    public Object visit(Float_const floatconstOP) {
        String toWrite = "";
        toWrite += floatconstOP.getVal();
        return toWrite;
    }

    @Override
    public Object visit(String_const stringconstOP) {
        String toWrite = "";
        toWrite += "\""+stringconstOP.getVal() + "\"";  // "ciao"
        return toWrite;
    }

    @Override
    public Object visit(Identifier id) {
        String toWrite = "";
        toWrite += id.getVal();
        return toWrite;
    }

    @Override
    public Object visit(PLUS plusOP) {
        String toWrite = "";
        if (plusOP.getLeft() != null && plusOP.getRight() != null) {
            String toWriteLeft = (String) plusOP.getLeft().accept(this);
            String toWriteRight = (String) plusOP.getRight().accept(this);
            if (plusOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (plusOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (plusOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    plusOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "concat("+toWriteLeft+", "+ toWriteRight+")";
            } else toWrite += toWriteLeft + " + " + toWriteRight;
        }

        return toWrite;
    }

    @Override
    public Object visit(MINUS minusOP) {
        String toWrite = "";
        if (minusOP.getLeft() != null && minusOP.getRight() != null) {
            String toWriteLeft = (String) minusOP.getLeft().accept(this);
            String toWriteRight = (String) minusOP.getRight().accept(this);
            if (minusOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (minusOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            toWrite += toWriteLeft + " - " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(TIMES timesOP) {
        String toWrite = "";
        if (timesOP.getLeft() != null && timesOP.getRight() != null) {
            String toWriteLeft = (String) timesOP.getLeft().accept(this);
            String toWriteRight = (String) timesOP.getRight().accept(this);
            if (timesOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (timesOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            toWrite += toWriteLeft + " * " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(DIV divOP) {
        String toWrite = "";
        if (divOP.getLeft() != null && divOP.getRight() != null) {
            String toWriteLeft = (String) divOP.getLeft().accept(this);
            String toWriteRight = (String) divOP.getRight().accept(this);
            if (divOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (divOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            toWrite += toWriteLeft + " / " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(AND andOP) {
        String toWrite = "";
        if (andOP.getLeft() != null && andOP.getRight() != null) {
            String toWriteLeft = (String) andOP.getLeft().accept(this);
            String toWriteRight = (String) andOP.getRight().accept(this);
            if (andOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (andOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            toWrite += toWriteLeft + " && " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(OR orOP) {
        String toWrite = "";
        if (orOP.getLeft() != null && orOP.getRight() != null) {
            String toWriteLeft = (String) orOP.getLeft().accept(this);
            String toWriteRight = (String) orOP.getRight().accept(this);
            if (orOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (orOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            toWrite += toWriteLeft + " || " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(GT gtOP) {
        String toWrite = "";
        if (gtOP.getLeft() != null && gtOP.getRight() != null) {
            String toWriteLeft = (String) gtOP.getLeft().accept(this);
            String toWriteRight = (String) gtOP.getRight().accept(this);
            if (gtOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (gtOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (gtOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    gtOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") > 0";
            } else toWrite += toWriteLeft + " > " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(GE geOP) {
        String toWrite = "";
        if (geOP.getLeft() != null && geOP.getRight() != null) {
            String toWriteLeft = (String) geOP.getLeft().accept(this);
            String toWriteRight = (String) geOP.getRight().accept(this);
            if (geOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (geOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (geOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    geOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") >= 0";
            } else toWrite += toWriteLeft + " >= " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(LT ltOP) {
        String toWrite = "";
        if (ltOP.getLeft() != null && ltOP.getRight() != null) {
            String toWriteLeft = (String) ltOP.getLeft().accept(this);
            String toWriteRight = (String) ltOP.getRight().accept(this);
            if (ltOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (ltOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (ltOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    ltOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") < 0";
            } else toWrite += toWriteLeft + " < " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(LE leOP) {
        String toWrite = "";
        if (leOP.getLeft() != null && leOP.getRight() != null) {
            String toWriteLeft = (String) leOP.getLeft().accept(this);
            String toWriteRight = (String) leOP.getRight().accept(this);
            if (leOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (leOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (leOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    leOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") <= 0";
            } else toWrite += toWriteLeft + " <= " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(EQ eqOP) {
        String toWrite = "";
        if (eqOP.getLeft() != null && eqOP.getRight() != null) {
            String toWriteLeft = (String) eqOP.getLeft().accept(this);
            String toWriteRight = (String) eqOP.getRight().accept(this);
            if (eqOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (eqOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (eqOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    eqOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") == 0";
            } else toWrite += toWriteLeft + " == " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(NE neOP) {
        String toWrite = "";
        if (neOP.getLeft() != null && neOP.getRight() != null) {
            String toWriteLeft = (String) neOP.getLeft().accept(this);
            String toWriteRight = (String) neOP.getRight().accept(this);
            if (neOP.getLeft() instanceof CallProc) {
                toWriteLeft += ".par1";
            }
            if (neOP.getRight() instanceof CallProc) {
                toWriteRight += ".par1";
            }
            if (neOP.getLeft().types.get(0) == SymbolTable.ValueType.String &&
                    neOP.getRight().types.get(0) == SymbolTable.ValueType.String) {
                toWrite += "strcmp("+toWriteLeft+", "+ toWriteRight+") != 0";
            } else toWrite += toWriteLeft + " != " + toWriteRight;
        }
        return toWrite;
    }

    @Override
    public Object visit(NOT notOP) {
        String toWrite = "";
        String toWriteLeft = (String) notOP.getChild().accept(this);
        if (notOP.getChild() instanceof CallProc) {
            toWriteLeft += ".par1";
        }
        toWrite += "!("+toWriteLeft+")";

        return toWrite;
    }

    @Override
    public Object visit(UMINUS uminusOP) {
        String toWrite = "";
        String toWriteLeft = (String) uminusOP.getChild().accept(this);
        if (uminusOP.getChild() instanceof CallProc) {
            toWriteLeft += ".par1";
        }
        toWrite += "-("+toWriteLeft+")";

        return toWrite;
    }

    @Override
    public Object visit(False falseOP) {
        return falseOP.getValue();
    }

    @Override
    public Object visit(True trueOP) {
        return trueOP.getValue();
    }

    @Override
    public Object visit(Null nullNode) {
        return "NULL";
    }



    public String getCTypeFromTypeNodeValue(String fromType){
        switch (fromType){
            case "int":
                return  "int ";

            case "float":
                return "float ";

            case "string":
                return "char ";

            case "bool":
                return "bool ";

        }
        return "";
    }

    public String getCScanPrintSymbolByToySemanticType(SymbolTable.ValueType fromType){
        switch (fromType){
            case String:
                return  "%s";

            case Integer: case Boolean:
                return "%d";

            case Float:
                return "%f";

        }
        return "";
    }
}
