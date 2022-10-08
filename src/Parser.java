//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
import EtapaLexico.Semantica.*;
import EtapaLexico.AnalisisLexico;
import EtapaLexico.Token;
import Compilador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//#line 26 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short CADENA=259;
public final static short IF=260;
public final static short THEN=261;
public final static short ELSE=262;
public final static short ENDIF=263;
public final static short OUT=264;
public final static short FUN=265;
public final static short RETURN=266;
public final static short BREAK=267;
public final static short WHEN=268;
public final static short WHILE=269;
public final static short MAYOR_IGUAL=270;
public final static short MENOR_IGUAL=271;
public final static short ASIGNACION=272;
public final static short DISTINTO=273;
public final static short ENTERO=274;
public final static short DOUBLE=275;
public final static short CONST=276;
public final static short DEFER=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    3,    3,    3,    3,    5,
    5,    7,    7,    7,    8,    8,   10,   10,   10,   10,
   10,   12,   12,   13,   13,   13,    4,    4,   11,   11,
   14,   14,   16,   16,   16,   16,   18,   18,   22,   22,
   23,   23,   19,   19,   20,   20,    9,   24,   24,    2,
    2,   17,   17,   28,   28,   28,   28,   28,   28,   31,
   31,   32,   32,   32,   32,   33,   33,   34,   34,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   37,   37,
   27,   27,   38,   38,   38,   38,   38,   38,   36,   36,
   29,   29,   29,   29,   29,   29,   29,   39,   39,   40,
   40,   40,   40,   21,   21,   21,   21,   21,   25,   25,
   25,   25,   41,   41,   41,   41,   41,   41,   42,   42,
    6,   26,   15,   15,   15,   15,   15,   15,   15,   15,
   43,   43,   43,   45,   45,   45,   44,   44,   44,   44,
   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    4,    1,    3,    3,    1,    3,    2,    2,    1,    3,
    1,    1,    1,    1,    2,    1,    7,    6,    6,    5,
    6,    1,    3,    2,    1,    1,    1,    1,    9,    8,
    2,    1,    1,    1,    1,    1,    4,    5,    9,    6,
    9,    6,   13,   10,   16,   18,    2,    2,    1,    2,
    1,    1,    1,    2,    2,    2,    2,    2,    2,    9,
    7,   12,   10,   14,   12,    4,    5,    5,    3,    5,
    6,    5,    5,    3,    2,    4,    3,    3,    9,    7,
    2,    1,    2,    2,    2,    2,    2,    2,    1,    2,
    4,    5,    7,    7,    4,    5,    5,    5,    3,    5,
    3,    4,    4,    3,    2,    2,    1,    2,    3,    2,
    2,    1,    1,    1,    1,    1,    1,    1,    2,    1,
    2,    3,    3,    3,    1,    6,    6,    4,    5,    5,
    3,    3,    1,    1,    1,    2,    1,    6,    4,    3,
    5,    3,    1,    2,
};
final static short yydefred[] = {                         0,
    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   27,   28,    0,    0,    4,    0,   12,    0,    0,
    0,   53,   13,   14,    0,   51,    0,   52,    0,    0,
    0,    0,   59,    0,    0,    0,  135,  116,  117,  118,
    0,    0,  113,  114,  115,    0,    0,    0,    0,    0,
    0,  133,  137,  144,    0,    0,    0,    0,    0,    0,
  120,    0,    0,   47,    1,   50,   11,    0,    8,    0,
    7,    0,   15,   54,   55,   56,   57,   58,    0,    0,
    0,  136,  108,    0,    0,    0,    0,    0,    0,    0,
    0,  105,    0,    0,    0,    0,  142,    0,   25,    0,
    0,    0,    0,    0,    0,  119,    6,   10,    0,    0,
    0,    0,    0,    0,   32,   33,   34,   35,   36,    0,
  134,  140,    0,  104,    0,    0,    0,    0,    0,    0,
   95,    0,    0,    0,    0,   49,    0,   91,    0,    0,
  131,  132,    0,    0,    0,    0,   24,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   31,    0,    0,
  139,    0,    0,  128,    0,    0,    0,    0,   99,   97,
    0,   48,   96,    0,   92,  141,    0,    0,   20,    0,
   23,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  129,    0,  130,    0,    0,    0,    0,  101,
    0,    0,    0,   18,    0,   21,   19,    0,    0,    0,
    0,    0,    0,    0,   37,    0,    0,    0,    0,    0,
    0,  138,  126,  127,  102,    0,  103,   98,   93,   94,
   17,    0,   61,    0,    0,    0,    0,    0,    0,   38,
    0,    0,    0,    0,    0,  100,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   60,
    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   82,   63,    0,   40,    0,    0,    0,
    0,    0,    0,   29,    0,    0,    0,   90,    0,   83,
    0,   81,   85,   87,   84,   88,   86,    0,    0,   42,
    0,    0,    0,    0,    0,   65,    0,    0,    0,   62,
    0,    0,    0,   44,    0,    0,    0,    0,    0,    0,
   66,    0,    0,    0,   39,    0,    0,    0,   64,    0,
   69,    0,    0,    0,   67,    0,    0,   41,    0,    0,
    0,   74,    0,    0,   78,    0,    0,    0,   43,    0,
   68,    0,    0,    0,    0,   80,    0,    0,   72,    0,
   70,    0,    0,    0,   71,   79,    0,   45,    0,   46,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   46,   20,   21,   22,   23,   24,   25,
   73,  102,  103,  114,   47,  115,  116,  117,  118,  119,
   48,  186,  216,  166,   49,   27,  278,  172,   29,   30,
   31,   32,  281,  318,  332,  282,  283,  284,   91,  134,
   50,   62,   51,   52,   53,
};
final static short yysindex[] = {                      -219,
    0,    0,  -57,  206,   13,  -52,   95,  -20,  -27,   36,
   84,    0,    0, -103,  625,    0,  237,    0,  -95,   18,
  107,    0,    0,    0,   23,    0,  109,    0,  122,  127,
  140,  143,    0,   16,  -54,  177,    0,    0,    0,    0,
    2,  103,    0,    0,    0,  223,  -18, -111,  239,   16,
   21,    0,    0,    0,  -37,  243,  -39,  389,  389,   26,
    0, -103,  -52,    0,    0,    0,    0,   27,    0,   38,
    0,  700,    0,    0,    0,    0,    0,    0,    7,  268,
   10,    0,    0,  280,   16,   60,   60,   16,  294,  625,
  -90,    0,    7,   60,   60,  284,    0,  -25,    0,  274,
   82,  287,  304,  318,  334,    0,    0,    0,  -26,   95,
  345,  356,  362,  722,    0,    0,    0,    0,    0,  389,
    0,    0,   56,    0,   37,   61,   21,   21,    7,  353,
    0,  625,  322,  130,  -56,    0,  309,    0,  625,  132,
    0,    0,  346,  351,  372,   69,    0,  358, -210,  160,
  366,  157,  -84,   16,  389,  389,  385,    0,  392,   66,
    0,   29,   49,    0,  427,  503,  376,  509,    0,    0,
  182,    0,    0,  519,    0,    0,   69,   35,    0,   69,
    0,  355,  407,  412,  278,   72,   96,  413,  414,   16,
  395,  420,    0,  156,    0,  426,  406,  536,  410,    0,
  418,  209,  216,    0,   69,    0,    0,  625,  431, -103,
  389,  455,  625, -108,    0,  228,  439,  242,  442,  106,
  467,    0,    0,    0,    0,  449,    0,    0,    0,    0,
    0,  546,    0,  468,  469,   16,  451,  476,  625,    0,
  393,  323,  496,  478, -103,    0,  484,  381,  487,  270,
  499,   16, -135,  490,  513,  625, -103,  434,  520,    0,
 -150,  501,  527,  511,   16,  341,  528,    0,   16,  465,
  530,  515,  409,   95,  324,  535,  523,  561,  525,  526,
  529,  534,  537,    0,    0, -103,    0,  400,  539,   16,
  443,  555,  425,    0, -150,  540,  325,    0,  389,    0,
  543,    0,    0,    0,    0,    0,    0,  566,  483,    0,
  444,  557,   16, -150,  567,    0, -115,  101,  577,    0,
  441,  568,  495,    0,  474,  475,  570, -150,  572,  337,
    0,  370,  364, -150,    0,  573,  510,  596,    0,  581,
    0,   -3, -150,  151,    0,  -97,  489,    0,  584,   16,
  589,    0,  -34,  587,    0, -150,  592,  612,    0,  480,
    0,  594,  481,  598,  601,    0,   16,  531,    0,  613,
    0,  615,  488,  616,    0,    0,  551,    0,  620,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  655,    0,    0,    1,    0,  621,    0,    0,
    0,    0,    0,    0,    0,    0,  688,    0,    0,   28,
    0,    0,    0,    0,   81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  133,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -82,  -32,
  166,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  631,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,    0,    0,  -77,    0,    0,    0,  -24,    0,    0,
    0,    0,  -19,    0,    0,    0,    0,    0,    0,    0,
   71,    0,  658,    0,    0,    0,    0,    0,  123,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   44,  369,  397,  -17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  603,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   87,    0,  144,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  641,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  440,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  450,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  453,    0,    0,    0,    0,    0,  460,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,  350,  683,    0,    0,    0,    0,    0,
    0,  606,  565,    0,  -31,  604,   32,    0,    0,    0,
 -105,    0,    0,  -23,  -28,  -14, -255,    6,    0,  562,
    0,  605,    0,    0,    0, -288,    0,  597,    0,  -73,
  677,    0,   -2,  277,  -47,
};
final static int YYTABLESIZE=999;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         61,
   11,  100,   79,   97,  153,   35,  362,  328,  112,   28,
   41,   90,   57,   84,  239,  144,  110,  140,   93,   55,
   64,  111,   28,  109,   86,  356,   87,    9,  329,  104,
  105,  152,  139,  123,  122,   26,  353,    1,   90,  315,
  107,   44,   45,   43,   11,  106,   99,  106,   66,   86,
  122,   87,  122,  125,   41,  352,  129,  357,  326,   11,
   41,   70,   94,   12,   13,    4,  135,   95,  171,  193,
   70,   33,  340,   41,  344,   58,   69,   28,  347,  162,
   16,  163,  126,  127,  128,  107,  125,  354,  125,  195,
  112,  159,  205,   41,  133,  136,  161,  202,  110,  160,
  365,  164,   94,  111,   41,  109,   63,   95,  168,  274,
   41,   26,  192,    8,   26,  174,  275,  276,   11,   28,
    5,   63,  187,   59,    7,   11,  188,  189,    8,  123,
  267,  123,   10,   11,   42,  167,  217,  136,   86,   41,
   87,  198,  167,   83,  136,   72,  244,   41,   86,   89,
   87,  275,    9,   60,   44,   45,   43,  238,  220,  194,
  196,   67,   44,   45,   43,   71,   11,   74,  297,  275,
  136,  137,  138,  134,  134,  134,  185,  134,  107,  134,
   75,   11,  235,  106,  232,   76,  124,  209,  124,  237,
  133,  134,  134,  134,  134,  234,  223,   94,   77,    5,
   63,   78,   95,    7,  250,   16,  125,    8,  125,  355,
  125,   10,   11,  136,   80,  253,   81,   99,  136,   34,
  266,   96,   36,   37,  125,  125,  125,  125,  112,   56,
  259,   99,  270,  288,   12,   13,  110,  291,   54,   12,
   13,  111,  271,  109,  136,   34,  277,  209,   12,   13,
  122,   38,   39,  262,   40,  134,   11,   11,  311,   82,
   11,  136,   85,  277,   11,   11,  121,   37,   11,   11,
  319,  308,   36,   37,   11,   11,   11,   11,  296,   92,
  277,  325,   98,    9,    9,   36,   37,    9,  125,   12,
   13,    9,    9,    9,  108,    9,    9,   34,  262,  277,
  277,    9,    9,    9,    9,   36,   37,  120,   12,   13,
  264,  277,   86,  277,   87,  277,   36,   37,  360,  277,
  124,  363,  121,   37,  143,  277,  296,  148,  277,  277,
   16,  146,  277,  214,  215,  373,   16,   16,  147,  277,
   16,  277,   12,   13,   16,   16,   16,  149,   16,   16,
  277,   36,   37,   19,   16,   16,   16,   16,  150,   36,
   37,   65,  330,  331,   38,   39,   19,   40,   12,   13,
  141,  142,   38,   39,  151,   40,   12,   13,   11,   11,
  169,  289,   11,   86,  154,   87,   11,   11,   11,  134,
   11,   11,  170,  134,  175,  155,   11,   11,   11,   11,
  213,  156,  134,  134,  176,  134,  101,   63,  177,  123,
  274,  123,  178,  123,    8,  180,  132,  275,  276,   11,
  182,   19,  125,  183,  190,  184,  125,  123,  123,  123,
  123,  165,  191,   41,  200,  125,  125,  124,  125,  124,
  309,  124,   86,  130,   87,  256,  210,  101,   44,   45,
   43,  211,  221,  218,  219,  124,  124,  124,  124,  343,
  222,    5,    6,   19,  225,    7,  224,   94,  227,    8,
    9,  229,   95,   10,   11,  165,  228,  208,  230,   12,
   13,   14,   15,  312,  323,   86,   86,   87,   87,  233,
  240,  123,    5,    6,  236,  179,    7,  241,  101,  243,
    8,    9,  242,  261,   10,   11,  245,  246,  248,  249,
   12,   13,   14,   15,  337,  252,   86,  254,   87,  124,
  368,  370,   86,   86,   87,   87,  204,  206,  377,  207,
   86,  295,   87,    5,   63,  257,  258,    7,  265,  130,
  131,    8,  260,  212,  263,   10,   11,  314,  268,    5,
   63,  197,  269,    7,  231,  130,  131,    8,  272,  285,
  273,   10,   11,  334,    5,   63,  286,  290,    7,  287,
  293,  173,    8,  294,  299,  201,   10,   11,    5,   63,
  298,  300,    7,  303,  304,  317,    8,  305,  255,  247,
   10,   11,  306,   63,  313,  307,  274,  310,  316,  301,
    8,  320,  342,  275,  276,   11,  321,  322,    5,   63,
    5,   63,    7,  327,    7,  324,    8,  333,    8,  336,
   10,   11,   10,   11,  346,  123,  335,  199,  339,  123,
  341,  348,  345,  201,  349,  350,    5,   63,  123,  123,
    7,  123,  359,  203,    8,   36,   37,  361,   10,   11,
  366,  367,  369,  124,    2,  374,  371,  124,   38,   39,
  226,   40,   12,   13,    5,   63,  124,  124,    7,  124,
  247,  375,    8,  376,  378,  379,   10,   11,  380,  143,
    5,   63,    5,   63,    7,  301,    7,    3,    8,  121,
    8,  327,   10,   11,   10,   11,    5,   63,   22,   89,
    7,   68,   75,  145,    8,  351,    5,   63,   10,   11,
    7,  364,   77,  181,    8,   76,  251,  158,   10,   11,
    5,   63,   73,   88,    7,  372,    0,   49,    8,    0,
  292,   63,   10,   11,  274,    0,    0,    0,    8,    0,
  338,  275,  276,   11,    0,   63,    0,    0,  274,    0,
    0,    0,    8,    0,  358,  275,  276,   11,    5,   63,
    0,    0,    7,    0,    5,   63,    8,    0,    7,    0,
   10,   11,    8,    0,    5,   63,   10,   11,    7,    0,
    0,    0,    8,    0,    0,    0,   10,   11,    0,    0,
    0,    5,   63,    0,    0,    7,    0,    0,    0,    8,
    0,    5,   63,   10,   11,    7,    0,    0,    0,    8,
    0,    0,    0,   10,   11,    0,    0,   63,    0,    0,
  274,    0,  279,   63,    8,    0,  274,  275,  276,   11,
    8,    0,    0,  275,  276,   11,    0,   63,    0,  279,
  274,    0,    0,   63,    8,    0,  274,  275,  276,   11,
    8,    0,    0,  275,  276,   11,  279,   63,   49,   49,
  274,    0,   49,    0,    8,  280,   49,  275,  276,   11,
   49,   49,    0,    0,  302,  279,  279,    0,    0,    0,
    5,   63,  280,    0,    7,    0,    0,  279,    8,  279,
    0,  279,   10,   11,    0,  279,    0,    0,    0,  280,
    0,  279,    0,    0,  279,  279,    0,    0,  279,    0,
    0,  302,    0,    0,    0,  279,    0,  279,  280,  280,
    0,    0,  302,    0,    0,    0,  279,    0,    0,    0,
  280,    0,  280,    0,  280,    0,  302,    0,  280,    0,
  302,    0,    0,  302,  280,    0,    0,  280,  280,    0,
  302,  280,    0,    0,    0,    5,  109,    0,  280,  110,
  280,  302,    0,    8,    9,  111,    0,  112,  113,  280,
    0,    0,    0,   12,   13,   14,   15,    5,  109,    0,
    0,  110,    0,    0,    0,    8,    9,  157,    0,  112,
  113,    0,    0,    0,    0,   12,   13,   14,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
    0,   41,   34,   41,  110,   58,   41,  123,   41,    4,
   45,  123,   40,   42,  123,   41,   41,   91,   50,   40,
   15,   41,   17,   41,   43,  123,   45,    0,  317,   58,
   59,   58,  123,   81,   41,    4,   40,  257,  123,  295,
  123,   60,   61,   62,   44,  123,  257,   62,   17,   43,
   41,   45,   59,   85,   45,   59,   88,  346,  314,   59,
   45,   44,   42,  274,  275,  123,   90,   47,  125,   41,
   44,   59,  328,   45,  330,   40,   59,   72,  334,   43,
    0,   45,   85,   86,   87,   59,   43,  343,   45,   41,
  123,  120,   58,   45,   89,   90,   41,  171,  123,   44,
  356,   41,   42,  123,   45,  123,  257,   47,  132,  260,
   45,   41,  160,  264,   44,  139,  267,  268,  269,  114,
  256,  257,  154,   40,  260,  125,  155,  156,  264,   43,
  266,   45,  268,  269,   40,  130,   41,  132,   43,   45,
   45,  165,  137,   41,  139,  123,   41,   45,   43,  261,
   45,  267,  125,  257,   60,   61,   62,  266,  190,  162,
  163,  257,   60,   61,   62,   59,   44,   59,  274,  267,
  165,  262,  263,   41,   42,   43,  261,   45,  261,   47,
   59,   59,  211,  261,  208,   59,   43,  182,   45,  213,
  185,   59,   60,   61,   62,  210,   41,   42,   59,  256,
  257,   59,   47,  260,  236,  125,   41,  264,   43,   59,
   45,  268,  269,  208,  269,  239,   40,  257,  213,  272,
  252,  259,  257,  258,   59,   60,   61,   62,  261,  257,
  245,  257,  256,  265,  274,  275,  261,  269,  259,  274,
  275,  261,  257,  261,  239,  272,  261,  242,  274,  275,
  257,  270,  271,  248,  273,  123,  256,  257,  290,  258,
  260,  256,   40,  278,  264,  265,  257,  258,  268,  269,
  299,  286,  257,  258,  274,  275,  276,  277,  273,   41,
  295,  313,   40,  256,  257,  257,  258,  260,  123,  274,
  275,  264,  265,  266,  257,  268,  269,  272,  293,  314,
  315,  274,  275,  276,  277,  257,  258,   40,  274,  275,
   41,  326,   43,  328,   45,  330,  257,  258,  350,  334,
   41,  353,  257,  258,   41,  340,  321,   41,  343,  344,
  125,   58,  347,  262,  263,  367,  256,  257,  257,  354,
  260,  356,  274,  275,  264,  265,  266,   44,  268,  269,
  365,  257,  258,    4,  274,  275,  276,  277,   41,  257,
  258,  125,  262,  263,  270,  271,   17,  273,  274,  275,
   94,   95,  270,  271,   41,  273,  274,  275,  256,  257,
   59,   41,  260,   43,   40,   45,  264,  265,  266,  257,
  268,  269,  263,  261,  263,   40,  274,  275,  276,  277,
  123,   40,  270,  271,   59,  273,   57,  257,   58,   41,
  260,   43,   41,   45,  264,   58,  123,  267,  268,  269,
  261,   72,  257,   58,   40,  269,  261,   59,   60,   61,
   62,  123,   41,   45,   59,  270,  271,   41,  273,   43,
   41,   45,   43,  262,   45,  123,   40,   98,   60,   61,
   62,   40,   58,   41,   41,   59,   60,   61,   62,  123,
   41,  256,  257,  114,   59,  260,   41,   42,   59,  264,
  265,  263,   47,  268,  269,  123,   59,  123,  263,  274,
  275,  276,  277,   41,   41,   43,   43,   45,   45,   59,
  263,  123,  256,  257,   40,  146,  260,   59,  149,   58,
  264,  265,  261,  123,  268,  269,   40,   59,   41,   41,
  274,  275,  276,  277,   41,   40,   43,  125,   45,  123,
   41,   41,   43,   43,   45,   45,  177,  178,   41,  180,
   43,  123,   45,  256,  257,   40,   59,  260,   40,  262,
  263,  264,   59,  266,   58,  268,  269,  123,   59,  256,
  257,  125,   40,  260,  205,  262,  263,  264,  125,   59,
   41,  268,  269,  123,  256,  257,   40,   40,  260,   59,
   41,  263,  264,   59,   40,  125,  268,  269,  256,  257,
  257,   59,  260,   59,   59,  261,  264,   59,  266,  125,
  268,  269,   59,  257,   40,   59,  260,   59,   59,  125,
  264,   59,  266,  267,  268,  269,   41,  125,  256,  257,
  256,  257,  260,  125,  260,   59,  264,   41,  264,  125,
  268,  269,  268,  269,  261,  257,   59,  125,   59,  261,
   59,   59,  263,  125,  125,   40,  256,  257,  270,  271,
  260,  273,   59,  125,  264,  257,  258,   59,  268,  269,
   59,   40,   59,  257,    0,  125,   59,  261,  270,  271,
  125,  273,  274,  275,  256,  257,  270,  271,  260,  273,
  125,   59,  264,   59,   59,  125,  268,  269,   59,   59,
  256,  257,  256,  257,  260,  125,  260,    0,  264,   59,
  264,  125,  268,  269,  268,  269,  256,  257,   41,   59,
  260,   19,  263,   98,  264,  125,  256,  257,  268,  269,
  260,  125,  263,  149,  264,  263,  266,  114,  268,  269,
  256,  257,  263,   47,  260,  125,   -1,  125,  264,   -1,
  266,  257,  268,  269,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,  268,  269,   -1,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,  268,  269,  256,  257,
   -1,   -1,  260,   -1,  256,  257,  264,   -1,  260,   -1,
  268,  269,  264,   -1,  256,  257,  268,  269,  260,   -1,
   -1,   -1,  264,   -1,   -1,   -1,  268,  269,   -1,   -1,
   -1,  256,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  256,  257,  268,  269,  260,   -1,   -1,   -1,  264,
   -1,   -1,   -1,  268,  269,   -1,   -1,  257,   -1,   -1,
  260,   -1,  261,  257,  264,   -1,  260,  267,  268,  269,
  264,   -1,   -1,  267,  268,  269,   -1,  257,   -1,  278,
  260,   -1,   -1,  257,  264,   -1,  260,  267,  268,  269,
  264,   -1,   -1,  267,  268,  269,  295,  257,  256,  257,
  260,   -1,  260,   -1,  264,  261,  264,  267,  268,  269,
  268,  269,   -1,   -1,  278,  314,  315,   -1,   -1,   -1,
  256,  257,  278,   -1,  260,   -1,   -1,  326,  264,  328,
   -1,  330,  268,  269,   -1,  334,   -1,   -1,   -1,  295,
   -1,  340,   -1,   -1,  343,  344,   -1,   -1,  347,   -1,
   -1,  315,   -1,   -1,   -1,  354,   -1,  356,  314,  315,
   -1,   -1,  326,   -1,   -1,   -1,  365,   -1,   -1,   -1,
  326,   -1,  328,   -1,  330,   -1,  340,   -1,  334,   -1,
  344,   -1,   -1,  347,  340,   -1,   -1,  343,  344,   -1,
  354,  347,   -1,   -1,   -1,  256,  257,   -1,  354,  260,
  356,  365,   -1,  264,  265,  266,   -1,  268,  269,  365,
   -1,   -1,   -1,  274,  275,  276,  277,  256,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,   -1,  268,
  269,   -1,   -1,   -1,   -1,  274,  275,  276,  277,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","CADENA","IF","THEN","ELSE",
