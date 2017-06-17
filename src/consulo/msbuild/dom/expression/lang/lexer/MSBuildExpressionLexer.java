/* The following code was generated by JFlex 1.4.4 on 6/17/17 7:54 PM */

package consulo.msbuild.dom.expression.lang.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionTokens;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.4
 * on 6/17/17 7:54 PM from the specification file
 * <tt>W:/_github.com/consulo/consulo-msbuild/src/consulo/msbuild/dom/expression/lang/lexer/MSBuildExpressionLexer.flex</tt>
 */
public class MSBuildExpressionLexer extends LexerBase {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int MACRO = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\2\5\0\16\2\10\0\1\3\2\0\1\5\1\4\1\7\6\0"+
    "\12\2\7\0\32\1\1\0\1\6\2\0\1\1\1\0\32\1\4\0"+
    "\41\2\2\0\4\1\4\0\1\1\2\0\1\2\7\0\1\1\4\0"+
    "\1\1\5\0\27\1\1\0\37\1\1\0\u01ca\1\4\0\14\1\16\0"+
    "\5\1\7\0\1\1\1\0\1\1\21\0\160\2\5\1\1\0\2\1"+
    "\2\0\4\1\10\0\1\1\1\0\3\1\1\0\1\1\1\0\24\1"+
    "\1\0\123\1\1\0\213\1\1\0\5\2\2\0\236\1\11\0\46\1"+
    "\2\0\1\1\7\0\47\1\7\0\1\1\1\0\55\2\1\0\1\2"+
    "\1\0\2\2\1\0\2\2\1\0\1\2\10\0\33\1\5\0\3\1"+
    "\15\0\5\2\6\0\1\1\4\0\13\2\5\0\53\1\37\2\4\0"+
    "\2\1\1\2\143\1\1\0\1\1\10\2\1\0\6\2\2\1\2\2"+
    "\1\0\4\2\2\1\12\2\3\1\2\0\1\1\17\0\1\2\1\1"+
    "\1\2\36\1\33\2\2\0\131\1\13\2\1\1\16\0\12\2\41\1"+
    "\11\2\2\1\4\0\1\1\5\0\26\1\4\2\1\1\11\2\1\1"+
    "\3\2\1\1\5\2\22\0\31\1\3\2\104\0\1\1\1\0\13\1"+
    "\67\0\33\2\1\0\4\2\66\1\3\2\1\1\22\2\1\1\7\2"+
    "\12\1\2\2\2\0\12\2\1\0\7\1\1\0\7\1\1\0\3\2"+
    "\1\0\10\1\2\0\2\1\2\0\26\1\1\0\7\1\1\0\1\1"+
    "\3\0\4\1\2\0\1\2\1\1\7\2\2\0\2\2\2\0\3\2"+
    "\1\1\10\0\1\2\4\0\2\1\1\0\3\1\2\2\2\0\12\2"+
    "\4\1\7\0\1\1\5\0\3\2\1\0\6\1\4\0\2\1\2\0"+
    "\26\1\1\0\7\1\1\0\2\1\1\0\2\1\1\0\2\1\2\0"+
    "\1\2\1\0\5\2\4\0\2\2\2\0\3\2\3\0\1\2\7\0"+
    "\4\1\1\0\1\1\7\0\14\2\3\1\1\2\13\0\3\2\1\0"+
    "\11\1\1\0\3\1\1\0\26\1\1\0\7\1\1\0\2\1\1\0"+
    "\5\1\2\0\1\2\1\1\10\2\1\0\3\2\1\0\3\2\2\0"+
    "\1\1\17\0\2\1\2\2\2\0\12\2\1\0\1\1\17\0\3\2"+
    "\1\0\10\1\2\0\2\1\2\0\26\1\1\0\7\1\1\0\2\1"+
    "\1\0\5\1\2\0\1\2\1\1\7\2\2\0\2\2\2\0\3\2"+
    "\10\0\2\2\4\0\2\1\1\0\3\1\2\2\2\0\12\2\1\0"+
    "\1\1\20\0\1\2\1\1\1\0\6\1\3\0\3\1\1\0\4\1"+
    "\3\0\2\1\1\0\1\1\1\0\2\1\3\0\2\1\3\0\3\1"+
    "\3\0\14\1\4\0\5\2\3\0\3\2\1\0\4\2\2\0\1\1"+
    "\6\0\1\2\16\0\12\2\11\0\1\1\7\0\3\2\1\0\10\1"+
    "\1\0\3\1\1\0\27\1\1\0\12\1\1\0\5\1\3\0\1\1"+
    "\7\2\1\0\3\2\1\0\4\2\7\0\2\2\1\0\2\1\6\0"+
    "\2\1\2\2\2\0\12\2\22\0\2\2\1\0\10\1\1\0\3\1"+
    "\1\0\27\1\1\0\12\1\1\0\5\1\2\0\1\2\1\1\7\2"+
    "\1\0\3\2\1\0\4\2\7\0\2\2\7\0\1\1\1\0\2\1"+
    "\2\2\2\0\12\2\1\0\2\1\17\0\2\2\1\0\10\1\1\0"+
    "\3\1\1\0\51\1\2\0\1\1\7\2\1\0\3\2\1\0\4\2"+
    "\1\1\10\0\1\2\10\0\2\1\2\2\2\0\12\2\12\0\6\1"+
    "\2\0\2\2\1\0\22\1\3\0\30\1\1\0\11\1\1\0\1\1"+
    "\2\0\7\1\3\0\1\2\4\0\6\2\1\0\1\2\1\0\10\2"+
    "\22\0\2\2\15\0\60\1\1\2\2\1\7\2\4\0\10\1\10\2"+
    "\1\0\12\2\47\0\2\1\1\0\1\1\2\0\2\1\1\0\1\1"+
    "\2\0\1\1\6\0\4\1\1\0\7\1\1\0\3\1\1\0\1\1"+
    "\1\0\1\1\2\0\2\1\1\0\4\1\1\2\2\1\6\2\1\0"+
    "\2\2\1\1\2\0\5\1\1\0\1\1\1\0\6\2\2\0\12\2"+
    "\2\0\4\1\40\0\1\1\27\0\2\2\6\0\12\2\13\0\1\2"+
    "\1\0\1\2\1\0\1\2\4\0\2\2\10\1\1\0\44\1\4\0"+
    "\24\2\1\0\2\2\5\1\13\2\1\0\44\2\11\0\1\2\71\0"+
    "\53\1\24\2\1\1\12\2\6\0\6\1\4\2\4\1\3\2\1\1"+
    "\3\2\2\1\7\2\3\1\4\2\15\1\14\2\1\1\17\2\2\0"+
    "\46\1\1\0\1\1\5\0\1\1\2\0\53\1\1\0\u014d\1\1\0"+
    "\4\1\2\0\7\1\1\0\1\1\1\0\4\1\2\0\51\1\1\0"+
    "\4\1\2\0\41\1\1\0\4\1\2\0\7\1\1\0\1\1\1\0"+
    "\4\1\2\0\17\1\1\0\71\1\1\0\4\1\2\0\103\1\2\0"+
    "\3\2\40\0\20\1\20\0\125\1\14\0\u026c\1\2\0\21\1\1\0"+
    "\32\1\5\0\113\1\3\0\3\1\17\0\15\1\1\0\4\1\3\2"+
    "\13\0\22\1\3\2\13\0\22\1\2\2\14\0\15\1\1\0\3\1"+
    "\1\0\2\2\14\0\64\1\40\2\3\0\1\1\3\0\2\1\1\2"+
    "\2\0\12\2\41\0\3\2\2\0\12\2\6\0\130\1\10\0\51\1"+
    "\1\2\1\1\5\0\106\1\12\0\35\1\3\0\14\2\4\0\14\2"+
    "\12\0\12\2\36\1\2\0\5\1\13\0\54\1\4\0\21\2\7\1"+
    "\2\2\6\0\12\2\46\0\27\1\5\2\4\0\65\1\12\2\1\0"+
    "\35\2\2\0\13\2\6\0\12\2\15\0\1\1\130\0\5\2\57\1"+
    "\21\2\7\1\4\0\12\2\21\0\11\2\14\0\3\2\36\1\15\2"+
    "\2\1\12\2\54\1\16\2\14\0\44\1\24\2\10\0\12\2\3\0"+
    "\3\1\12\2\44\1\122\0\3\2\1\0\25\2\4\1\1\2\4\1"+
    "\3\2\2\1\11\0\300\1\47\2\25\0\4\2\u0116\1\2\0\6\1"+
    "\2\0\46\1\2\0\6\1\2\0\10\1\1\0\1\1\1\0\1\1"+
    "\1\0\1\1\1\0\37\1\2\0\65\1\1\0\7\1\1\0\1\1"+
    "\3\0\3\1\1\0\7\1\3\0\4\1\2\0\6\1\4\0\15\1"+
    "\5\0\3\1\1\0\7\1\16\0\5\2\32\0\5\2\20\0\2\1"+
    "\23\0\1\1\13\0\5\2\5\0\6\2\1\0\1\1\15\0\1\1"+
    "\20\0\15\1\3\0\33\1\25\0\15\2\4\0\1\2\3\0\14\2"+
    "\21\0\1\1\4\0\1\1\2\0\12\1\1\0\1\1\3\0\5\1"+
    "\6\0\1\1\1\0\1\1\1\0\1\1\1\0\4\1\1\0\13\1"+
    "\2\0\4\1\5\0\5\1\4\0\1\1\21\0\51\1\u0a77\0\57\1"+
    "\1\0\57\1\1\0\205\1\6\0\4\1\3\2\2\1\14\0\46\1"+
    "\1\0\1\1\5\0\1\1\2\0\70\1\7\0\1\1\17\0\1\2"+
    "\27\1\11\0\7\1\1\0\7\1\1\0\7\1\1\0\7\1\1\0"+
    "\7\1\1\0\7\1\1\0\7\1\1\0\7\1\1\0\40\2\57\0"+
    "\1\1\u01d5\0\3\1\31\0\11\1\6\2\1\0\5\1\2\0\5\1"+
    "\4\0\126\1\2\0\2\2\2\0\3\1\1\0\132\1\1\0\4\1"+
    "\5\0\51\1\3\0\136\1\21\0\33\1\65\0\20\1\u0200\0\u19b6\1"+
    "\112\0\u51cd\1\63\0\u048d\1\103\0\56\1\2\0\u010d\1\3\0\20\1"+
    "\12\2\2\1\24\0\57\1\1\2\4\0\12\2\1\0\31\1\7\0"+
    "\1\2\120\1\2\2\45\0\11\1\2\0\147\1\2\0\4\1\1\0"+
    "\4\1\14\0\13\1\115\0\12\1\1\2\3\1\1\2\4\1\1\2"+
    "\27\1\5\2\20\0\1\1\7\0\64\1\14\0\2\2\62\1\21\2"+
    "\13\0\12\2\6\0\22\2\6\1\3\0\1\1\4\0\12\2\34\1"+
    "\10\2\2\0\27\1\15\2\14\0\35\1\3\0\4\2\57\1\16\2"+
    "\16\0\1\1\12\2\46\0\51\1\16\2\11\0\3\1\1\2\10\1"+
    "\2\2\2\0\12\2\6\0\27\1\3\0\1\1\1\2\4\0\60\1"+
    "\1\2\1\1\3\2\2\1\2\2\5\1\2\2\1\1\1\2\1\1"+
    "\30\0\3\1\2\0\13\1\5\2\2\0\3\1\2\2\12\0\6\1"+
    "\2\0\6\1\2\0\6\1\11\0\7\1\1\0\7\1\221\0\43\1"+
    "\10\2\1\0\2\2\2\0\12\2\6\0\u2ba4\1\14\0\27\1\4\0"+
    "\61\1\u2104\0\u016e\1\2\0\152\1\46\0\7\1\14\0\5\1\5\0"+
    "\1\1\1\2\12\1\1\0\15\1\1\0\5\1\1\0\1\1\1\0"+
    "\2\1\1\0\2\1\1\0\154\1\41\0\u016b\1\22\0\100\1\2\0"+
    "\66\1\50\0\15\1\3\0\20\2\20\0\7\2\14\0\2\1\30\0"+
    "\3\1\31\0\1\1\6\0\5\1\1\0\207\1\2\0\1\2\4\0"+
    "\1\1\13\0\12\2\7\0\32\1\4\0\1\1\1\0\32\1\13\0"+
    "\131\1\3\0\6\1\2\0\6\1\2\0\6\1\2\0\3\1\3\0"+
    "\2\1\3\0\2\1\22\0\3\2\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\2\1\1\2\1\3\1\4\1\5\1\6\1\7";

