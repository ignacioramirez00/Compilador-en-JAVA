%{
import EtapaLexico.Semantica.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK WHEN WHILE MAYOR_IGUAL MENOR_IGUAL ASIGNACION DISTINTO

%start program

%%
program :




seleccion_when: WHEN '(' comparacion_bool ')' THEN '{' ejecucion '}'
 //falta agregar errores

seleccion_while: WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion '}'
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


%%