"ENDIF","OUT","FUN","RETURN","BREAK","WHEN","WHILE","MAYOR_IGUAL","MENOR_IGUAL",
"ASIGNACION","DISTINTO","ENTERO","DOUBLE","CONST","DEFER",
};
final static String yyrule[] = {
"$accept : program",
"program : header_program '{' ejecucion '}'",
"program : header_program",
"program : header_program '{' ejecucion",
"program : header_program '{' '}'",
"header_program : ID",
"declaracion_variables : tipo lista_variables ';'",
"declaracion_variables : definicion_constante ';'",
"declaracion_variables : lista_variables ';'",
"declaracion_variables : lista_variables",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"sentencia_declarable : declaracion_variables",
"sentencia_declarable : funcion",
"sentencia_declarable : diferimiento",
"funcion : header_funcion ejecucion_funcion",
"funcion : header_funcion",
"header_funcion : FUN ID '(' lista_parametros ')' ':' tipo",
"header_funcion : FUN ID '(' ')' ':' tipo",
"header_funcion : FUN '(' lista_parametros ')' ':' tipo",
"header_funcion : FUN '(' ')' ':' tipo",
"header_funcion : FUN ID '(' lista_parametros ')' tipo",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"parametro : tipo ID",
"parametro : ID",
"parametro : tipo",
"tipo : ENTERO",
"tipo : DOUBLE",
"ejecucion_funcion : '{' bloque_funcion RETURN '(' expresion ')' ';' '}' ';'",
"ejecucion_funcion : '{' RETURN '(' expresion ')' ';' '}' ';'",
"bloque_funcion : bloque_funcion sentencia_funcion",
"bloque_funcion : sentencia_funcion",
"sentencia_funcion : sentencia",
"sentencia_funcion : seleccion_funcion",
"sentencia_funcion : seleccion_when_funcion",
"sentencia_funcion : iteracion_while_funcion",
"seleccion_funcion : IF condicion_salto_if then_seleccion_funcion ENDIF",
"seleccion_funcion : IF condicion_salto_if then_seleccion_funcion else_seleccion_funcion ENDIF",
"then_seleccion_funcion : THEN '{' ejecucion_control RETURN '(' expresion ')' '}' ';'",
"then_seleccion_funcion : THEN RETURN '(' expresion ')' ';'",
"else_seleccion_funcion : ELSE '{' ejecucion_control RETURN '(' expresion ')' '}' ';'",
"else_seleccion_funcion : ELSE RETURN '(' expresion ')' ';'",
"seleccion_when_funcion : WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control RETURN '(' expresion ')' '}' ';'",
"seleccion_when_funcion : WHEN '(' comparacion_bool ')' THEN RETURN '(' expresion ')' ';'",
"iteracion_while_funcion : WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' '}' ';'",
"iteracion_while_funcion : ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' '}' ';'",
"diferimiento : DEFER sentencia_ejecutable",
"ejecucion_control : ejecucion_control sentencia_ejecutable",
"ejecucion_control : sentencia_ejecutable",
"ejecucion : ejecucion sentencia",
"ejecucion : sentencia",
"sentencia : sentencia_ejecutable",
"sentencia : sentencia_declarable",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : impresion ';'",
"sentencia_ejecutable : seleccion_when ';'",
"sentencia_ejecutable : iteracion_while ';'",
"sentencia_ejecutable : error ';'",
"seleccion_when : WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control '}' ';'",
"seleccion_when : WHEN '(' comparacion_bool ')' THEN sentencia_ejecutable ';'",
"iteracion_while : WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'",
"iteracion_while : WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable ';'",
"iteracion_while : ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'",
"iteracion_while : ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable ';'",
"seleccion_iteracion : IF condicion_salto_if then_seleccion_iteracion ENDIF",
"seleccion_iteracion : IF condicion_salto_if then_seleccion_iteracion else_seleccion_iteracion ENDIF",
"then_seleccion_iteracion : THEN '{' ejecucion_iteracion '}' ';'",
"then_seleccion_iteracion : THEN break ';'",
"else_seleccion_iteracion : ELSE '{' ejecucion_iteracion '}' ';'",
"else_seleccion_iteracion : ELSE RETURN '(' expresion ')' ';'",
"else_seleccion_iteracion : ELSE RETURN '(' ')' ';'",
"else_seleccion_iteracion : ELSE RETURN '(' expresion ')'",
"else_seleccion_iteracion : ELSE RETURN ';'",
"else_seleccion_iteracion : ELSE RETURN",
"else_seleccion_iteracion : ELSE '{' ejecucion_iteracion '}'",
"else_seleccion_iteracion : ELSE '{' ejecucion_iteracion",
"else_seleccion_iteracion : ELSE ejecucion_iteracion ';'",
"seleccion_when_iteracion : WHEN '(' comparacion_bool ')' THEN '{' ejecucion_iteracion '}' ';'",
"seleccion_when_iteracion : WHEN '(' comparacion_bool ')' THEN break ';'",
"ejecucion_iteracion : ejecucion_iteracion sentencia_iteracion",
"ejecucion_iteracion : sentencia_iteracion",
"sentencia_iteracion : asignacion ';'",
"sentencia_iteracion : seleccion_iteracion ';'",
"sentencia_iteracion : impresion ';'",
"sentencia_iteracion : seleccion_when_iteracion ';'",
"sentencia_iteracion : iteracion_while ';'",
"sentencia_iteracion : break ';'",
"break : BREAK",
"break : BREAK ID",
"seleccion : IF condicion_salto_if then_seleccion ENDIF",
"seleccion : IF condicion_salto_if then_seleccion else_seleccion ENDIF",
"seleccion : IF condicion_salto_if '{' ejecucion_control '}' else_seleccion ENDIF",
"seleccion : IF condicion_salto_if then_seleccion '{' ejecucion_control '}' ENDIF",
"seleccion : IF condicion_salto_if THEN ENDIF",
"seleccion : IF condicion_salto_if then_seleccion ELSE ENDIF",
"seleccion : IF condicion_salto_if THEN else_seleccion ENDIF",
"then_seleccion : THEN '{' ejecucion_control '}' ';'",
"then_seleccion : THEN sentencia_ejecutable ';'",
"else_seleccion : ELSE '{' ejecucion_control '}' ';'",
"else_seleccion : ELSE sentencia_ejecutable ';'",
"else_seleccion : ELSE '{' '}' ';'",
"else_seleccion : ELSE ejecucion_control '}' ';'",
"condicion_salto_if : '(' comparacion_bool ')'",
"condicion_salto_if : comparacion_bool ')'",
"condicion_salto_if : '(' comparacion_bool",
"condicion_salto_if : comparacion_bool",
"condicion_salto_if : '(' ')'",
"comparacion_bool : expresion comparador expresion",
"comparacion_bool : expresion comparador",
"comparacion_bool : comparador expresion",
"comparacion_bool : comparador",
"comparador : '>'",
"comparador : '<'",
"comparador : '='",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"lista_asignaciones : lista_asignaciones asignacion",
"lista_asignaciones : asignacion",
"definicion_constante : CONST lista_asignaciones",
"asignacion : ID ASIGNACION expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : tipo '(' expresion '+' termino ')'",
"expresion : tipo '(' expresion '-' termino ')'",
"expresion : tipo '(' termino ')'",
"expresion : tipo '(' expresion '+' ')'",
"expresion : tipo '(' expresion '-' ')'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"combinacion_terminales : ID",
"combinacion_terminales : CTE",
"combinacion_terminales : '-' CTE",
"factor : combinacion_terminales",
"factor : ID '(' combinacion_terminales ',' combinacion_terminales ')'",
"factor : ID '(' combinacion_terminales ')'",
"factor : ID '(' ')'",
"impresion : OUT '(' CADENA ')' ';'",
"impresion : OUT '(' ')'",
"impresion : OUT",
"impresion : OUT CADENA",
};

