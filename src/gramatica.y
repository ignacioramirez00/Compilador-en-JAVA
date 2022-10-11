%{
import EtapaLexico.Semantica.*;
import EtapaLexico.AnalisisLexico;
import EtapaLexico.Lexema;
import EtapaLexico.Tokens.Token;
import EtapaLexico.TablaSimbolos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK WHEN WHILE MAYOR_IGUAL MENOR_IGUAL ASIGNACION DISTINTO ENTERO DOUBLE CONST DEFER

%left '+' '-'
%left '*' '/'

%start program

%% // declaracion del programa principal

program: header_program '{' ejecucion '}' ';' {addEstructura("programa");}
        | header_program {addEstructura("programa sin ejecucion");}
        | header_program '{' ejecucion {agregarError(errores_sintacticos,"Error","Se esperaba un '}' al final del programa");}
        | header_program '{' '}' {agregarError(errores_sintacticos,"Error","Se esperaba una sentencia de ejecucion");}
;

header_program: ID
    //| nombre_programa declaracion_funcion

;

//reglas de declaraciones y bloques de sentencias

declaracion_variables: tipo lista_variables ';'
        | definicion_constante ';'
        //| declaracion_variables lista_variables ';' // raro
        | lista_variables ';' {agregarError(errores_sintacticos,"Error","Se espera un tipo para declaracion_variable");}
        | lista_variables {agregarError(errores_sintacticos,"Error","Se espera un ';' al final de la declaracion");}
;

lista_variables: lista_variables ',' ID
        | ID
;

sentencia_declarable: declaracion_variables {addEstructura("declaracion variables");}
        | funcion {addEstructura("declaracion funcion");}
        | diferimiento {addEstructura("diferimiento");}
;

funcion: header_funcion ejecucion_funcion
        | header_funcion {agregarError(errores_sintacticos,"Error","Se espera una ejecucion_funcion");}
;

header_funcion: FUN ID '(' lista_parametros ')' ':' tipo
        | FUN ID '(' ')' ':' tipo
        | FUN '(' lista_parametros ')' ':' tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
        | FUN '(' ')' ':' tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
        | FUN ID '(' lista_parametros ')' tipo {agregarError(errores_sintacticos,"Error","Se espera el ':' luego de asignar los parametros");}
;


lista_parametros: parametro
        | parametro ',' parametro
        // avisar que el maximo es 2
;

parametro: tipo ID
        //ERRORES
        | ID {agregarError(errores_sintacticos,"Error","Se espera el tipo del parametro");}
        | tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre del parametro");}
;

tipo: ENTERO
    | DOUBLE
    //faltaria ver el manejo de las tablas
;


ejecucion_funcion: '{' bloque_funcion RETURN '(' expresion ')' ';' '}' ';'
        | '{' RETURN '(' expresion ')' ';' '}' ';'
        | '{' bloque_funcion RETURN '(' expresion ')' ';' bloque_funcion '}' ';' {agregarError(errores_sintacticos,"Error", "El RETURN debe ser la ultima sentencia de la funcion");}
;



bloque_funcion: bloque_funcion sentencia_funcion
        | sentencia_funcion
;

sentencia_funcion: sentencia
        | seleccion_funcion ';' {addEstructura("if en funcion");}
        | seleccion_when_funcion {addEstructura("when en funcion");}
        | iteracion_while_funcion {addEstructura("while en funcion");}
;

seleccion_funcion: IF condicion_salto_if then_seleccion_funcion ENDIF
    | IF condicion_salto_if then_seleccion_funcion else_seleccion_funcion ENDIF
    | IF condicion_salto_if then_seleccion else_seleccion_funcion ENDIF
    | IF condicion_salto_if then_seleccion_funcion else_seleccion ENDIF
    // falta los errores
;
then_seleccion_funcion: THEN '{' ejecucion_control RETURN '(' expresion ')' ';' '}' ';'
    | THEN RETURN '(' expresion ')' ';'
;

else_seleccion_funcion: ELSE '{' ejecucion_control RETURN '(' expresion ')' ';' '}' ';'
    | ELSE RETURN '(' expresion ')' ';'
;

seleccion_when_funcion: WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control RETURN '(' expresion ')' ';' '}' ';'
                | WHEN '(' comparacion_bool ')' THEN RETURN '(' expresion ')' ';'
                //ERRORES
                | WHEN '(' comparacion_bool ')' THEN RETURN '(' expresion ')' {agregarError(errores_sintacticos,"Error","Se espera un ';' al final de la expresion");}
                | WHEN '(' comparacion_bool ')' THEN RETURN ';' {agregarError(errores_sintacticos,"Error","Se espera la expresion de retorno");}
                | WHEN '(' comparacion_bool ')' THEN RETURN {agregarError(errores_sintacticos,"Error","Se espera una expresion de retorno y un ';' al final");}
                | WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control RETURN '(' expresion ')' '}' {agregarError(errores_sintacticos,"Error","Se espera un ';' al final de '}'");}
                | WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control RETURN expresion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera que la expresion este entre parentesis");}
                | WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control RETURN expresion '}' {agregarError(errores_sintacticos,"Error","Se espera un ';' al final y que la expresion se encuentre entre parentesis");}
;

iteracion_while_funcion: WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' ';' '}' ';'
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' ';' '}' ';'
                //Errores
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' '}' {agregarError(errores_sintacticos,"Error","Se espera un ';' luego de '}' ");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una expresion luego del RETURN");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN expresion {agregarError(errores_sintacticos,"Error","falta los parentesis en la expresion, falta '}' y un ';'");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN expresion ';'{agregarError(errores_sintacticos,"Error","falta los parentesis en la expresion y un '}' para el cierre");}
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN '(' expresion ')' '}' {agregarError(errores_sintacticos,"Error","Se espera un ';' luego de '}' ");}
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN expresion {agregarError(errores_sintacticos,"Error","falta los parentesis en la expresion, falta '}' y un ';'");}
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion RETURN expresion ';'{agregarError(errores_sintacticos,"Error","falta los parentesis en la expresion y un '}' para el cierre");}
;