  private static int [] zzUnpackAction() {
    int [] result = new int[10];
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
    "\0\0\0\10\0\20\0\30\0\20\0\20\0\20\0\40"+
    "\0\20\0\20";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[10];
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
    "\3\3\1\4\1\3\1\5\1\6\1\3\1\7\1\10"+
    "\1\7\1\10\3\7\1\11\14\0\1\12\4\0\3\10"+
    "\4\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[40];
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


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\3\11\1\1\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[10];
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

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  private IElementType myTokenType;
  private int myState;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;



  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 2152) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  @Override
  public IElementType getTokenType() {
    if (myTokenType == null) locateToken();
    return myTokenType;
  }

  @Override
  public final int getTokenStart(){
    if (myTokenType == null) locateToken();
    return zzStartRead;
  }

  @Override
  public final int getTokenEnd(){
    if (myTokenType == null) locateToken();
    return getTokenStart() + yylength();
  }

  @Override
  public void advance() {
    if (myTokenType == null) locateToken();
    myTokenType = null;
  }

  @Override
  public int getState() {
    if (myTokenType == null) locateToken();
    return myState;
  }

  @Override
  public void start(final CharSequence buffer, int startOffset, int endOffset, final int initialState) {
    reset(buffer, startOffset, endOffset, initialState);
    myTokenType = null;
  }