//#line 415 "gramatica.y"


public static final String ERROR = "Error";
public static final String WARNING = "Warning";

//public static List<Character> buffer = new ArrayList();
public static final List<String> errores_sintacticos = new ArrayList<>();
public static final List<Character> buffer = new ArrayList<>();
public static AnalisisLexico AL;

void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
}

public static void agregarError(List<String> errores, String tipo, String error) {
        if (tipo.equals("ERROR")) {
                errores_compilacion = true;
        }

        int linea_actual = AnalisisLexico.getLineaActual();

        errores.add(tipo + " (Linea " + linea_actual + "): " + error);
}


int yylex() {
    //AnalisisLexico AL = new AnalisisLexico(); // ESTO ESTA MAL ? CADA VEZ QUE ENTRE A YYLEX VA A CREAR UNA TABLA DE SIMBOLOS NUEVA
    int tok = 0;
    AL.estado_actual = 0;
    boolean tieneToken = false;
    while (tieneToken == false) {
        if (!buffer.isEmpty) {
            Character c = buffer.get(0);
            Token t = AL.cambiarEstado(c,buffer);
            if (t != null) {
                tieneToken = true;
                tok = t.getId();
                if (t.getAtributo() != null) {
                    yylval = new ParserVal(t.getAtributo());
                }
            }
        } else {
            tieneToken = true;
        }
    }
    return tok;
}

