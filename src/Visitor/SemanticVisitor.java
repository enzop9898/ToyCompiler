package Visitor;

import Node.*;

import java.util.*;

public class SemanticVisitor implements Visitor<Object> {

    public Stack<SymbolTable> stackOfTable = new Stack<>();
    private ArrayList<SymbolTable.ValueType> actualScopeRetTypes = null;

    @Override
    public Object visit(Program progOP) {

        stackOfTable.push(progOP.initSymbolTable()); //creo tabella e la aggiungo allo stack
        if (progOP.getVardeclist() != null && progOP.getVardeclist().size() > 0)
            for(VarDecl d: progOP.getVardeclist()) { //vado a visitare i vardecl
                d.accept(this);
            }
        // controlli sul main
        boolean mainControllato = false;


        for(Proc p: progOP.getProclist()) {
            if (p.getID().getVal().equalsIgnoreCase("main")){
                //progOP.getProclist().remove(p);
                //progOP.getProclist().add(p);
                mainControllato = true;
                break;
            }
        }
        if(!mainControllato) {
            System.err.println("Semantic Error: Main not declared");  // main non esiste
            System.exit(0);
        }

        for(Proc p: progOP.getProclist()) {
            p.accept(this); // visito le procedure
        }

        System.out.println(stackOfTable.firstElement().toString());
        stackOfTable.pop(); //rimuovo dal type env

        return null;
    }