   @Override
   public CharSequence getBufferSequence() {
     return zzBuffer;
   }

   @Override
   public int getBufferEnd() {
     return zzEndRead;
   }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
    myTokenType = null;
  }

   protected void locateToken() {
     if (myTokenType != null) return;
     try {
       myState = yystate();
       myTokenType = advanceImpl();
     }
     catch (java.io.IOException e) { /*Can't happen*/ }
     catch (Error e) {
       // add lexer class name to the error
       final Error error = new Error(getClass().getName() + ": " + e.getMessage());
       error.setStackTrace(e.getStackTrace());
       throw error;
     }
   }

   /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
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
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advanceImpl() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL.charAt(zzCurrentPosL++);
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
              zzInput = zzBufferL.charAt(zzCurrentPosL++);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 7: 
          { yybegin(MACRO); return MSBuildExpressionTokens.MACRO_START;
          }
        case 8: break;
        case 5: 
          { return MSBuildExpressionTokens.MACRO_NAME;
          }
        case 9: break;
        case 4: 
          { return MSBuildExpressionTokens.BAD_CHARACTER;
          }
        case 10: break;
        case 6: 
          { yybegin(YYINITIAL); return MSBuildExpressionTokens.MACRO_STOP;
          }
        case 11: break;
        case 1: 
          { return MSBuildExpressionTokens.TEXT;
          }
        case 12: break;
        case 3: 
          { return MSBuildExpressionTokens.PATH_SEPARATOR;
          }
        case 13: break;
        case 2: 
          { return MSBuildExpressionTokens.SINGLE_QUOTE;
          }
        case 14: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
