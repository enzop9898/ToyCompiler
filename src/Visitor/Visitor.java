package Visitor;
import Node.*;

public interface Visitor <T> {

    T visit(Program progOP);
    T visit(VarDecl vardeclOP);
    T visit(Proc procOP);
    T visit(IfStat ifstatOP);
    T visit(Type typeOP);
    T visit(IdInit idinitOP);
    T visit(ReturnExprs returnexprOP);
    T visit(ParDecl pardeclOP);
    T visit(ReadlnStat readlnOP);
    T visit(WriteStat writestatOP);
    T visit(AssignStat assignstatOP);
    T visit(CallProc callprocOP);
    T visit(WhileStat whilestatOP);
    T visit(Else elseOP);
    T visit(Elif elifOP);
    T visit(Int_const intconstOP);
    T visit(Float_const floatconstOP);
    T visit(String_const stringconstOP);
    T visit(Identifier id);
    T visit(PLUS plusOP);
    T visit(MINUS minusOP);
    T visit(TIMES timesOP);
    T visit(DIV divOP);
    T visit(AND andOP);
    T visit(OR orOP);
    T visit(GT gtOP);
    T visit(GE geOP);
    T visit(LT ltOP);
    T visit(LE leOP);
    T visit(EQ eqOP);
    T visit(NE neOP);
    T visit(NOT notOP);
    T visit(UMINUS uminusOP);
    T visit(False falseOP);
    T visit(True trueOP);
    T visit (Null nullNode);
    T visit (IFEXPR efexpr);
}