public Double getDouble(String d){
    if (d.contains("D")){
        var w = d.split("D");
        return Math.pow(Double.valueOf(w[0]),Double.valueOf(w[1]));
    } else {
        return Double.valueOf(d);
    }

}

public String negarConstante(String c) { // AHORA?
    String ptr = TablaSimbolos.obtenerSimbolo(c);
    String nuevo = '-' + c;
    if (c.contains(".")) {
        Double d = getDouble(c);
        if ((d > Math.pow(-1.7976931348623157,308) && d < Math.pow(-2.2250738585072014,-308))){
            TablaSimbolos.negarConstante(ptr,c);
        } else {
            agregarError(errores_sintacticos, "ERROR", "El numero " + constante + " esta fuera de rango.");
            nuevo = "";
        }
    } else {
        agregarError(errores_sintacticos, "WARNING", "El numero " + constante + " fue truncado al valor minimo (0), ya que es menor que este mismo");
        nuevo = "0";
        TablaSimbolos.truncarEntero(ptr,nuevo);
    }
}

public static void main(String[] args) {
    AL = new AnalisisLexico();
    buffer = Compilador.crearBuffer();
}
//#line 751 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 23 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se esperaba un '}' al final del programa");}
break;
case 4:
//#line 24 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se esperaba una sentencia de ejecucion");}
break;
case 8:
//#line 40 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera un tipo para declaracion_variable");}
break;
case 9:
//#line 41 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera un ';' al final de la declaracion");}
break;
case 16:
//#line 59 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una ejecucion_funcion");}
break;
case 19:
//#line 64 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
break;
case 20:
//#line 65 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
break;
case 21:
//#line 67 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera el ':' luego de asignar los parametros");}
break;
case 25:
//#line 78 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera el tipo del parametro");}
break;
case 26:
//#line 79 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera el nombre del parametro");}
break;
case 72:
//#line 262 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que haya una expresion entre los parentesis");}
break;
case 73:
//#line 263 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que haya un ';' luego de la expresion ");}
break;
case 74:
//#line 264 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que luego del return haya una expresion entre parentesis");}
break;
case 75:
//#line 265 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que luego del return haya una expresion entre parentesis y un ';'al final");}
break;
case 76:
//#line 266 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que haya un ';' luego de '}' ");}
break;
case 77:
//#line 267 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que haya una '}' y un ';' ");}
break;
case 78:
//#line 268 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que haya que la ejecucion_iteracion se encuentre entre { }");}
break;
case 93:
//#line 307 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se esperan un THEN");}
break;
case 94:
//#line 308 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera un ELSE")}
break;
case 95:
//#line 309 "gramatica.y"
{agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del THEN");}
break;
case 96:
//#line 310 "gramatica.y"
{agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del ELSE");}
break;
case 97:
//#line 311 "gramatica.y"
{agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del THEN");}
break;
case 102:
//#line 327 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera sentencias dentro del cuerpo del ELSE");}
break;
case 103:
//#line 328 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera un '{' luego del ELSE");}
break;
case 105:
//#line 333 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera '(' al principio de la comparacion");}
break;
case 106:
//#line 334 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera ')' al final de la comparacion_bool");}
break;
case 107:
//#line 335 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera que la comparacion_bool se encuentre entre parentesis");}
break;
case 108:
//#line 336 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una condicion");}
break;
case 110:
//#line 342 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una expresion luego del comparador");}
break;
case 111:
//#line 343 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una expresion antes del comparador");}
break;
case 112:
//#line 344 "gramatica.y"
{agregarError(errores_sintacticos,"Error","se espera expresiones para poder realizar las comparaciones");}
break;
case 126:
//#line 371 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
break;
case 127:
//#line 372 "gramatica.y"
{agregarError(erores_sintacticos,"Error","Conversion explicita no permitida");}
break;
case 128:
//#line 373 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
break;
case 129:
//#line 374 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una termino luego del signo '+' y conversion explicita no permitida");}
break;
case 130:
//#line 375 "gramatica.y"
{agregarError(erroes_sintacticos,"Error","Se espera una termino luego del signo '-' y conversion explicita no permitida");}
break;
case 142:
//#line 408 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera una cadena dentro del OUT");}
break;
case 143:
//#line 409 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera () con una cadena dentro");}
break;
case 144:
//#line 410 "gramatica.y"
{agregarError(errores_sintacticos,"Error","Se espera un que la CADENA se encuentre entre parentesis");}
break;
//#line 1056 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