diferimiento: DEFER sentencia_ejecutable
;

ejecucion_control: ejecucion_control sentencia_ejecutable
        | sentencia_ejecutable

ejecucion: ejecucion sentencia
        | sentencia
;

sentencia: sentencia_ejecutable
        | sentencia_declarable
;

;
sentencia_ejecutable: asignacion ';' 
                | seleccion ';' {addEstructura("if");}
                | impresion ';' {addEstructura("impresion");}
                | seleccion_when ';' {addEstructura("when");}
                | iteracion_while {addEstructura("while");}
                | error ';' {addEstructura("error");}
;

seleccion_when: WHEN '(' comparacion_bool ')' THEN '{' ejecucion_control '}' ';'
            | WHEN '(' comparacion_bool ')' THEN sentencia_ejecutable
            // me dice que tengo que incorporar en lista de palabras reservadas a la palabra const
            // faltan los errores
            | WHEN '(' comparacion_bool ')' THEN {agregarError(errores_sintacticos,"Error","Se espera una ejecucion y un ';' ");}
            | WHEN '(' ')' THEN '{' ejecucion_control  '}' {agregarError(errores_sintacticos,"Error","Se espera una comparacion_bool dentro de '(' ')' ");}
            | WHEN '(' comparacion_bool ')' '{' ejecucion_control  '}' {agregarError(errores_sintacticos,"Error","Se espera un THEN luego de la comparacion_bool");}
            | WHEN comparacion_bool THEN '{' ejecucion_control  '}' {agregarError(errores_sintacticos,"Error","Se espera que la comparacion_bool se encuentre encerrada con '(' ')' ");}
            | WHEN THEN '{' ejecucion_control  '}' {agregarError(errores_sintacticos,"Error","Se espera una comparacion_bool encerrado entre '(' ')' ");}
;

