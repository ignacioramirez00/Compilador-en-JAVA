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
    0,    0,    1,    3,    3,    5,    5,    7,    7,    7,
    8,   10,   10,   12,   12,   13,    4,    4,   11,   11,
   14,   14,   16,   16,   16,   16,   18,   18,   22,   22,
   23,   23,   19,   19,   20,   20,    9,   24,   24,    2,
    2,   17,   17,   28,   28,   28,   28,   28,   28,   31,
   31,   32,   32,   32,   32,   33,   33,   34,   34,   35,
   35,   37,   37,   27,   27,   38,   38,   38,   38,   38,
   38,   36,   36,   29,   29,   39,   39,   40,   40,   21,
   25,   41,   41,   41,   41,   41,   41,   42,   42,    6,
   26,   15,   15,   15,   43,   43,   43,   45,   45,   45,
   44,   44,   44,   44,   30,
};
final static short yylen[] = {                            2,
    4,    1,    1,    3,    2,    3,    1,    1,    1,    1,
    2,    7,    6,    1,    3,    2,    1,    1,    9,    8,
    2,    1,    1,    1,    1,    1,    4,    5,    9,    6,
    9,    6,   13,   10,   16,   18,    2,    2,    1,    2,
    1,    1,    1,    2,    2,    2,    2,    2,    2,    9,
    7,   12,   10,   14,   12,    4,    5,    5,    3,    5,
    6,    9,    7,    2,    1,    2,    2,    2,    2,    2,
    2,    1,    2,    4,    5,    5,    3,    5,    3,    3,
    3,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    2,
    1,    6,    4,    3,    5,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   17,   18,    0,    0,    0,    8,    0,    0,   43,
    9,   10,    0,   41,    0,   42,    0,    0,    0,    0,
   49,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   89,    0,   37,    1,   40,    7,    0,    5,    0,   11,
   44,   45,   46,   47,   48,    0,   99,    0,    0,    0,
   97,  101,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    4,    0,    0,    0,    0,    0,    0,    0,
   22,   23,   24,   25,   26,    0,  100,    0,    0,    0,
    0,    0,   85,   86,   87,   82,   83,   84,    0,   80,
    0,    0,    0,   74,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    0,    0,    0,    0,    0,   21,
   98,  104,    0,    0,    0,   95,   96,    0,    0,    0,
   39,   77,    0,    0,   75,  105,    0,   16,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  103,    0,    0,   38,    0,   79,   13,    0,   15,    0,
    0,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,   76,    0,   12,    0,   51,    0,
    0,    0,    0,    0,    0,   28,    0,    0,    0,    0,
  102,    0,   78,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   50,    0,    0,    0,
    0,    0,    0,    0,   20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,   53,    0,   30,    0,    0,    0,    0,    0,    0,
   19,    0,    0,    0,   73,    0,   66,    0,   64,   68,
   70,   67,   71,   69,    0,    0,   32,    0,    0,    0,
    0,    0,   55,    0,    0,    0,   52,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,   56,    0,    0,
    0,   29,    0,    0,    0,   54,    0,   59,    0,    0,
   57,    0,    0,   31,    0,    0,    0,    0,    0,    0,
    0,    0,   33,    0,   58,    0,    0,    0,   63,    0,
    0,    0,   60,    0,    0,    0,   61,   62,    0,   35,
    0,   36,
};
final static short yydgoto[] = {                          2,
    3,   16,   17,   18,   47,   19,   20,   21,   22,   23,
   50,  109,  110,   80,   64,   81,   82,   83,   84,   85,
   35,  145,  168,  130,   65,   25,  225,  131,   27,   28,
   29,   30,  228,  265,  279,  229,  230,  231,   67,  105,
   99,   42,   60,   61,   62,
};
final static short yysindex[] = {                      -249,
    0,    0,  -38,  327,   29,  -55,   55,   62, -164,   72,
   73,    0,    0, -135, -212, -110,    0, -131,   77,    0,
    0,    0,   11,    0,   78,    0,   94,   98,  101,  112,
    0,  -26,  -96,  -26,  -87,  -82,  140,  -26,  -26,  -89,
    0, -135,    0,    0,    0,    0,  -10,    0, -136,    0,
    0,    0,    0,    0,    0,  145,    0,  -66,   10,   19,
    0,    0,  153,  -31,  161,   75, -220,  162,  -40,  168,
  171,    0,    0,  -52,  -45,   55,  173,  175,  176,  270,
    0,    0,    0,    0,    0,  -35,    0,  -26,  -26,  -26,
  -26,  -26,    0,    0,    0,    0,    0,    0,  -26,    0,
 -212,  167,   81,    0,  -39,  170,  199,  -24,  205,  218,
    2,  206,    0,   -4,    9,  -26,  -26,  -26,  235,    0,
    0,    0,   24,   19,   19,    0,    0,  230,   10,  188,
    0,    0, -212,  219,    0,    0, -211,    0,  223, -211,
   91,  242,  243,  -69, -192,   51,  236,  251,  -26,  -20,
    0,  226,  232,    0,  198,    0,    0, -211,    0, -212,
  234, -135,  -26,  257, -212,  -91,    0,   37,  249,   40,
  253,   82,  279,  281,    0,  266,    0,  204,    0,  287,
  289,  -26,  147,  293, -212,    0,  209,   58,  296,  299,
    0, -135,    0,  304,   97,  288,  127,  312,  -26,  295,
  305,  330, -212, -135,  274,  333,    0,  352,  309,  362,
  336,  -26,  165,  368,    0,  -26,  164,  371,  350,  120,
   55,  157,  377,  359,  217,  360,  363,  367,  370,  387,
    0,    0, -135,    0,  210,  388,  -26,  213,  396,  122,
    0,  352,  390,  189,    0,  -26,    0,  392,    0,    0,
    0,    0,    0,    0,  412,  334,    0,  328,  406,  -26,
  352,  231,    0, -116, -189,  428,    0,  137,  411,  346,
    0,  342,   38,  417,  352,  419,  -90,    0,  216,  222,
  352,    0,  421,  364,  447,    0,  237,    0,  450,  352,
    0, -105,  174,    0,  434,  -26,  444,  -26,  250,  352,
  449,  472,    0,  355,    0,  382,  463,  256,    0,  -26,
  408,  478,    0,  482,  394,  484,    0,    0,  425,    0,
  494,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  531,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  497,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -21,    0,    0,  -32,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  517,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   46,    0,    0,    0,  521,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  506,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,  -23,    0,    0,    0,    0,    0,    0,
    0,    0,  430,    0,  -30,  491,   43,    0,    0,    0,
  -72,    0,    0,  207,   -2,  -14,  221,    1,    0,  267,
    0,  307,    0,    0,    0, -241,    0, -190,    0,    0,
    0,    0,   22,   53,  -75,
};
final static int YYTABLESIZE=621;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
  107,   59,   33,  115,   26,  122,  275,    1,   91,   58,
  123,   88,  114,   89,   44,   43,   26,  300,   58,   98,
   98,   98,  276,   98,   58,   98,   91,   72,   97,   98,
   96,  185,  290,   74,  249,   70,   71,   98,   98,   98,
   98,  103,  104,    5,    6,  108,   24,    7,   73,   26,
  301,    8,   88,  165,   89,   10,   11,   94,   45,   94,
   90,   94,   12,   13,  151,   91,  102,  150,  129,  166,
  167,  249,  277,  278,  173,   94,   94,   94,   94,   92,
   26,   92,  249,   92,    4,  146,   93,   31,   93,  128,
   93,  169,   37,   88,   34,   89,  249,   92,   92,   92,
   92,   36,  249,  134,   93,   93,   93,   93,  249,  124,
  125,   38,   39,  157,  147,  148,  108,  249,  172,    5,
   75,   40,  190,   76,   88,   46,   89,    8,    9,   77,
  154,   78,   79,   49,  177,   48,   51,   12,   13,   14,
   15,  161,  126,  127,  102,    5,    6,  180,  244,    7,
  222,  197,   52,    8,    9,  154,   53,   10,   11,   54,
  181,  222,  248,   12,   13,   14,   15,  211,  213,   88,
   55,   89,   63,   66,  184,  289,   68,  206,  154,   69,
  203,  235,   32,  154,   86,  238,    5,    6,  161,  218,
    7,   87,   92,  224,    8,  209,  164,  101,   10,   11,
  154,  100,  106,  133,  113,  236,  258,   88,  111,   89,
  224,  112,  116,  160,  117,  118,   32,  154,  255,  208,
  243,  121,   57,  135,   91,  132,   32,  224,  136,  272,
   56,   57,  138,   12,   13,   98,  121,   57,   93,   94,
  209,   95,  242,  266,  261,  139,  224,  224,   98,   98,
  256,   98,   88,  259,   89,   88,  137,   89,  224,  281,
  224,  140,  141,  142,  143,  304,  224,  306,  243,  144,
  152,  153,  224,   94,  149,  224,  170,  156,  224,  315,
  158,  162,  163,  174,  224,  224,   94,   94,  194,   94,
  175,  171,  179,  224,    6,   92,  182,  221,  274,  186,
  188,    8,   93,  285,  222,  223,   11,  187,   92,   92,
  189,   92,  153,    5,    6,   93,   93,    7,   93,  191,
  192,    8,  176,  202,  193,   10,   11,  195,  194,  196,
    5,    6,  199,  201,    7,  204,    5,    6,    8,  155,
    7,  248,   10,   11,    8,  210,    5,    6,   10,   11,
    7,  212,    5,    6,    8,  274,    7,  205,   10,   11,
    8,  297,  207,  215,   10,   11,  178,  232,  270,  216,
   88,  183,   89,  220,  307,    5,    6,    5,    6,    7,
  314,    7,  284,    8,   88,    8,   89,   10,   11,   10,
   11,  200,    5,    6,  234,  311,    7,   88,  219,   89,
    8,  233,    5,    6,   10,   11,    7,  237,  241,  217,
    8,  240,  198,  245,   10,   11,  246,  247,  250,    5,
    6,  251,  312,    7,   88,  252,   89,    8,  253,  239,
    6,   10,   11,  221,  319,  260,   88,    8,   89,  302,
  222,  223,   11,    5,    6,  254,  257,    7,  263,  264,
  267,    8,  268,    5,    6,   10,   11,    7,  269,    5,
    6,    8,  262,    7,  271,   10,   11,    8,  280,  282,
  283,   10,   11,    6,  226,  286,  221,  288,  291,  294,
    8,  273,  292,  222,  223,   11,  296,    6,  295,  298,
  221,  226,  303,    6,    8,  287,  221,  222,  223,   11,
    8,  293,  305,  222,  223,   11,    6,  309,  226,  221,
  299,  310,    6,    8,  227,  221,  222,  223,   11,    8,
  308,  313,  222,  223,   11,    5,   75,  226,  226,   76,
    2,  227,  316,    8,    9,  119,  317,   78,   79,  226,
  318,  226,  320,   12,   13,   14,   15,  226,  227,  321,
    5,    6,  322,  226,    7,   90,  226,   14,    8,  226,
  214,   81,   10,   11,   72,  226,  226,  227,  227,  159,
  120,    0,    0,    0,  226,    0,    0,    0,    0,  227,
    0,  227,    5,    6,    0,    0,    7,  227,    0,    0,
    8,    9,    0,  227,   10,   11,  227,    0,    0,  227,
   12,   13,   14,   15,    0,  227,  227,    0,    6,    0,
    0,  221,    0,    0,  227,    8,    0,    0,  222,  223,
   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
   41,   32,   58,   76,    4,   41,  123,  257,   41,   45,
   86,   43,   58,   45,  125,   15,   16,  123,   45,   41,
   42,   43,  264,   45,   45,   47,   59,   42,   60,   61,
   62,  123,  123,   44,  225,   38,   39,   59,   60,   61,
   62,  262,  263,  256,  257,   69,    4,  260,   59,   49,
  292,  264,   43,  123,   45,  268,  269,   41,   16,   43,
   42,   45,  274,  275,   41,   47,   66,   44,   99,  262,
  263,  262,  262,  263,  150,   59,   60,   61,   62,   41,
   80,   43,  273,   45,  123,  116,   41,   59,   43,   92,
   45,   41,  257,   43,   40,   45,  287,   59,   60,   61,
   62,   40,  293,  103,   59,   60,   61,   62,  299,   88,
   89,   40,   40,  137,  117,  118,  140,  308,  149,  256,
  257,  257,   41,  260,   43,  257,   45,  264,  265,  266,
  130,  268,  269,  123,  158,   59,   59,  274,  275,  276,
  277,  141,   90,   91,  144,  256,  257,  162,  221,  260,
  267,  182,   59,  264,  265,  155,   59,  268,  269,   59,
  163,  267,  125,  274,  275,  276,  277,   41,  199,   43,
   59,   45,  269,  261,  266,  266,  259,  192,  178,   40,
  123,  212,  272,  183,   40,  216,  256,  257,  188,  204,
  260,  258,   40,  208,  264,  195,  266,  123,  268,  269,
  200,   41,   41,  123,  257,   41,  237,   43,   41,   45,
  225,   41,   40,  123,   40,   40,  272,  217,  233,  123,
  220,  257,  258,  263,  257,   59,  272,  242,   59,  260,
  257,  258,  257,  274,  275,  257,  257,  258,  270,  271,
  240,  273,  123,  246,  123,   41,  261,  262,  270,  271,
   41,  273,   43,   41,   45,   43,   58,   45,  273,  123,
  275,   44,  261,   58,  269,  296,  281,  298,  268,  261,
   41,  125,  287,  257,   40,  290,   41,   59,  293,  310,
   58,   40,   40,   58,  299,  300,  270,  271,  125,  273,
   59,   41,   59,  308,  257,  257,   40,  260,  125,  263,
  261,  264,  257,  266,  267,  268,  269,   59,  270,  271,
   58,  273,  125,  256,  257,  270,  271,  260,  273,   41,
   40,  264,  125,  266,   59,  268,  269,   41,  125,   41,
  256,  257,   40,  125,  260,   40,  256,  257,  264,  133,
  260,  125,  268,  269,  264,   58,  256,  257,  268,  269,
  260,   40,  256,  257,  264,  125,  260,   59,  268,  269,
  264,  125,   59,   59,  268,  269,  160,   59,   41,   40,
   43,  165,   45,   41,  125,  256,  257,  256,  257,  260,
  125,  260,   41,  264,   43,  264,   45,  268,  269,  268,
  269,  185,  256,  257,   59,   41,  260,   43,  125,   45,
  264,   40,  256,  257,  268,  269,  260,   40,   59,  203,
  264,   41,  266,  257,  268,  269,   40,   59,   59,  256,
  257,   59,   41,  260,   43,   59,   45,  264,   59,  266,
  257,  268,  269,  260,   41,   40,   43,  264,   45,  266,
  267,  268,  269,  256,  257,   59,   59,  260,   59,  261,
   59,  264,   41,  256,  257,  268,  269,  260,  125,  256,
  257,  264,  242,  260,   59,  268,  269,  264,   41,   59,
  125,  268,  269,  257,  208,   59,  260,   59,  263,   59,
  264,  261,  261,  267,  268,  269,   40,  257,  125,   40,
  260,  225,   59,  257,  264,  275,  260,  267,  268,  269,
  264,  281,   59,  267,  268,  269,  257,   59,  242,  260,
  290,   40,  257,  264,  208,  260,  267,  268,  269,  264,
  300,   59,  267,  268,  269,  256,  257,  261,  262,  260,
    0,  225,  125,  264,  265,  266,   59,  268,  269,  273,
   59,  275,   59,  274,  275,  276,  277,  281,  242,  125,
  256,  257,   59,  287,  260,   59,  290,   41,  264,  293,
  266,   41,  268,  269,   59,  299,  300,  261,  262,  140,
   80,   -1,   -1,   -1,  308,   -1,   -1,   -1,   -1,  273,
   -1,  275,  256,  257,   -1,   -1,  260,  281,   -1,   -1,
  264,  265,   -1,  287,  268,  269,  290,   -1,   -1,  293,
  274,  275,  276,  277,   -1,  299,  300,   -1,  257,   -1,
   -1,  260,   -1,   -1,  308,  264,   -1,   -1,  267,  268,
  269,
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
"header_program : ID",
"declaracion_variables : tipo lista_variables ';'",
"declaracion_variables : definicion_constante ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"sentencia_declarable : declaracion_variables",
"sentencia_declarable : funcion",
"sentencia_declarable : diferimiento",
"funcion : header_funcion ejecucion_funcion",
"header_funcion : FUN ID '(' lista_parametros ')' ':' tipo",
"header_funcion : FUN ID '(' ')' ':' tipo",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"parametro : tipo ID",
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
"then_seleccion : THEN '{' ejecucion_control '}' ';'",
"then_seleccion : THEN sentencia_ejecutable ';'",
"else_seleccion : ELSE '{' ejecucion_control '}' ';'",
"else_seleccion : ELSE sentencia_ejecutable ';'",
"condicion_salto_if : '(' comparacion_bool ')'",
"comparacion_bool : expresion comparador expresion",
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
};

//#line 417 "gramatica.y"


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
//#line 615 "Parser.java"
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
