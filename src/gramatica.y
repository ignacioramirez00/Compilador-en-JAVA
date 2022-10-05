%{
import EtapaLexico.Semantica.*;
import EtapaLexico.AnalisisLexico;
import EtapaLexico.Token;
import Compilador;
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
        | header_program begin_prog ejecucion {agregarError(errores_sintacticos,"Error","Se esperaba un '}' al final del programa");}
        | header_program begin_prog '}' {agregarError(errores_sintacticos,"Error","Se esperaba una sentencia de ejecucion");}
        | '}' {agregarError(errores_sintacticos,"Error","Se esperaba un programa");}
        | '{' {agregarError(errores_sintacticos,"Error","Se esperaba un programa");}
;


begin_prog: '{'
;

header_program: nombre_programa
    | nombre_programa declaracion_funcion
    | declaracion_funcion {agregarError(errores_sintacticos, Parser.ERROR, "Se esperaba nombre del programa");}

nombre_programa: ID
;



//reglas de declaraciones y bloques de sentencias

//__________________//duda de declaracion ???

declaracion_variables: tipo lista_variables ';'
        | declaracion_variables lista_variables ';'
        | lista_variables ';' {agregarError(errores_sintacticos,"Error","Se espera un tipo para declaracion_variable");}
        | lista_variables {agregarError(errores_sintacticos,"Error","Se espera un ';' al final de la declaracion");}
;

lista_variables: lista_variables ',' ID {



        }
        | ID {

        }
;


declaracion_funcion: declaracion_funcion funcion
        | funcion
;


funcion: header_funcion ejecucion_funcion
        | header_funcion {agregarError(errores_sintacticos,"Error","Se espera una ejecucion_funcion");}
;

header_funcion: FUN ID '(' lista_parametros ')' ':' tipo
        | FUN ID '(' ')' ':' tipo
        | FUN '(' lista_parametros ')' ':' tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
        | FUN '(' ')' ':' tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre de la funcion");}
        | FUN ID '(' lista_parametros ')' ':' {agregarError(errores_sintacticos,"Error","Se espera el tipo del retorno de la funcion");}
        | FUN ID '(' lista_parametros ')' tipo {agregarError(errores_sintacticos,"Error","Se espera el ':' luego de asignar los parametros");}
;


lista_parametros: parametro
        | parametro ',' parametro
        // avisar que el maximo es 2
;

parametro: tipo ID {
               // falta la data





         }
        //ERRORES
        | ID {agregarError(errores_sintacticos,"Error","Se espera el tipo del parametro");}
        | tipo {agregarError(errores_sintacticos,"Error","Se espera el nombre del parametro");}
;

tipo: //faltaria ver el manejo de las tablas
;


ejecucion_funcion: '{' bloque_funcion RETURN '(' expresion ')' ';' '}'
        | '{' RETURN '(' expresion ')' ';' '}'

/*aca va el titan



























*/

;

bloque_funcion: bloque_funcion sentencia_funcion
        | sentencia_funcion
        | bloque_funcion declaracion_funcion sentencia_funcion
        | declaracion_funcion sentencia_funcion
;

sentencia_funcion: sentencia
;


ejecucion: ejecucion sentencia
        | sentencia
;

sentencia: sentencia_ejecutable
;

bloque_sentencias_ejecutables: bloque_sentencias_ejecutables sentencia_ejecutable
                | sentencia_ejecutable
;


sentencia_ejecutable: asignacion ';'
                | seleccion ';'
                | impresion ';'
                | seleccion_when ';'
                | iteracion_while ';'
                | declaracion_variables
                //ERRORES
                | seleccion {agregarError(errores_sintacticos,"Error","Se espera un ';' ");}
                | asignacion {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
                | impresion {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
                | seleccion_when {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
                | iteracion_while {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
                | declaracion_variables {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
                | break {agregarError(errores_sintacticos,"Error","Se espera un ';'");}
;

seleccion_when: WHEN '(' comparacion_bool ')' THEN '{' ejecucion '}'
                | WHEN '(' comparacion_bool ')' THEN sentencia_ejecutable ';'
 // faltan los errores
















;

iteracion_while: WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable ';'
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'
                | ID ':' WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable ';'
                | ID ASIGNACION WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion_iteracion '}' ';'
                | ID ASIGNACION WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' sentencia_ejecutable ';'
 // desarrollando los errores
                | WHILE ':' '(' asignacion ')' '{' ejecucion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una comparacion_bool antes del ':' ");}
                | WHILE '(' comparacion_bool ')' '(' asignacion ')' '{' ejecucion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera ':' luego de la comparacion_bool");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' ejecucion '}' {agregarError(errores_sintacticos,"Error","Se espera un ';' del cierre '}' posterior a la ejecucion");}
                | WHILE '(' comparacion_bool ')' ':' '{' ejecucion '}' ';' {agregarError(errores_sintactico,"Error","Se espera una asignacion luego del ':' ");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' '}' ';' {agregarError(errores_sintactico,"Error","Se espera una ejecucion luego de la ASIGNACION");}
                | WHILE '(' ')' ':' '(' asignacion ')' '{' ejecucion '}' ';' {agregarError(errores_sintactico,"Error","Se espera una comparacion_bool dentro de los '(' ')' ");}
                | WHILE '(' comparacion_bool ')' ':' '(' ')' '{' ejecucion '}' ';' {agregarError(errores_sintactico,"Error","Se espera una asignacion dentro de los '(' ')'  ");}
                | WHILE '(' comparacion_bool ')' ':' '(' asignacion ')' '{' '}' ';' {agregarError(errores_sintacticos,"Error","Se espera una ejecucion");}
;

ejecucion_iteracion : asignacion ';'
                | seleccion ';'
                | impresion ';'
                | seleccion_when ';'
                | iteracion_while ';'
                | declaracion_variables
                | break ';'

break: BREAK {
            apilarBreak();
            agregarToken("");
            agregarToken("#BI");
            }
        | BREAK ID
        | BREAK constante
;

seleccion: IF condicion_salto_if then_seleccion ENDIF {
       //laburar con el polaco lewan
    }
    | IF condicion_salto_if then_seleccion else_seleccion ENDIF {
       // laburar con el polaco lewan
    }
   // ERRORES
    | IF condicion_salto_if '{' ejecucion '}' else_seleccion ENDIF {agregarError(errores_sintacticos,"Error","Se esperan un THEN");}
    | IF condicion_salto_if then_seleccion '{' ejecucion '}' ENDIF {agregarError(errores_sintacticos,"Error","Se espera un ELSE")}
    | IF condicion_salto_if THEN ENDIF {agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del THEN");}
    | IF condicion_salto_if then_seleccion ELSE ENDIF {agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del ELSE");}
    | IF condicion_salto_if THEN else_seleccion ENDIF {agregarError(errores_sintactico,"Error","Se espera bloque de sentencias luego del THEN");}
;


then_seleccion: THEN '{' ejecucion '}' ';' {
        // luburo con la polaca
    }
    | THEN sentencia_ejecutable ';' {
        // laburo con la polaca
    }
    // ERRORES
    | THEN '{' ejecucion {agregarError(errores_sintacticos,"Error","Se espera un '}' al de las sentencias del THEN");}
    | THEN '{' '}' {agregarError(errores_sintacticos,"Error","Se espera sentencias dentro del cuerpo del THEN");}
    | THEN ejecucion '}' {agregarError(errores_sintacticos,"Error","Se espera un '{' para comenzar el THEN");}
    | THEN '{' ejecucion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera un ;");}
    //duda por el ;
;

else_seleccion: ELSE '{' ejecucion '}'
    | ELSE sentencia_ejecutable ';'
  // ERRORES
    | ELSE '{' '}' ';' {agregarError(errores_sintacticos,"Error","Se espera sentencias dentro del cuerpo del ELSE");}
    | ELSE ejecucion '}' ';' {agregarError(errores_sintacticos,"Error","Se espera un '{' luego del ELSE");}
;

condicion_salto_if: '(' comparacion_bool ')'{
       // apilar()
    }
   // ERRORES
    | comparacion_bool ')' {agregarError(errores_sintacticos,"Error","Se espera '(' al principio de la comparacion");}
    | '(' ')' {agregarError(errores_sintacticos,"Error","Se espera una condicion");}
;

comparacion_bool: expresion comparador expresion
    | expresion
    //ERRORES
    | expresion comparador {agregarError(errores_sintacticos,"Error","Se espera una expresion luego del comparador");}
    | comparador expresion {agregarError(errores_sintacticos,"Error","Se espera una expresion antes del comparador");}
;

comparador: '>'
    | '<'
    | '='
    | MAYOR_IGUAL
    | MENOR_IGUAL
    | DISTINTO
;

asignacion: ID ASIGNACION '(' expresion ')'{
        // tabla de simbolos
    }
    | ID ASIGNACION expresion {
        // tabla de simbolos
    }
    // ERRORES
    | ASIGNACION expresion {agregarError(errores_sintacticos,"Error","Se espera un ID en el comienzo de la ASIGNACION");}
    | ID expresion {agregarError(errores_sintacticos,"Error","Se espera la ASIGNACION entre la ID y la expresion");}
    | ID ASIGNACION {agregarError(errores_sintacticos,"Error","Se espera una expresion del lado derecho de la asignacion");}
;

expresion: expresion '+' termino_positivo
    | expresion '-' termino_positivo
    | termino
    // ERRORES
    | tipo '(' expresion '+' termino_positivo ')' {agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
    | tipo '(' expresion '-' termino_positivo ')' {agregarError(erores_sintacticos,"Error","Conversion explicita no permitida");}
    | tipo '(' termino ')' {agregarError(errores_sintacticos,"Error","Conversion explicita no permitida");}
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



factor: ID {
      // laburar con la tabla de simbolos
    }
    | CTE {
        // tabla de simbolos
    }
    | '-' CTE {
        // laburar con la tabla de simbolos
    }
    | ID '(' ID ',' ID ')'{
        // laburar con la tabla de simbolos
    }
    | ID '(' ID ',' constante ')'{
        // laburar con la tabla de simbolos
    }
    | ID '(' constante ',' ID ')'{
        // laburar con la tabla de simbolos
    }
    | ID '(' constante ',' constante ')'{
        // labuarar con la tabla de simbolo
    }
    | ID '(' constante ')'{
        // labuarar con la tabla de simbolo
    }
    | ID '(' ID ')'{
        // labuarar con la tabla de simbolo
    }
    | ID '(' ')'
;

factor_positivo: ID { // lo agregamos aca a lo anterior?
        // laburar con la tabla de simbolo
    }
    | CTE{
        // laburar con la tabla de simbolo
    }
    | ID '(' ID ')'{
        // laburar con la tabla de simbolo
    }
    | ID '(' constante ')'{
        // laburar con la tabld de simbolo
    }
    // ERROR
    //| ID '(' ')' {agregarError(errores_sintacticos,"Error","se espera un parametro");}
    //consulta ya que puede pasar que no haya ningun parametro
;



constante: CTE {
         //agregar atributo a la tabla de simbolo
     }
 	 |'-' CTE {
        //agregar atributo a la tabla de simbolo
     }
;


impresion: OUT'(' CADENA ')'';'{
     // falta agregar el codigo
     }
    | OUT '(' ')' {agregarError(errores_sintacticos,"Error","Se espera una cadena dentro del OUT");}
    | OUT {agregarError(errores_sintacticos,"Error","Se espera () con una cadena dentro");}
;

%%


public static final String ERROR = "Error";
public static final String WARNING = "Warning";

//public static List<Character> buffer = new ArrayList();
public static final List<String> errores_sintacticos = new ArrayList<>();


public static void agregarError(List<String> errores, String tipo, String error) {
        if (tipo.equals("ERROR")) {
                errores_compilacion = true;
        }

        int linea_actual = AnalisisLexico.getLineaActual();

        errores.add(tipo + " (Linea " + linea_actual + "): " + error);
}

int yylex() {
    int tok = 0;
    List<Character> buffer = Compilador.crearBuffer();
    AnalisisLexico.estado_actual = 0;
    boolean tieneToken = false;
    while (tieneToken == false) {
        if (!buffer.isEmpty) {
            Character c = buffer.get(0);
            int caracter = AnalisisLexico.getCaracter();
            Token t = AnalisisLexico.cambiarEstado(c,buffer);
            //yylval?
            if (t != null) {
                tieneToken = true;
                tok = t.getId();
            }
        } else {
            tieneToken = true;
        }
    }
    return tok;
}