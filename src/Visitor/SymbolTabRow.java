package Visitor;
import Node.*;
import Visitor.SymbolTable.*;

import java.util.ArrayList;

public class SymbolTabRow {

    public String id;
    public ValueType vtype;
    public Kind type;
    public ArrayList<ValueType> inputParams, outputParams;

    public SymbolTabRow(String id, ValueType vtype) {
        this.type = Kind.Variable;
        this.id = id;
        this.vtype = vtype;
    }

    public SymbolTabRow(String id, ArrayList<ValueType> inputParams, ArrayList<ValueType> outputParams) {
        this.type = Kind.Function;
        this.id = id;
        this.inputParams = inputParams;
        this.outputParams = outputParams;
    }

    public boolean isVariable() {
        if (this.type == Kind.Variable) return true; else return false;
    }

    @Override
    public String toString() {
        if (isVariable())
            return "Entry of type Variable :: " + id + " | " + vtype;
        else  {
            String inputs = "";
            for (int i = 0; i < inputParams.size(); i++)
                if (i == inputParams.size()-1)
                    inputs += (inputParams.get(i));
                else inputs += (inputParams.get(i) + ", ");
            String outputs = "";
            for (int i = 0; i < outputParams.size(); i++) {
                if (i == outputParams.size() - 1)
                    outputs += (outputParams.get(i));
                else outputs += (outputParams.get(i) + ", ");
            }
            return "Entry of type Function :: " + id + "(" + inputs + ") -> " + outputs;
        }
    }


}