iteracion_while:  WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable 
                // desarrollando los errores
                | WHILE ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una comparacion_bool antes del ':' ");}
                | WHILE '(' comparacion_bool ')' '(' asignacion ')' '{' ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera ':' luego de la comparacion_bool");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una ejecucion luego de la ASIGNACION");}
                | WHILE '(' ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una comparacion_bool dentro de los '(' ')' ");}
                | WHILE '(' comparacion_bool ')' ':' '(' ')' '{' ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una asignacion dentro de los '(' ')'  ");}
                | WHILE '(' comparacion_bool ')' '(' asignacion ')' sentencia_ejecutable ';' {agregarError(errores_sintacticos,"Error","Se espera un ':' luego de la comparacion_bool");}
                | WHILE '(' comparacion_bool ')' ':' sentencia_ejecutable ';' {agregarError(errores_sintacticos,"Error","Se espera una asignacion luego del ':' ");}
                | WHILE '(' comparacion_bool ')' ':' '(' ')' sentencia_ejecutable ';' {agregarError(errores_sintacticos,"Error","Se espera una asignacion entre los parentesis");}
                | WHILE '(' comparacion_bool ')' ':' asignacion sentencia_ejecutable ';' {agregarError(errores_sintacticos,"Error","Se espera que la asignacion se encuentre entre parentesis");}

;
seleccion_iteracion: IF condicion_salto_if then_seleccion_iteracion ENDIF
    | IF condicion_salto_if then_seleccion_iteracion else_seleccion_iteracion ENDIF
    | IF condicion_salto_if then_seleccion_iteracion {agregarError(errores_sintacticos,"Error","Se espera un ENDIF al final del IF");}
    | IF condicion_salto_if ENDIF {agregarError(errores_sintacticos,"Error","Se espera que haya then_seleccion_iteracion ");}
    | IF then_seleccion_iteracion ENDIF {agregarError(errores_sintacticos,"Error","Se espera que haya una condicion_salto_if");}
    | IF else_seleccion_iteracion ENDIF {agregarError(errores_sintacticos,"Error","Se espera que haya una condicion_salto_if");}


