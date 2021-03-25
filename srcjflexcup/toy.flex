/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


/**
   This is a small example of a standalone text substitution scanner
   It reads a name after the keyword name and substitutes all occurences
   of "hello" with "hello <name>!". There is a sample input file
   "sample.inp" provided in this directory
*/

import java_cup.runtime.Symbol; //This is how we pass tokens to the parser
import java.util.ArrayList;
import java.io.*;

%%

%public
%unicode                                // We wish to read text files
%cup
%line
%column
%init{
stringTable = new ArrayList<ElementoSym>();
%init}

%{

  public static ArrayList<ElementoSym> stringTable;
  StringBuilder stringa = new StringBuilder();  //stringa aggiunta !


  //private static HashMap<String, String> keywordsTable;
  public static final String[] terminalNames= new String[]{
              "SEMI","COMMA","ID","INT","STRING","FLOAT","BOOL","PROC","LPAR","RPAR","COLON","CORP",

                          "VOID","IF","THEN","ELIF","FI","ELSE","WHILE","DO","OD","READ","WRITE","ASSIGN","PLUS",

                          "MINUS","TIMES","DIV","EQ","NE","LT","LE","GT","GE","AND","OR","NOT","NULL","TRUE","FALSE",

                          "INT_CONST","FLOAT_CONST","STRING_CONST","ERROR","RETURN","EOF","IFEXPR"
      };

      private Symbol generateToken(int type) {
              return new Symbol(type);
            }

      private Symbol generateToken (int type, Object lessema){
          ElementoSym nuovo;
          nuovo = new ElementoSym("");

          if (type == 45) return new Symbol(type, yyline, yycolumn, lessema.toString());
          else if (type == 46 && !stringTable.contains(lessema.toString())) {
              nuovo.setLessemaString(lessema.toString());
              stringTable.add(nuovo);
          } else if (type == 43 || type == 44) {
              nuovo.setLessemaString(lessema.toString());
              stringTable.add(nuovo);
          }
          //System.out.println("lessema fin qui letto: "+ lessema.toString());
          return new Symbol(type, yyline, yycolumn, lessema.toString());

      }

      private Symbol generateError(Object value) {
              return new Symbol(sym.error, yyline, yycolumn, value);
            }

%}

Identifier = ([A-Za-z]|\_)([A-Za-z0-9]|\_)*
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator}|[ \t\f]
Number_int = ((0|[1-9][0-9]*))
Number_float = ((0|[1-9][0-9]*)(\.[0-9]*[1-9]+((E)[1-9]+)?)?)
Character = \'.\'
/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent = ( [^*] | \*+ [^/*] )*


%state STRING
%state COMMENT

%%

    /* keywords */
      <YYINITIAL> "if"                  { return generateToken(sym.IF); }
      <YYINITIAL> "then"                { return generateToken(sym.THEN); }
      <YYINITIAL> "else"                { return generateToken(sym.ELSE); }
      <YYINITIAL> "while"               { return generateToken(sym.WHILE); }
      <YYINITIAL> "int"                 { return generateToken(sym.INT); }
      <YYINITIAL> "float"               { return generateToken(sym.FLOAT); }
      <YYINITIAL> "string"               { return generateToken(sym.STRING); }
      <YYINITIAL> "bool"               { return generateToken(sym.BOOL); }
      <YYINITIAL> "proc"               { return generateToken(sym.PROC); }
      <YYINITIAL> "corp"               { return generateToken(sym.CORP); }
      <YYINITIAL> "float"               { return generateToken(sym.FLOAT); }
      <YYINITIAL> "void"               { return generateToken(sym.VOID); }
      <YYINITIAL> "elif"               { return generateToken(sym.ELIF); }
      <YYINITIAL> "fi"               { return generateToken(sym.FI); }
      <YYINITIAL> "do"               { return generateToken(sym.DO); }
      <YYINITIAL> "od"               { return generateToken(sym.OD); }
      <YYINITIAL> "readln"               { return generateToken(sym.READ); }
      <YYINITIAL> "write"               { return generateToken(sym.WRITE); }
      <YYINITIAL> "null"               { return generateToken(sym.NULL); }
      <YYINITIAL> "true"               { return generateToken(sym.TRUE); }
      <YYINITIAL> "false"               { return generateToken(sym.FALSE); }
      <YYINITIAL> "null"               { return generateToken(sym.NULL); }
      <YYINITIAL> "ifexpr"             {return generateToken(sym.IFEXPR);}


    /* separators */
      <YYINITIAL> "("                   { return generateToken(sym.LPAR); }
      <YYINITIAL> ")"                   { return generateToken(sym.RPAR); } // fare keywords e installID poi aggiustare number
      <YYINITIAL> ","                   { return generateToken(sym.COMMA); }
      <YYINITIAL> ";"                   { return generateToken(sym.SEMI); }
      <YYINITIAL> ":"                   { return generateToken(sym.COLON); }

      /* relop */
      <YYINITIAL> "<"                   { return generateToken(sym.LT); }
      <YYINITIAL> "<="                  { return generateToken(sym.LE); }
      <YYINITIAL> "="                  { return generateToken(sym.EQ); }
      <YYINITIAL> "<>"                  { return generateToken(sym.NE); }
      <YYINITIAL> ">"                   { return generateToken(sym.GT); }
      <YYINITIAL> ">="                  { return generateToken(sym.GE); }
      <YYINITIAL> ":="                 { return generateToken(sym.ASSIGN); }
      <YYINITIAL> "+"               { return generateToken(sym.PLUS); }
      <YYINITIAL> "-"               { return generateToken(sym.MINUS); }
      <YYINITIAL> "*"               { return generateToken(sym.TIMES); }
      <YYINITIAL> "/"               { return generateToken(sym.DIV); }
      <YYINITIAL> "&&"               { return generateToken(sym.AND); }
      <YYINITIAL> "||"               { return generateToken(sym.OR); }
      <YYINITIAL> "!"               { return generateToken(sym.NOT); }
      <YYINITIAL> "->"               { return generateToken(sym.RETURN); }


<YYINITIAL>{
      {WhiteSpace} { /* do nothing */ }

      /* literals */
      \"                                { stringa = new StringBuilder();
                                          yybegin(STRING); }

      /* identifiers */
      {Identifier}                      { return generateToken(sym.ID, yytext());}

      /* literals */
      {Number_int}                      { return generateToken(sym.INT_CONST,yytext()); }
      {Number_float}                    { return  generateToken(sym.FLOAT_CONST,yytext());}

      "/*"                              { yybegin(COMMENT); }


}
//string
<STRING> {

    <<EOF>>         {       String lex = yytext();
                            throw new Error("Stringa"+ lex +" non chiusa alla posizione: " + (yyline+1) + ":" + yycolumn);
                        }
    "\""              { yybegin(YYINITIAL);
                        return generateToken(sym.STRING_CONST, stringa.toString()); }
    [^\"]               { stringa.append(yytext()); }

}

<COMMENT> {
    <<EOF>>            {
                           String lex = yytext();
                           throw new Error("Commento " +lex+ "non chiuso alla posizione: " + (yyline+1) + ":" + yycolumn);
                        }
    "*/"               {
                            yybegin(YYINITIAL);
                        }
    "*"                { }
    [^"*/"]            { }
}


/* error fallback */
[^] {       String lex = yytext();
            throw new Error("Errore alla posizione: " + (yyline+1) + ":" + yycolumn+ ", " + lex ); }

<<EOF>> {return new Symbol(sym.EOF);}