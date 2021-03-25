// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: srcjflexcup/toy.flex

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


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int COMMENT = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2, 2
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\4\1\5\3\0\1\6\1\0\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\11\20\1\21"+
    "\1\22\1\23\1\24\1\25\2\0\4\26\1\27\25\26"+
    "\4\0\1\26\1\0\1\30\1\31\1\32\1\33\1\34"+
    "\1\35\1\36\1\37\1\40\2\26\1\41\1\26\1\42"+
    "\1\43\1\44\1\26\1\45\1\46\1\47\1\50\1\51"+
    "\1\52\1\53\2\26\1\0\1\54\u0383\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\4\1\1\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\2\14\1\15\1\16"+
    "\1\17\1\20\1\21\17\22\1\1\1\23\1\24\2\25"+
    "\1\26\1\27\1\30\1\0\1\31\1\32\1\33\1\34"+
    "\2\22\1\35\2\22\1\36\1\22\1\37\2\22\1\40"+
    "\10\22\1\41\1\42\1\43\7\22\1\44\11\22\1\0"+
    "\1\45\1\46\1\47\1\50\3\22\1\51\1\52\2\22"+
    "\1\53\1\54\1\55\2\22\1\43\1\56\1\57\3\22"+
    "\1\60\1\61\1\62\1\63\1\64";

  private static int [] zzUnpackAction() {
    int [] result = new int[118];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\55\0\132\0\207\0\207\0\264\0\207\0\207"+
    "\0\341\0\207\0\207\0\207\0\207\0\207\0\u010e\0\u013b"+
    "\0\u0168\0\u0195\0\u01c2\0\207\0\u01ef\0\207\0\u021c\0\u0249"+
    "\0\u0276\0\u02a3\0\u02d0\0\u02fd\0\u032a\0\u0357\0\u0384\0\u03b1"+
    "\0\u03de\0\u040b\0\u0438\0\u0465\0\u0492\0\u04bf\0\u04ec\0\207"+
    "\0\207\0\207\0\u0519\0\207\0\207\0\207\0\u0546\0\207"+
    "\0\207\0\207\0\207\0\u0573\0\u05a0\0\u0249\0\u05cd\0\u05fa"+
    "\0\u0249\0\u0627\0\u0654\0\u0681\0\u06ae\0\u0249\0\u06db\0\u0708"+
    "\0\u0735\0\u0762\0\u078f\0\u07bc\0\u07e9\0\u0816\0\207\0\207"+
    "\0\u0843\0\u0870\0\u089d\0\u08ca\0\u08f7\0\u0924\0\u0951\0\u097e"+
    "\0\u0249\0\u09ab\0\u09d8\0\u0a05\0\u0a32\0\u0a5f\0\u0a8c\0\u0ab9"+
    "\0\u0ae6\0\u0b13\0\u0b40\0\u0249\0\u0249\0\u0249\0\u0249\0\u0b6d"+
    "\0\u0b9a\0\u0bc7\0\u0249\0\u0249\0\u0bf4\0\u0c21\0\u0249\0\u0249"+
    "\0\u0249\0\u0c4e\0\u0c7b\0\u0b40\0\u0249\0\u0249\0\u0ca8\0\u0cd5"+
    "\0\u0d02\0\u0249\0\u0249\0\u0249\0\u0249\0\u0249";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[118];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\1\6\1\7\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\4\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\3\30\1\31\1\32"+
    "\1\33\1\34\1\35\2\30\1\36\1\30\1\37\1\40"+
    "\1\41\1\42\1\43\1\44\1\30\1\45\1\46\1\30"+
    "\1\47\5\50\1\51\47\50\11\52\1\53\4\52\1\4"+
    "\36\52\57\0\1\5\60\0\1\54\73\0\1\55\40\0"+
    "\1\56\60\0\1\57\54\0\1\57\1\0\2\22\60\0"+
    "\1\60\54\0\1\61\1\62\53\0\1\63\47\0\2\30"+
    "\5\0\26\30\20\0\2\30\5\0\15\30\1\64\10\30"+
    "\20\0\2\30\5\0\15\30\1\65\10\30\20\0\2\30"+
    "\5\0\15\30\1\66\10\30\20\0\2\30\5\0\13\30"+
    "\1\67\12\30\20\0\2\30\5\0\2\30\1\70\7\30"+
    "\1\71\1\72\12\30\20\0\2\30\5\0\7\30\1\73"+
    "\4\30\1\74\11\30\20\0\2\30\5\0\22\30\1\75"+
    "\3\30\20\0\2\30\5\0\5\30\1\76\20\30\20\0"+
    "\2\30\5\0\17\30\1\77\6\30\20\0\2\30\5\0"+
    "\6\30\1\100\17\30\20\0\2\30\5\0\21\30\1\101"+
    "\4\30\20\0\2\30\5\0\11\30\1\102\5\30\1\103"+
    "\6\30\20\0\2\30\5\0\15\30\1\104\10\30\20\0"+
    "\2\30\5\0\11\30\1\105\5\30\1\106\6\30\55\0"+
    "\1\107\16\0\1\110\55\0\1\57\1\111\53\0\2\30"+
    "\5\0\15\30\1\112\10\30\20\0\2\30\5\0\17\30"+
    "\1\113\6\30\20\0\2\30\5\0\12\30\1\114\5\30"+
    "\1\115\5\30\20\0\2\30\5\0\13\30\1\116\12\30"+
    "\20\0\2\30\5\0\15\30\1\117\10\30\20\0\2\30"+
    "\5\0\6\30\1\120\17\30\20\0\2\30\5\0\21\30"+
    "\1\121\4\30\20\0\2\30\5\0\13\30\1\122\12\30"+
    "\20\0\2\30\5\0\15\30\1\123\10\30\20\0\2\30"+
    "\5\0\2\30\1\124\23\30\20\0\2\30\5\0\17\30"+
    "\1\125\6\30\20\0\2\30\5\0\6\30\1\126\17\30"+
    "\20\0\2\30\5\0\22\30\1\127\3\30\20\0\2\30"+
    "\5\0\12\30\1\130\13\30\20\0\2\30\5\0\12\30"+
    "\1\131\13\30\20\0\2\30\5\0\12\30\1\132\13\30"+
    "\20\0\1\57\1\111\6\0\1\133\44\0\2\30\5\0"+
    "\13\30\1\134\12\30\20\0\2\30\5\0\16\30\1\135"+
    "\7\30\20\0\2\30\5\0\7\30\1\136\16\30\20\0"+
    "\2\30\5\0\6\30\1\137\17\30\20\0\2\30\5\0"+
    "\20\30\1\140\5\30\20\0\2\30\5\0\2\30\1\141"+
    "\23\30\20\0\2\30\5\0\25\30\1\142\20\0\2\30"+
    "\5\0\13\30\1\143\12\30\20\0\2\30\5\0\4\30"+
    "\1\144\21\30\20\0\2\30\5\0\5\30\1\145\20\30"+
    "\20\0\2\30\5\0\12\30\1\146\13\30\20\0\2\30"+
    "\5\0\14\30\1\147\11\30\20\0\2\30\5\0\6\30"+
    "\1\150\17\30\20\0\2\30\5\0\5\30\1\151\20\30"+
    "\20\0\2\30\5\0\13\30\1\152\12\30\20\0\2\30"+
    "\5\0\21\30\1\153\4\30\21\0\1\154\53\0\2\30"+
    "\5\0\6\30\1\155\17\30\20\0\2\30\5\0\21\30"+
    "\1\156\4\30\20\0\2\30\5\0\16\30\1\157\7\30"+
    "\20\0\2\30\5\0\13\30\1\160\12\30\20\0\2\30"+
    "\5\0\14\30\1\161\11\30\20\0\2\30\5\0\6\30"+
    "\1\162\17\30\20\0\2\30\5\0\6\30\1\163\17\30"+
    "\20\0\2\30\5\0\17\30\1\164\6\30\20\0\2\30"+
    "\5\0\14\30\1\165\11\30\20\0\2\30\5\0\10\30"+
    "\1\166\15\30\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3375];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\2\11\1\1\2\11\1\1\5\11\5\1\1\11"+
    "\1\1\1\11\21\1\3\11\1\1\3\11\1\0\4\11"+
    "\23\1\2\11\22\1\1\0\33\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[118];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */

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



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Yylex(java.io.Reader in) {
  stringTable = new ArrayList<ElementoSym>();
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case STRING: {
              String lex = yytext();
                            throw new Error("Stringa"+ lex +" non chiusa alla posizione: " + (yyline+1) + ":" + yycolumn);
            }  // fall though
            case 119: break;
            case COMMENT: {
              String lex = yytext();
                           throw new Error("Commento " +lex+ "non chiuso alla posizione: " + (yyline+1) + ":" + yycolumn);
            }  // fall though
            case 120: break;
            default:
              {
                return new Symbol(sym.EOF);
              }
        }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { String lex = yytext();
            throw new Error("Errore alla posizione: " + (yyline+1) + ":" + yycolumn+ ", " + lex );
            }
            // fall through
          case 53: break;
          case 2:
            { /* do nothing */
            }
            // fall through
          case 54: break;
          case 3:
            { return generateToken(sym.NOT);
            }
            // fall through
          case 55: break;
          case 4:
            { stringa = new StringBuilder();
                                          yybegin(STRING);
            }
            // fall through
          case 56: break;
          case 5:
            { return generateToken(sym.LPAR);
            }
            // fall through
          case 57: break;
          case 6:
            { return generateToken(sym.RPAR);
            }
            // fall through
          case 58: break;
          case 7:
            { return generateToken(sym.TIMES);
            }
            // fall through
          case 59: break;
          case 8:
            { return generateToken(sym.PLUS);
            }
            // fall through
          case 60: break;
          case 9:
            { return generateToken(sym.COMMA);
            }
            // fall through
          case 61: break;
          case 10:
            { return generateToken(sym.MINUS);
            }
            // fall through
          case 62: break;
          case 11:
            { return generateToken(sym.DIV);
            }
            // fall through
          case 63: break;
          case 12:
            { return generateToken(sym.INT_CONST,yytext());
            }
            // fall through
          case 64: break;
          case 13:
            { return generateToken(sym.COLON);
            }
            // fall through
          case 65: break;
          case 14:
            { return generateToken(sym.SEMI);
            }
            // fall through
          case 66: break;
          case 15:
            { return generateToken(sym.LT);
            }
            // fall through
          case 67: break;
          case 16:
            { return generateToken(sym.EQ);
            }
            // fall through
          case 68: break;
          case 17:
            { return generateToken(sym.GT);
            }
            // fall through
          case 69: break;
          case 18:
            { return generateToken(sym.ID, yytext());
            }
            // fall through
          case 70: break;
          case 19:
            { stringa.append(yytext());
            }
            // fall through
          case 71: break;
          case 20:
            { yybegin(YYINITIAL);
                        return generateToken(sym.STRING_CONST, stringa.toString());
            }
            // fall through
          case 72: break;
          case 21:
            { 
            }
            // fall through
          case 73: break;
          case 22:
            { return generateToken(sym.AND);
            }
            // fall through
          case 74: break;
          case 23:
            { return generateToken(sym.RETURN);
            }
            // fall through
          case 75: break;
          case 24:
            { yybegin(COMMENT);
            }
            // fall through
          case 76: break;
          case 25:
            { return generateToken(sym.ASSIGN);
            }
            // fall through
          case 77: break;
          case 26:
            { return generateToken(sym.LE);
            }
            // fall through
          case 78: break;
          case 27:
            { return generateToken(sym.NE);
            }
            // fall through
          case 79: break;
          case 28:
            { return generateToken(sym.GE);
            }
            // fall through
          case 80: break;
          case 29:
            { return generateToken(sym.DO);
            }
            // fall through
          case 81: break;
          case 30:
            { return generateToken(sym.FI);
            }
            // fall through
          case 82: break;
          case 31:
            { return generateToken(sym.IF);
            }
            // fall through
          case 83: break;
          case 32:
            { return generateToken(sym.OD);
            }
            // fall through
          case 84: break;
          case 33:
            { return generateToken(sym.OR);
            }
            // fall through
          case 85: break;
          case 34:
            { yybegin(YYINITIAL);
            }
            // fall through
          case 86: break;
          case 35:
            { return  generateToken(sym.FLOAT_CONST,yytext());
            }
            // fall through
          case 87: break;
          case 36:
            { return generateToken(sym.INT);
            }
            // fall through
          case 88: break;
          case 37:
            { return generateToken(sym.BOOL);
            }
            // fall through
          case 89: break;
          case 38:
            { return generateToken(sym.CORP);
            }
            // fall through
          case 90: break;
          case 39:
            { return generateToken(sym.ELIF);
            }
            // fall through
          case 91: break;
          case 40:
            { return generateToken(sym.ELSE);
            }
            // fall through
          case 92: break;
          case 41:
            { return generateToken(sym.NULL);
            }
            // fall through
          case 93: break;
          case 42:
            { return generateToken(sym.PROC);
            }
            // fall through
          case 94: break;
          case 43:
            { return generateToken(sym.THEN);
            }
            // fall through
          case 95: break;
          case 44:
            { return generateToken(sym.TRUE);
            }
            // fall through
          case 96: break;
          case 45:
            { return generateToken(sym.VOID);
            }
            // fall through
          case 97: break;
          case 46:
            { return generateToken(sym.FALSE);
            }
            // fall through
          case 98: break;
          case 47:
            { return generateToken(sym.FLOAT);
            }
            // fall through
          case 99: break;
          case 48:
            { return generateToken(sym.WHILE);
            }
            // fall through
          case 100: break;
          case 49:
            { return generateToken(sym.WRITE);
            }
            // fall through
          case 101: break;
          case 50:
            { return generateToken(sym.IFEXPR);
            }
            // fall through
          case 102: break;
          case 51:
            { return generateToken(sym.READ);
            }
            // fall through
          case 103: break;
          case 52:
            { return generateToken(sym.STRING);
            }
            // fall through
          case 104: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
