import EtapaLexico.AnalisisLexico;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Compilador {
    private AnalisisLexico lexico;
    private List<Character> buffer = new ArrayList<Character>();
    Map<Integer,String> tokens = new HashMap<>();

    public Compilador(String datos_estados, String acciones_semanticas){
        lexico = new AnalisisLexico(datos_estados,acciones_semanticas);
        tokens.put(257,"identificador");
        tokens.put(258, "constante");
        tokens.put(61, "=");
        tokens.put(272, "=:");
        tokens.put(273, "=!");
        tokens.put(62, ">");
        tokens.put(270, ">=");
        tokens.put(60, "<");
        tokens.put(271, "<=");
        tokens.put(259, "cadena");
        tokens.put(43, "+");
        tokens.put(45, "-");
        tokens.put(42, "*");
        tokens.put(47, "/");
        tokens.put(40, "(");
        tokens.put(41, ")");
        tokens.put(123, "{");
        tokens.put(125, "}");
        tokens.put(44, ",");
        tokens.put(59, ";");
        tokens.put(58, ":");
        tokens.put(21,"palabra reservada");
    }

    public List<Character> crearBuffer(String filePath) { // ta bom?
        String fileName = filePath; // se podra poner un link de git?
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (int i = 0; i< line.length(); i++) {
                    buffer.add(line.charAt(i));
                }
                buffer.add('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    void ejecutarLexico(){
        List<Token> t = lexico.leerCodigo(crearBuffer("C:\\Users\\Matias\\Desktop\\programa_prueba.txt"));
        System.out.println(t.toString());
    }
    void ejecutarCompilador(String s){
        buffer = crearBuffer(s);
        Parser p = new Parser();
        p.setSintactico(buffer,lexico);
        p.yyparse();
        List<String> estructura = p.getEstructura();
        List<String> errores_sintacticos = p.getErrores();
        List<String> errores_lexicos = lexico.getErrores();
        List<Token> tokens = lexico.getTokens();
        System.out.println("Tokens: " + tokens);
        System.out.println("Estructura: " + estructura);
        System.out.println("Errores lexicos: " + errores_lexicos);
        System.out.println("Errores sintacticos: " + errores_sintacticos);
    }
}

