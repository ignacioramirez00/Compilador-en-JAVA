package EtapaLexico;

import EtapaLexico.Semantica.*;
import EtapaLexico.Tokens.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalisisLexico {
    private PalabrasReservadas palabrasReservadas = new PalabrasReservadas();
    private TablaSimbolos tablaSimbolos = new TablaSimbolos();
    private Integer transicionEstados[][] = new Integer[12][26];
    private List<AccionSemantica> accionesSemanticas[][] = new ArrayList[12][26];
    public static StringBuilder tokenActual = new StringBuilder();
    public static int estadoActual = 0;
    private static int lineaActual = 1;

    private static List<CompilationError> errores = new ArrayList<>();

    public AnalisisLexico(){
        crearTablas();
    }

    public static void setLineaActual(int lineaA) {
        lineaActual = lineaA;
    }
    public static int getLineaActual(){
        return lineaActual;
    }

    public void crearTablas(){
        String fileName = "archivos/datos_estados.txt"; // se podra poner un link de git?
        try {
            int fila = 0;
            int columna = 0;
            int datos = 0;
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                if (datos > 25) {
                    fila = fila+1;
                    columna = 0;
                    datos = 0;
                }
                Integer line = Integer.parseInt(sc.nextLine());
                transicionEstados[fila][columna] = line;
                columna++;
                datos++;
            }
            mostrarMatriz(transicionEstados);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName2 = "archivos/acciones_semanticas.txt"; // se podra poner un link de git?
        try {
            int fila = 0;
            int columna = 0;
            int datos = 0;
            Scanner sc2 = new Scanner(new File(fileName2));
            while (sc2.hasNextLine()) {
                if (datos > 25) {
                    fila = fila+1;
                    columna = 0;
                    datos = 0;
                }
                String line = sc2.nextLine();

                List<AccionSemantica> as = new ArrayList<AccionSemantica>();
                if (line.equals("AS0")) {
                    as.add(new AccionSemantica0());
                }
                if (line.equals("AS1")) {
                    as.add(new AccionSemantica1());
                }
                if (line.equals("AS2")) {
                    as.add(new AccionSemantica2());
                }
                if (line.equals("AS3")) {
                    as.add(new AccionSemantica3());
                }
                if (line.equals("AS4")) {
                    as.add(new AccionSemantica4());
                }
                if (line.equals("AS5")) {
                    as.add(new AccionSemantica5());
                }
                if (line.equals("AS6")) {
                    as.add(new AccionSemantica6());
                }
                if (line.equals("AS7")) {
                    as.add(new AccionSemantica7());
                }
                if (line.equals("AS8")) {
                    as.add(new AccionSemantica8());
                }
                if (line.equals("AS9")) {
                    as.add(new AccionSemantica9());
                }
                if (line.equals("ASE")) {
                    as.add(new AccionSemanticaError());
                }
                if (line.equals("AS2/10")) {
                    as.add(new AccionSemantica2());
                    as.add(new AccionSemantica10());
                }
                if (line.equals("AS3/10")) {
                    as.add(new AccionSemantica3());
                    as.add(new AccionSemantica10());
                }
                if (line.equals("AS4/10")) {
                    as.add(new AccionSemantica4());
                    as.add(new AccionSemantica10());
                }
                if (line.equals("AS7/10")) {
                    as.add(new AccionSemantica7());
                    as.add(new AccionSemantica10());
                }
                accionesSemanticas[fila][columna] = as;
                columna++;
                datos++;
            }
            mostrarMatrizAS(accionesSemanticas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mostrarMatrizAS(List<AccionSemantica> t[][]) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.print(t[i][j].toString() + "   ");
            }
            System.out.println();
        }
    }

    void mostrarMatriz(Integer t[][]) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.print(t[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static void agregarError(String tipo, String mensaje) {
        int linea = AnalisisLexico.getLineaActual();
        CompilationError e = new CompilationError(tipo,mensaje,linea);
        errores.add(e);
    }

    public List<Token> leerCodigo(List<Character> buffer){
        List<Token> tok = new ArrayList<>();
        Token t;
            while (!buffer.isEmpty()) {
                int caracter_actual;
                var c = buffer.get(0);
                switch (buffer.get(0)) {
                    case ' ':
                        caracter_actual = 0;
                        break;
                    case '\t':
                        caracter_actual = 1;
                        break;
                    case '\n':
                        caracter_actual = 2;
                        break;
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        caracter_actual = 3;
                        break;
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
                        caracter_actual = 4; //La D no va.
                        break;
                    case '_':
                        caracter_actual = 5;
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        caracter_actual = 6;
                        break;
                    case '.':
                        caracter_actual = 7;
                        break;
                    case 'D':
                        caracter_actual = 8;
                        break;
                    case '+':
                        caracter_actual = 9;
                        break;
                    case '-':
                        caracter_actual = 10;
                        break;
                    case '/':
                        caracter_actual = 11;
                        break;
                    case '(':
                        caracter_actual = 12;
                        break;
                    case ')':
                        caracter_actual = 13;
                        break;
                    case '{':
                        caracter_actual = 14;
                        break;
                    case '}':
                        caracter_actual = 15;
                        break;
                    case ',':
                        caracter_actual = 16;
                        break;
                    case ';':
                        caracter_actual = 17;
                        break;
                    case '=':
                        caracter_actual = 18;
                        break;
                    case ':':
                        caracter_actual = 19;
                        break;
                    case '!':
                        caracter_actual = 20;
                        break;
                    case '<':
                        caracter_actual = 21;
                        break;
                    case '>':
                        caracter_actual = 22;
                        break;
                    case '*':
                        caracter_actual = 23;
                        break;
                    case '\'':
                        caracter_actual = 24;
                        break;
                    default:
                        caracter_actual = 25;
                        break;
                }
                List<AccionSemantica> accSemanticas = accionesSemanticas[estadoActual][caracter_actual];
                if (accSemanticas.size() == 2) {
                    System.out.println("ENTRO A UNA ACCION SEMANTICA CON DOS");
                    System.out.println(buffer);
                    t = accSemanticas.get(0).ejecutar(buffer.get(0), buffer, tokenActual);
                    accSemanticas.get(1).ejecutar(c, buffer, tokenActual);
                    System.out.println(buffer);
                } else {
                    t = accSemanticas.get(0).ejecutar(buffer.get(0), buffer, tokenActual);
                }
                if (t != null) { //DEVOLVEMOS LISTA DE TOKENS O UN SOLO TOKEN CADA VEZ?
                    estadoActual = 0;
                    tok.add(t);
                    tokenActual = new StringBuilder();
                } else {
                    estadoActual = transicionEstados[estadoActual][caracter_actual];
                }
                if (estadoActual == -1) {
                    estadoActual = 0;
                }
                if (estadoActual == -2) {
                    estadoActual = 0;
                }
            }
        return tok;
    }

    public int yylex(List<Character> buffer){
        boolean tieneToken = false;
        Token t = null;
        int tok = 0;
        while (tieneToken == false) {
            if (!buffer.isEmpty()) {
                int caracter_actual;
                switch (buffer.get(0)) {
                    case ' ':
                        caracter_actual = 0;
                        break;
                    case '\t':
                        caracter_actual = 1;
                        break;
                    case '\n':
                        caracter_actual = 2;
                        break;
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        caracter_actual = 3;
                        break;
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
                        caracter_actual = 4; //La D no va.
                        break;
                    case '_':
                        caracter_actual = 5;
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        caracter_actual = 6;
                        break;
                    case '.':
                        caracter_actual = 7;
                        break;
                    case 'D':
                        caracter_actual = 8;
                        break;
                    case '+':
                        caracter_actual = 9;
                        break;
                    case '-':
                        caracter_actual = 10;
                        break;
                    case '/':
                        caracter_actual = 11;
                        break;
                    case '(':
                        caracter_actual = 12;
                        break;
                    case ')':
                        caracter_actual = 13;
                        break;
                    case '{':
                        caracter_actual = 14;
                        break;
                    case '}':
                        caracter_actual = 15;
                        break;
                    case ',':
                        caracter_actual = 16;
                        break;
                    case ';':
                        caracter_actual = 17;
                        break;
                    case '=':
                        caracter_actual = 18;
                        break;
                    case ':':
                        caracter_actual = 19;
                        break;
                    case '!':
                        caracter_actual = 20;
                        break;
                    case '<':
                        caracter_actual = 21;
                        break;
                    case '>':
                        caracter_actual = 22;
                        break;
                    case '*':
                        caracter_actual = 23;
                        break;
                    case '\'':
                        caracter_actual = 24;
                        break;
                    default:
                        caracter_actual = 25;
                        break;
                }
                List<AccionSemantica> accSemanticas = accionesSemanticas[estadoActual][caracter_actual];
                if (accSemanticas.size() == 2) {
                    t = accSemanticas.get(0).ejecutar(buffer.get(0), buffer, tokenActual);
                    accSemanticas.get(1).ejecutar(buffer.get(0), buffer, tokenActual);
                } else {
                    t = accSemanticas.get(0).ejecutar(buffer.get(0), buffer, tokenActual);
                }
                if (t != null) { //DEVOLVEMOS LISTA DE TOKENS O UN SOLO TOKEN CADA VEZ?
                    estadoActual = 0;
                    tieneToken = true;
                    tok = t.getId();
                } else {
                    estadoActual = transicionEstados[estadoActual][caracter_actual];
                }
            }
        }
        return tok;
    }
}
