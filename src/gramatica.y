%{
import EtapaLexico.Semantica.*;
import EtapaLexico.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK WHEN WHILE MAYOR_IGUAL MENOR_IGUAL ASIGNACION DISTINTO

%start program

%% // declaracion del programa principal

program: header_program begin_prog ejecucion '}'
        | header_program
             // falta errores
;


begin_prog: '{'
;

header_program: nombre_programa
    | nombre_programa declaracion_funcion

nombre_programa: ID
;



//reglas de declaraciones y bloques de sentencias

//__________________//duda de declaracion ???

declaracion_variables: tipo lista_variables ';'
        | declaracion_variables lista_variables ';'
;

lista_variables: lista_variables ',' ID
        | ID
;


declaracion_funcion: declaracion_funcion funcion
        | funcion
;


funcion: header_funcion ejecucion_funcion
;

header_funcion: FUN ID '(' lista_parametros ')' ':' tipo
        | FUN ID '(' ')' ':' tipo

lista_parametros: parametro
        | parametro ',' parametro
        // avisar que el maximo es 2

parametro: tipo ID
        //ERRORES
        | ID
        | tipo
;



tipo: //faltaria ver el manejo de las tablas
;


ejecucion_funcion: '{' bloque_funcion RETURN '(' expresion ')' ';' '}'
        | '{' RETURN '(' expresion ')' ';' '}'


bloque_funcion: bloque_funcion sentencia_funcion
        | sentencia_funcion
;

sentencia_funcion: sentencia
;

parametro: tipo ID
        //ERRORES
        | ID
        | tipo

ejecucion: ejecucion sentencia
        | sentencia
;

sentencia: sentencia_ejecutable
;

bloque_sentencias_ejecutables: bloque_sentencias_ejecutables sentencia_ejecutable
                | sentencia_ejecutable

sentencia_ejecutable: asignacion ';'
                | seleccion ';'
                | impresion ';'
                | seleccion_when ';'
                | iteracion_while ';'
                | declaracion_variables
                //ERRORES
                | seleccion

seleccion_when: WHEN '(' comparacion_bool ')' THEN '{' ejecucion '}'
 //falta agregar errores

iteracion_while: WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion '}'
 //falta errores

break: BREAK {
    apilarBreak();
    agregarToken("");
    agregarToken("#BI");
}
;

seleccion: IF condicion_salto_if then_seleccion ENDIF
    | IF condicion_salto_if then_seleccion else_seleccion ENDIF
   // ERRORES
    | IF condicion_salto_if '{' ejecucion '}' else_seleccion ENDIF
    | IF condicion_salto_if then_seleccion '{' ejecucion ENDIF
    | IF condicion_salto_if THEN ENDIF
    | IF condicion_salto_if then_seleccion ELSE ENDIF
    | IF condicion_salto_if THEN else_seleccion ENDIF
;

then_seleccion: THEN '{' ejecucion '}'
   // ERRORES
    | THEN '{' ejecucion
    | THEN '{' '}'
    | THEN ejecucion '}'
    | THEN '{' ejecucion '}'
;

else_seleccion: ELSE '{' ejecucion '}'
  // ERRORES
    | ELSE '{' '}'
    | ELSE ejecucion '}'
;

condicion_salto_if: '(' comparacion_bool ')'
   // ERRORES
    | comparacion_bool ')'
    | '(' ')'
;

comparacion_bool: expresion comparador expresion
    | expresion
    //ERRORES
    | expresion comparador
    | comparador expresion

comparador: '>'
    | '<'
    | '='
    | MAYOR_IGUAL
    | MENOR_IGUAL
    | DISTINTO
;

asignacion: ID ASIGNACION '(' expresion ')'
    | ID ASIGNACION expresion
    // ERRORES
    | ASIGNACION expresion
    | ID expresion
    | ID ASIGNACION
;

expresion: expresion '+' termino_positivo
    | expresion '-' termino_positivo
    | termino
    // ERRORES
    | tipo '(' expresion '+' termino_positivo ')'
    | tipo '(' expresion '-' termino_positivo ')'
    | tipo '(' termino ')'
    // duda con el tipo de la expresion
;

termino: termino '*' factor
    | termino '/' factor
    | factor
;

termino_positivo: termino_positivo '*' factor
    | termino_positivo '/' factor
    | factor_positivo
;

factor: ID
    | CTE
    | '-' CTE
    | ID '(' ID ')'
    | ID '(' constante ')'
    // ERROR
    | ID '(' ')'
;

factor_positivo: ID
    | CTE
    | ID '(' ID ')'
    | ID '(' constante ')'
    // ERROR
    | ID '(' ')'
;

constante: CTE
 	 |'-' CTE
;

impresion: OUT'(' CADENA ')'';'
    |


%%


public static final String ERROR = "Error";
public static final String WARNING = "Warning";

public static final List<String> errores_sintacticos = new ArrayList<>();


public static void agregarError(List<String> errores, String tipo, String error) {
        if (tipo.equals("ERROR")) {
                errores_compilacion = true;
        }

        int linea_actual = AnalizadorLexico.getLineaActual();

        errores.add(tipo + " (Linea " + linea_actual + "): " + error);
}