then_seleccion_iteracion: THEN '{' ejecucion_iteracion '}' ';'
    | THEN break ';'
    | THEN ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera que antes de la ejecucucion_iteracion haya una { ");}
    | THEN '{' ejecucion_iteracion ';' {agregarError(errores_sintacticos,"Error","Se espera que luego de la ejecucion_iteracion haya una llave");}
;


else_seleccion_iteracion: ELSE '{' ejecucion_iteracion '}' ';'
    | ELSE break ';'
    // ERRRORES
    | ELSE RETURN '(' ')' ';' {agregarError(errores_sintacticos,"Error","Se espera que haya una expresion entre los parentesis");}
    | ELSE RETURN '(' expresion ')' {agregarError(errores_sintacticos,"Error","Se espera que haya un ';' luego de la expresion ");}
    | ELSE RETURN ';' {agregarError(errores_sintacticos,"Error","Se espera que luego del return haya una expresion entre parentesis");}
    | ELSE RETURN {agregarError(errores_sintacticos,"Error","Se espera que luego del return haya una expresion entre parentesis y un ';'al final");}
    | ELSE '{' ejecucion_iteracion '}' {agregarError(errores_sintacticos,"Error","Se espera que haya un ';' luego de '}' ");}
    | ELSE '{' ejecucion_iteracion {agregarError(errores_sintacticos,"Error","Se espera que haya una '}' y un ';' ");}
    | ELSE ejecucion_iteracion ';' {agregarError(errores_sintacticos,"Error","Se espera que haya que la ejecucion_iteracion se encuentre entre { }");}
;





seleccion_when_iteracion: WHEN '(' comparacion_bool ')' THEN '{' ejecucion_iteracion '}' ';'
                | WHEN '(' comparacion_bool ')' THEN break ';'
                | WHEN '(' comparacion_bool ')' THEN '{' ejecucion_iteracion ';' {agregarError(errores_sintacticos,"Error","Se espera un '}' antes del ';'");}
                | WHEN '(' ')' THEN '{' ejecucion_iteracion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera que haya una comparacion_bool entre los parentesis");}
                | WHEN '(' ')' THEN break ';' {agregarError(errores_sintacticos,"Error","Se espera que haya una comparacion_bool entre parentesis");}
                | WHEN comparacion_bool THEN break ';' {agregarError(errores_sintacticos,"Error","Se espera que la comparacion_bool se encuentre entre parentesis");}
;

ejecucion_iteracion: ejecucion_iteracion sentencia_iteracion
                |sentencia_iteracion
;

sentencia_iteracion: asignacion ';'
                | seleccion_iteracion ';' {addEstructura("if en iteracion");}
                | impresion ';' {addEstructura("impresion");}
                | seleccion_when_iteracion {addEstructura("when en iteracion");}
                | iteracion_while {addEstructura("while");}
                | break ';' {addEstructura("break");}
;

break: BREAK 
    | BREAK ID
;

seleccion: IF condicion_salto_if then_seleccion ENDIF
    | IF condicion_salto_if then_seleccion else_seleccion ENDIF 
   // ERRORES
    | IF condicion_salto_if '{' ejecucion_control '}' else_seleccion ENDIF {agregarError(errores_sintacticos,"Error","Se esperan un THEN");}
    | IF condicion_salto_if then_seleccion '{' ejecucion_control '}' ENDIF {agregarError(errores_sintacticos,"Error","Se espera un ELSE");}
    | IF condicion_salto_if THEN ENDIF {agregarError(errores_sintacticos,"Error","Se espera bloque de sentencias luego del THEN");}
    | IF condicion_salto_if then_seleccion ELSE ENDIF {agregarError(errores_sintacticos,"Error","Se espera bloque de sentencias luego del ELSE");}
    | IF condicion_salto_if THEN else_seleccion ENDIF {agregarError(errores_sintacticos,"Error","Se espera bloque de sentencias luego del THEN");}
;


then_seleccion: THEN '{' ejecucion_control '}' ';'
    | THEN sentencia_ejecutable
    // ERRORES
    | THEN '{' ejecucion_control {agregarError(errores_sintacticos,"Error","Se espera un '}' al de las sentencias del THEN");}
    | THEN '{' '}' {agregarError(errores_sintacticos,"Error","Se espera sentencias dentro del cuerpo del THEN");}
    | THEN ejecucion_control '}' {agregarError(errores_sintacticos,"Error","Se espera un '{' para comenzar el THEN");}
;

else_seleccion: ELSE '{' ejecucion_control '}' ';'
    | ELSE sentencia_ejecutable
  // ERRORES
    | ELSE '{' '}' ';' {agregarError(errores_sintacticos,"Error","Se espera sentencias dentro del cuerpo del ELSE");}
    | ELSE ejecucion_control'}' ';' {agregarError(errores_sintacticos,"Error","Se espera un '{' luego del ELSE");}
;

condicion_salto_if: '(' comparacion_bool ')'
   // ERRORES
    | comparacion_bool ')' {agregarError(errores_sintacticos,"Error","Se espera '(' al principio de la comparacion");}
    | '(' comparacion_bool {agregarError(errores_sintacticos,"Error","Se espera ')' al final de la comparacion_bool");}
    | comparacion_bool {agregarError(errores_sintacticos,"Error","Se espera que la comparacion_bool se encuentre entre parentesis");}
    | '(' ')' {agregarError(errores_sintacticos,"Error","Se espera una condicion");}
;

comparacion_bool: expresion comparador expresion {addEstructura("comparacion");}
    //| expresion // raro
    //ERRORES
    | expresion comparador {agregarError(errores_sintacticos,"Error","Se espera una expresion luego del comparador");}
    | comparador expresion {agregarError(errores_sintacticos,"Error","Se espera una expresion antes del comparador");}
    | comparador {agregarError(errores_sintacticos,"Error","se espera expresiones para poder realizar las comparaciones");}
;

comparador: '>'
    | '<'
    | '='
    | MAYOR_IGUAL
    | MENOR_IGUAL
    | DISTINTO
;

lista_asignaciones: lista_asignaciones asignacion
    | asignacion
;
definicion_constante: CONST lista_asignaciones
;

asignacion: ID ASIGNACION expresion {addEstructura($1.sval + " asignacion " + $3.sval);}
;

expresion: expresion '+' termino
    | expresion '-' termino
    | termino
    // ERRORES
    | tipo '(' expresion '+' termino ')' {agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
    | tipo '(' expresion '-' termino ')' {agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
    | tipo '(' termino ')' {agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
    | tipo '(' expresion '+' ')' {agregarError(errores_sintacticos,"Error","Se espera una termino luego del signo '+' y conversion explicita no permitida");}
    | tipo '(' expresion '-' ')' {agregarError(errores_sintacticos,"Error","Se espera una termino luego del signo '-' y conversion explicita no permitida");}
    // duda con el tipo de la expresion
;

termino: termino '*' factor
    | termino '/' factor
    | factor
;

combinacion_terminales : ID
    | CTE
 	|'-' CTE {
            String ptr = TablaSimbolos.obtenerSimbolo($2.sval);
            negarConstante($2.sval);
    }
;

factor: combinacion_terminales
    | ID '(' combinacion_terminales ',' combinacion_terminales ')'
    | ID '(' combinacion_terminales ')'
    | ID '(' ')'
;



impresion: OUT'(' CADENA ')'
    | OUT '(' ')' {agregarError(errores_sintacticos,"Error","Se espera una cadena dentro del OUT");}
    | OUT {agregarError(errores_sintacticos,"Error","Se espera () con una cadena dentro");}
    | OUT CADENA {agregarError(errores_sintacticos,"Error","Se espera un que la CADENA se encuentre entre parentesis");}
;


%%


public static final String ERROR = "Error";
public static final String WARNING = "Warning";

public static List<String> errores_sintacticos = new ArrayList<>();
public static List<Character> buffer = new ArrayList<>();
public static List<String> estructura = new ArrayList<>();
public static AnalisisLexico AL;
public static boolean errores_compilacion = false;

void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
}
public void addEstructura(String s){
    estructura.add(s);
}

public List<String> getEstructura() {
    return estructura;
}

public List<String> getErrores() {
    return errores_sintacticos;
}

public static void agregarEstructura(String s){
    estructura.add(s);
}

public static void agregarError(List<String> errores, String tipo, String error) {
        if (tipo.equals("ERROR")) {
                errores_compilacion = true;
        }

        int linea_actual = AnalisisLexico.getLineaActual();

        errores.add(tipo + " (Linea " + linea_actual + "): " + error);
}


int yylex() {
    int tok = 0;
    //System.out.print("YYLEX, " + buffer.get(0) + " - ");
    Token t = AL.getToken(buffer);
    if (t != null) {
        if (t.getId() == 0) {
                return 0;
        }
        tok = t.getId();
        if (t.getAtributo() != null) {
            yylval = new ParserVal(t.getAtributo());
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
            if (TablaSimbolos.obtenerSimbolo(nuevo) == null){ // si no es null es porque ya se agrego anteriormente la misma constante.
                Lexema lexema = new Lexema(d);
                TablaSimbolos.agregarSimbolo(nuevo,lexema); // falta chequear que no se este usando la misma constante positiva y asi borrarla.
            }
        } else {
            agregarError(errores_sintacticos, "ERROR", "El numero " + c + " esta fuera de rango.");
            nuevo = "";
        }
    } else {
        agregarError(errores_sintacticos, "WARNING", "El numero " + c + " fue truncado al valor minimo (0), ya que es menor que este mismo");
        nuevo = "0";
        TablaSimbolos.truncarEntero(ptr,nuevo);
    }
    return nuevo;
}

public void setSintactico(List<Character> buffer, AnalisisLexico AL) {
    this.AL = AL;
    this.buffer = buffer;
}