    @Override
    public Object visit(VarDecl vardeclOP) {
        vardeclOP.getTipo().accept(this);

        if (vardeclOP.getIdlista() != null && vardeclOP.getIdlista().size() > 0) {

            SymbolTable topTable = stackOfTable.peek(); //prendo la symtab sul top dello stack (corrente)

            for (IdInit id : vardeclOP.getIdlista()) {
                try {
                    topTable.creaEntryVar(id.getId().getVal(), vardeclOP.getTipo().getNomeNode()); // aggiungo la entry della dichiarazione nella tab con id e tipo
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                id.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visit(Proc procOP) {
        ArrayList<SymbolTable.ValueType> in, out; //ce li popoliamo per aggiungerli alla entry declaration in tabsym
        in = new ArrayList<>();
        out = new ArrayList<>();

        try {

            //CONTROLLO PARAMETRI
            if (procOP.getPardec() != null && procOP.getPardec().size() > 0) { //se ha parametri
                if (procOP.getID().getVal().equals("main")) {
                    System.err.println("Semantic Error: main must not have parameters"); // controllo che se la proc è main non deve avere parametri input
                    System.exit(0);
                }

                for (ParDecl pD : procOP.getPardec()) {
                    SymbolTable.ValueType vtype = SymbolTable.StringToType(pD.getTipo().getNomeNode()); //mi prendo tutti i parametri e mi popolo l'array in
                    for (int i=0; i< pD.getListaId().size(); i++) in.add(vtype);                        // per quanti sono gli identificatori di quel tipo
                }// int int int -> float
            }
            //END CONTROLLO PARAMETRI

            //CONTROLLO TIPI DI RITORNO
            if (procOP.getResdec() != null && procOP.getResdec().size() > 0)
                for (Type t : procOP.getResdec()) {     //vedo tutti i tipi di ritorno
                    SymbolTable.ValueType vtype;
                    if (!t.getNomeNode().equals("void")) {
                        vtype = SymbolTable.StringToType(t.getNomeNode());  //se non è void lo aggiungo all'array out
                        //System.out.println("Ciao sono il tipo"+t.getNomeNode());
                        out.add(vtype);
                    } else {
                        //System.out.println("Ciao sono il tipo"+t.getNomeNode());
                        out.add(SymbolTable.ValueType.Void);
                    }
                }
            if (out.size() > 1 && out.contains(SymbolTable.ValueType.Void)) {                           //qui dentro vedo se ci sono più tipi di rit e c'è void
                System.err.println("Semantic error: void keyword is now allowed when multiple return types are declared");
                System.exit(0);
            } else stackOfTable.firstElement().creaEntryFunction(procOP.getID().getVal(), in, out);     //creo la entry della proc nell tab dei simboli al top
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        // END CONTROLLO TIPI DI RITORNO

        SymbolTable symT = procOP.initSymbolTable(); //creo la tab
        symT.setPadre(stackOfTable.firstElement()); //collego al padre (program)
        stackOfTable.push(symT); //pusho nello stack

        if (procOP.getPardec() != null && procOP.getPardec().size() > 0) {
            for(ParDecl pr: procOP.getPardec()) {
                pr.accept(this); //visito i parametri
            }
        }

        ////// body
        if (procOP.getVardec() != null && procOP.getVardec().size() > 0) {
            for (VarDecl vd: procOP.getVardec()) {
                vd.accept(this);
            }
        }

        if (procOP.getStatementlist() != null && procOP.getStatementlist().size() > 0) {
            for (Stat t: procOP.getStatementlist()) {
                t.accept(this);
            }
        }

        if (procOP.getRetexpr() != null && procOP.getRetexpr().getListaExpr().size() > 0){
            procOP.getRetexpr().accept(this); // qui mi prendo i tipi delle expr che sto restituendo
        } else {
            actualScopeRetTypes = new ArrayList<>(); //tipi delle espressioni di ritorno -> something
        }
        ////// end body


            if (procOP.getID().getVal().equalsIgnoreCase("main")) {
                try {
                    SymbolTabRow rigaMain = stackOfTable.peek().containsEntry("main");
                    if (rigaMain.outputParams.get(0) != SymbolTable.ValueType.Void)
                        throw new Exception("Semantic error: main function must be declared void"); //il main deve essere void
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
            if (actualScopeRetTypes.size() == 0) { //non restituisce nulla
                if (out.size() >= 1 && out.get(0) != SymbolTable.ValueType.Void) {
                    System.err.println("Missing return in " + procOP.getID().getVal()); //manca il return perchè dovrei restituire qualcosa ma non lo faccio
                    System.exit(0);
                }
            } else { //sto restituendo qualcosa
                if (out.size() > 0 && out.get(0) == SymbolTable.ValueType.Void) {
                    System.err.println("Semantic error: return values not allowed in void declared function " + procOP.getID().getVal());
                    System.exit(0);
                }
                if (actualScopeRetTypes.size() != out.size()) {
                    System.err.println("Semantic error: return values list are not in the same of return types declared for function " + procOP.getID().getVal());
                    System.exit(0);
                }
                for (int i=0; i < actualScopeRetTypes.size(); i++) {
                    if (!controllaCompatibilitàTipi(out.get(i), actualScopeRetTypes.get(i))) {
                        System.err.println("Semantic error: return attributes in function " + procOP.getID().getVal() + " does not match with types declared in function signature");
                        System.exit(0);
                    }
                }
            }
            actualScopeRetTypes = null;

        System.out.println(stackOfTable.peek().toString());
        stackOfTable.pop();

        return null;
    }


    private void error(String msg) {
        System.err.println(msg);
        System.exit(0);
    }

    @Override
    public Object visit(IfStat ifstatOP) {
        ifstatOP.getEspressione().accept(this);
        if (ifstatOP.getEspressione().types.size() != 1) {
            error("Semantic error: multiple condition return type found in if condition");
        } else if (ifstatOP.getEspressione().types.get(0) != SymbolTable.ValueType.Boolean) {
            error("Semantic error: condition type not allowed in IF statement");
                System.err.println();
                System.exit(0);
            }

            if (ifstatOP.getListaStat() != null && ifstatOP.getListaStat().size() > 0) {
                for (Stat st : ifstatOP.getListaStat()) {
                    st.accept(this);
            }
        }

        if (ifstatOP.getListaElif() != null && ifstatOP.getListaElif().size() >0) {
            for (Elif el : ifstatOP.getListaElif()) {
                el.accept(this);
            }
        }

        if (ifstatOP.getEls() != null) ifstatOP.getEls().accept(this);

        return null;
    }

    @Override
    public Object visit(Type typeOP) {
        return null;
    }

    @Override
    public Object visit(IdInit idinitOP) {
        idinitOP.getId().accept(this); //vado in identifier e mi setto il tipo dell'id che è un expr (lo prendo dalla entry nella tab dei simboli)
        if (idinitOP.getEspressione() != null) {
            idinitOP.getEspressione().accept(this); //vado nell'expr, così setto il tipo in types dell'expr
            if (idinitOP.getEspressione().types.size() > 1) {
                System.err.println("Semantic error: multiple value to initialize a variable"); //l'expr non deve avere più tipo per essere un id init
                System.exit(0);
            }
            if (!controllaCompatibilitàTipi(idinitOP.getId().types.get(0),idinitOP.getEspressione().types.get(0))) {
                System.err.println("Semantic error: wrong initialization for variable " + idinitOP.getId().getVal()); //i tipi devono essere compatibili
                System.exit(0);
            }
        }
        return null;
    }

    @Override
    public Object visit(ReturnExprs returnexprOP) {

        actualScopeRetTypes = new ArrayList<>();
        for ( Expr e: returnexprOP.getListaExpr()) { //result.types=int
            e.accept(this);
            for (SymbolTable.ValueType vt : e.types) {
                if(vt != SymbolTable.ValueType.Null && vt != SymbolTable.ValueType.Void)
                    actualScopeRetTypes.add(vt);
            }

        }
        return null;
    }

    @Override
    public Object visit(ParDecl pardeclOP) {

        if (pardeclOP.getListaId() != null && pardeclOP.getListaId().size() > 0) {
            for (Identifier s: pardeclOP.getListaId()) {
                try {
                    stackOfTable.peek().creaEntryVar(s.getVal(),pardeclOP.getTipo().getNomeNode());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }

        return null;
    }

    @Override
    public Object visit(ReadlnStat readlnOP) {

            for (Identifier p : readlnOP.getIdLista())
                p.accept(this);

        return null;
    }

    @Override
    public Object visit(WriteStat writestatOP) {
        for (Expr p : writestatOP.getExprLista())
            p.accept(this);
        return null;
    }

    @Override
    public Object visit(AssignStat assignstatOP) {
        ArrayList<SymbolTable.ValueType> tipiTemporanei = new ArrayList<>();
        for (Identifier id : assignstatOP.getIdList()) id.accept(this); //visitiamo gli identifier settando così il suo tipo
        for (Expr ex : assignstatOP.getExprList()) { //visitiamo tutti gli expr di dx settando i tipi
            ex.accept(this);
            tipiTemporanei.addAll(ex.types); // ci serve per confronfare i tipi
        }

        if (tipiTemporanei.size() != assignstatOP.getIdList().size()) {//confronto se il numero di parametri nella parte destra e quelli nella parte sinistra coincdiono nel numero
            System.err.println("Semantic Error: ID does not match whith assign values in assign stat");
            System.exit(0);
        }

        for (int i=0; i < tipiTemporanei.size(); i++) {// fa il match tra i tipi degli id a sinistra e i tipi delle expr a destra
            //System.out.println("TIPO DELL'ID SX: "+ assignstatOP.getIdList().get(i).types.get(0)+" TIPO DELL'EXPR DX: "+tipiTemporanei.get(i));
            if (!controllaCompatibilitàTipi(assignstatOP.getIdList().get(i).types.get(0), tipiTemporanei.get(i))) {
                System.err.println("Semantic Error: ID type does not match" +" with Assign Value type in assign stat for id: " + assignstatOP.getIdList().get(i).getVal());
            }
        }


        return null;
    }

    @Override
    public Object visit(CallProc callprocOP) {
        SymbolTable st = stackOfTable.peek();
        SymbolTabRow stR = null;
        try {
            if (st.containsKey(callprocOP.getId().getVal())) {
                try {
                    stR = st.containsEntry(callprocOP.getId().getVal());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // controllo gli argomenti della proc
        if (callprocOP.getExprList() != null && callprocOP.getExprList().size() > 0) {
            ArrayList<SymbolTable.ValueType> tempType = new ArrayList<>();
            for (Expr p : callprocOP.getExprList()) {
                p.accept(this);

                if (p instanceof CallProc) { //se un arg è un CallProc allora mi prendo i tipi out della Proc... prima ovviamente controllo che ci sia
                    try {
                        SymbolTabRow stR_CallP = st.containsEntry(((CallProc) p).getId().getVal()); //mi prendo la entry della callproc, se non è presente in gamma il metodo containsEntry lancerà exp
                        for (SymbolTable.ValueType vt: stR_CallP.outputParams) {
                            tempType.add(vt);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else tempType.add(p.types.get(0));
            }

            if (stR.inputParams.size() != tempType.size()) { //controllo se in num inputParams == quelli effettivi
                System.err.println("Semantic error: number of params doesn't match in function " + callprocOP.getId().getVal() + " call");
                System.exit(0);
            } else {
                for (int i=0; i < stR.inputParams.size(); i++) {
                    if (!controllaCompatibilitàTipi(stR.inputParams.get(i), tempType.get(i))){         // Per ogni expr interna devo avere il tipo salvato... possibili refactoring sui Nodi Expr
                        System.err.println("Semantic error: type mismatch in function " + callprocOP.getId().getVal() + " call");
                        System.exit(0);
                    }
                }
            }
        } else { //non ha parametri
            if (stR.inputParams != null && stR.inputParams.size() >0) { //mancano i parametri
                System.err.println("Semantic error: number of params doesn't match in function " + callprocOP.getId().getVal() + " call");
                System.exit(0);
            }
        }

        callprocOP.setTypes(stR.outputParams);

        return null;
    }

    @Override
    public Object visit(WhileStat whilestatOP) {
        whilestatOP.getEspressione().accept(this);
        if (whilestatOP.getEspressione().types.size() > 1) {
            System.err.println("Semantic error: multiple conditions found in while"); //una condizione non può essere un expr che restituisce più tipi
            System.exit(0);
        } else if (whilestatOP.getEspressione().types.get(0) != SymbolTable.ValueType.Boolean) {
            System.err.println("Semantic error: condition type not allowed in while. Only boolean allowed"); //l'expre della condizione deve avere tipo boolean
            System.exit(0);
        }

        if (whilestatOP.getStatList1() != null && whilestatOP.getStatList1().size() >0)
            for (Stat st : whilestatOP.getStatList1()) {
                st.accept(this);
            }

        if (whilestatOP.getStatList2() != null && whilestatOP.getStatList2().size() >0)
            for (Stat st : whilestatOP.getStatList2()) {
                st.accept(this);
            }

        return null;
    }

    @Override
    public Object visit(Else elseOP) {
        if (elseOP.getStatList() != null && elseOP.getStatList().size() > 0) {
            for (Stat p : elseOP.getStatList()) {
                p.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visit(Elif elifOP) {
        elifOP.getEspressione().accept(this);
        if (elifOP.getEspressione().types.size() > 1) {
            System.err.println("Semantic error: multiple condition return typ found in elif condition");
            System.exit(0);
        } else if ( elifOP.getEspressione().types.get(0) != SymbolTable.ValueType.Boolean){
            System.err.println("Semantic error: condition type not allowed in while");
            System.exit(0);
        }

        for (Stat p : elifOP.getListaStat()) {
                p.accept(this);
            }

        return null;
    }

    @Override
    public Object visit(Int_const intconstOP) {
        intconstOP.setType("int");
        return null;
    }

    @Override
    public Object visit(Float_const floatconstOP) {
        floatconstOP.setType("float");
        return null;
    }

    @Override
    public Object visit(String_const stringconstOP) {
        stringconstOP.setType("string");
        return null;
    }

    @Override
    public Object visit(Identifier id) {
        SymbolTable st = stackOfTable.peek();
        try {
            if (st.containsKey(id.getVal())) {
                SymbolTabRow str = null;
                try {
                    str = st.containsEntry(id.getVal());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                id.setType(str.vtype);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    @Override
    public Object visit(IFEXPR efexpr) {
    if(efexpr.getCond()!=null)
            efexpr.getCond().accept(this);
        if(efexpr.getCond().types.get(0)!=SymbolTable.ValueType.Boolean){
            System.err.println("The expression must be boolean");
        }
        if(efexpr.getSevera()!=null)
            efexpr.getSevera().accept(this);
        if(efexpr.getSefalsa()!=null)
            efexpr.getSefalsa().accept(this);
        if(efexpr.getSevera().types.get(0)!= efexpr.getSefalsa().types.get(0)){
            System.err.println("Error: the two expression in ifexpr must be the same type");
        }
        efexpr.setType(efexpr.getSefalsa().types.get(0));
        return null;
    }
    @Override
    public Object visit(PLUS plusOP) {
        if (plusOP.getLeft() != null && plusOP.getRight() != null) {
            plusOP.getLeft().accept(this);
            plusOP.getRight().accept(this);
            if (plusOP.getLeft() instanceof CallProc || plusOP.getRight() instanceof CallProc) {
                if (plusOP.getLeft().types.size() > 1 || plusOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            if (plusOP.getRight().types.get(0) == SymbolTable.ValueType.String && plusOP.getLeft().types.get(0) != SymbolTable.ValueType.String ||
                    plusOP.getRight().types.get(0) != SymbolTable.ValueType.String && plusOP.getLeft().types.get(0) == SymbolTable.ValueType.String) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            }
            SymbolTable.ValueType tipoSomma = getType_operations(plusOP.getLeft().types.get(0),plusOP.getRight().types.get(0));
            if (tipoSomma == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else plusOP.setType(tipoSomma);

        }
        return null;
    }

    @Override
    public Object visit(MINUS minusOP) {
        if (minusOP.getLeft() != null && minusOP.getRight() != null) {
            minusOP.getLeft().accept(this);
            minusOP.getRight().accept(this);
            if (minusOP.getLeft() instanceof CallProc || minusOP.getRight() instanceof CallProc) {
                if (minusOP.getLeft().types.size() > 1 || minusOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            if (minusOP.getLeft().types.get(0) == SymbolTable.ValueType.String || minusOP.getRight().types.get(0) == SymbolTable.ValueType.String){
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            }

            SymbolTable.ValueType tipoDiff = getType_operations(minusOP.getLeft().types.get(0),minusOP.getRight().types.get(0));
            if (tipoDiff == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else minusOP.setType(tipoDiff);

        }
        return null;
    }

    @Override
    public Object visit(TIMES timesOP) {
        if (timesOP.getLeft() != null && timesOP.getRight() != null) {
            timesOP.getLeft().accept(this);
            timesOP.getRight().accept(this);
            if (timesOP.getLeft() instanceof CallProc || timesOP.getRight() instanceof CallProc) {
                if (timesOP.getLeft().types.size() > 1 || timesOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            if (timesOP.getLeft().types.get(0) == SymbolTable.ValueType.String || timesOP.getRight().types.get(0) == SymbolTable.ValueType.String){
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            }

            SymbolTable.ValueType tipoMull = getType_operations(timesOP.getLeft().types.get(0),timesOP.getRight().types.get(0));
            if (tipoMull == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else timesOP.setType(tipoMull);

        }
        return null;
    }

    @Override
    public Object visit(DIV divOP) {
        if (divOP.getLeft() != null && divOP.getRight() != null) {
            divOP.getLeft().accept(this);
            divOP.getRight().accept(this);
            if (divOP.getLeft() instanceof CallProc || divOP.getRight() instanceof CallProc) {
                if (divOP.getLeft().types.size() > 1 || divOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            if (divOP.getLeft().types.get(0) == SymbolTable.ValueType.String || divOP.getRight().types.get(0) == SymbolTable.ValueType.String){
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            }

            SymbolTable.ValueType tipoDiv = getType_operations(divOP.getLeft().types.get(0),divOP.getRight().types.get(0));
            if (tipoDiv == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else divOP.setType(tipoDiv);

        }
        return null;
    }

    @Override
    public Object visit(AND andOP) {
        if (andOP.getLeft() != null && andOP.getRight() != null){
            andOP.getLeft().accept(this);
            andOP.getRight().accept(this);
            if (andOP.getLeft() instanceof CallProc || andOP.getRight() instanceof CallProc) {
                if (andOP.getLeft().types.size() > 1 || andOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }

            SymbolTable.ValueType tipoAnd = getType_andor(andOP.getLeft().types.get(0), andOP.getRight().types.get(0));
            if (tipoAnd == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else andOP.setType(tipoAnd);

        }
        return null;
    }

    @Override
    public Object visit(OR orOP) {
        if (orOP.getLeft() != null && orOP.getRight() != null){
            orOP.getLeft().accept(this);
            orOP.getRight().accept(this);
            if (orOP.getLeft() instanceof CallProc || orOP.getRight() instanceof CallProc) {
                if (orOP.getLeft().types.size() > 1 || orOP.getRight().types.size() > 1){
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }

            SymbolTable.ValueType tipoAnd = getType_andor(orOP.getLeft().types.get(0), orOP.getRight().types.get(0));
            if (tipoAnd == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else orOP.setType(tipoAnd);
        }
        return null;
    }

    @Override
    public Object visit(GT gtOP) {
        if (gtOP.getLeft() != null && gtOP.getRight() != null) {
            gtOP.getLeft().accept(this);
            gtOP.getRight().accept(this);
            if (gtOP.getLeft() instanceof CallProc || gtOP.getRight() instanceof CallProc) {
                if (gtOP.getLeft().types.size() > 1 || gtOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }

            SymbolTable.ValueType tipoGt = getType_boolean(gtOP.getLeft().types.get(0), gtOP.getRight().types.get(0));
            if (tipoGt == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } gtOP.setType(tipoGt);
        }
        return null;
    }

    @Override
    public Object visit(GE geOP) {
        if (geOP.getLeft() != null && geOP.getRight() != null) {
            geOP.getLeft().accept(this);
            geOP.getRight().accept(this);
            if (geOP.getLeft() instanceof CallProc || geOP.getRight() instanceof CallProc) {
                if (geOP.getLeft().types.size() > 1 || geOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            SymbolTable.ValueType tipoGe = getType_boolean(geOP.getLeft().types.get(0), geOP.getRight().types.get(0));
            if (tipoGe == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else geOP.setType(tipoGe);
        }
        return null;
    }

    @Override
    public Object visit(LT ltOP) {
        if (ltOP.getLeft() != null && ltOP.getRight() != null) {
            ltOP.getLeft().accept(this);
            ltOP.getRight().accept(this);
            if (ltOP.getLeft() instanceof CallProc || ltOP.getRight() instanceof CallProc) {
                if (ltOP.getLeft().types.size() > 1 || ltOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            SymbolTable.ValueType tipoLt = getType_boolean(ltOP.getLeft().types.get(0), ltOP.getRight().types.get(0));
            if (tipoLt == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else ltOP.setType(tipoLt);
        }
        return null;
    }

    @Override
    public Object visit(LE leOP) {
        if (leOP.getLeft() != null && leOP.getRight() != null) {
            leOP.getLeft().accept(this);
            leOP.getRight().accept(this);
            if (leOP.getLeft() instanceof CallProc || leOP.getRight() instanceof CallProc) {
                if (leOP.getLeft().types.size() > 1 || leOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            SymbolTable.ValueType tipoLe = getType_boolean(leOP.getLeft().types.get(0), leOP.getRight().types.get(0));
            if (tipoLe == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else leOP.setType(tipoLe);
        }
        return null;
    }

    @Override
    public Object visit(EQ eqOP) {
        if (eqOP.getLeft() != null && eqOP.getRight() != null) {
            eqOP.getLeft().accept(this);
            eqOP.getRight().accept(this);
            if (eqOP.getLeft() instanceof CallProc || eqOP.getRight() instanceof CallProc) {
                if (eqOP.getLeft().types.size() > 1 || eqOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            SymbolTable.ValueType tipoEq = getType_boolean(eqOP.getLeft().types.get(0), eqOP.getRight().types.get(0));
            if (tipoEq == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else eqOP.setType(tipoEq);
        }
        return null;
    }

    @Override
    public Object visit(NE neOP) {
        if (neOP.getLeft() != null && neOP.getRight() != null) {
            neOP.getLeft().accept(this);
            neOP.getRight().accept(this);
            if (neOP.getLeft() instanceof CallProc || neOP.getRight() instanceof CallProc) {
                if (neOP.getLeft().types.size() > 1 || neOP.getRight().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter or second parameter, returns 2 or more values in a binary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            SymbolTable.ValueType tipoNe = getType_boolean(neOP.getLeft().types.get(0), neOP.getRight().types.get(0));
            if (tipoNe == null) {
                System.err.println("Semantic error: type not compatible with operation");
                System.exit(0);
            } else neOP.setType(tipoNe);
        }
        return null;
    }

    @Override
    public Object visit(NOT notOP) {
        if (notOP.getChild() != null) {
                notOP.getChild().accept(this);
                if (notOP.getChild() instanceof CallProc) {
                    if (notOP.getChild().types.size() > 1) {
                        System.err.println("Semantic error: function call as first parameter returns 2 or more values in a Unary operation. This is not allowed.");
                        System.exit(0);
                    }
                }
                if (notOP.getChild().types.get(0) != SymbolTable.ValueType.Boolean) {
                    System.err.println("Semantic error: not operation conversion error, type mismatch");
                    System.exit(0);
                } else notOP.setType(notOP.getChild().types.get(0));
        }
        return null;
    }

    @Override
    public Object visit(UMINUS uminusOP) {
        if (uminusOP.getChild() != null) {
            uminusOP.getChild().accept(this);
            if (uminusOP.getChild() instanceof CallProc) {
                if (uminusOP.getChild().types.size() > 1) {
                    System.err.println("Semantic error: function call as first parameter returns 2 or more values in a Unary operation. This is not allowed.");
                    System.exit(0);
                }
            }
            if (uminusOP.getChild().types.get(0) != SymbolTable.ValueType.Integer && uminusOP.getChild().types.get(0) != SymbolTable.ValueType.Float) {
                System.err.println("Semantic error: not operation conversion error, type mismatch");
                System.exit(0);
            } else uminusOP.setType(uminusOP.getChild().types.get(0));
        }
        return null;
    }

    @Override
    public Object visit(False falseOP) {
        falseOP.setType("bool");
        return null;
    }

    @Override
    public Object visit(True trueOP) {
        trueOP.setType("bool");
        return null;
    }

    @Override
    public Object visit(Null nullNode) {
        nullNode.setType("bool");
        return null;
    }




    private boolean controllaCompatibilitàTipi(SymbolTable.ValueType dichiarata, SymbolTable.ValueType effettiva) {
        if (effettiva == SymbolTable.ValueType.Null)
            return true;
        if (dichiarata == SymbolTable.ValueType.Integer && effettiva == SymbolTable.ValueType.Integer)
            return true;
        if (dichiarata == SymbolTable.ValueType.Float && effettiva == SymbolTable.ValueType.Float)
            return true;
        if (dichiarata == SymbolTable.ValueType.Float && effettiva == SymbolTable.ValueType.Integer)
            return true;
        if (dichiarata == SymbolTable.ValueType.Integer && effettiva == SymbolTable.ValueType.Float)
            return true;
        if (dichiarata == SymbolTable.ValueType.Boolean && effettiva == SymbolTable.ValueType.Boolean)
            return true;
        if (dichiarata == SymbolTable.ValueType.String && effettiva == SymbolTable.ValueType.String)
            return true;
        return false;
    }

    public static SymbolTable.ValueType getType_operations(SymbolTable.ValueType operando1, SymbolTable.ValueType operando2){
        if (operando1 == SymbolTable.ValueType.Integer && operando2 == SymbolTable.ValueType.Integer)
            return SymbolTable.ValueType.Integer;
        if (operando1 == SymbolTable.ValueType.Integer && operando2 == SymbolTable.ValueType.Float)
            return SymbolTable.ValueType.Float;
        if (operando1 == SymbolTable.ValueType.Float && operando2 == SymbolTable.ValueType.Integer)
            return SymbolTable.ValueType.Float;
        if (operando1 == SymbolTable.ValueType.Float && operando2 == SymbolTable.ValueType.Float)
            return SymbolTable.ValueType.Float;
        if (operando1 == SymbolTable.ValueType.String && operando2 == SymbolTable.ValueType.String)
            return SymbolTable.ValueType.String;
        return null;
    }

    public static SymbolTable.ValueType getType_boolean(SymbolTable.ValueType t1, SymbolTable.ValueType t2){
        if (t1 == SymbolTable.ValueType.Boolean && t2 == SymbolTable.ValueType.Boolean)
            return SymbolTable.ValueType.Boolean;
        if (t1 == SymbolTable.ValueType.Integer && t2 == SymbolTable.ValueType.Integer)
            return SymbolTable.ValueType.Boolean;
        if (t1 == SymbolTable.ValueType.Integer && t2 == SymbolTable.ValueType.Float)
            return SymbolTable.ValueType.Boolean;
        if (t1 == SymbolTable.ValueType.Float && t2 == SymbolTable.ValueType.Integer)
            return SymbolTable.ValueType.Boolean;
        if (t1 == SymbolTable.ValueType.Float && t2 == SymbolTable.ValueType.Float)
            return SymbolTable.ValueType.Boolean;
        if (t1 == SymbolTable.ValueType.String && t2 == SymbolTable.ValueType.String)
            return SymbolTable.ValueType.Boolean;
        return null;
    }

    public static SymbolTable.ValueType getType_andor(SymbolTable.ValueType t1, SymbolTable.ValueType t2){
        if (t1 == SymbolTable.ValueType.Boolean && t2 == SymbolTable.ValueType.Boolean)
            return SymbolTable.ValueType.Boolean;
        return null;
    }
}
