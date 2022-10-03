import EtapaLexico.AnalisisLexico;
import EtapaLexico.Tokens.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Compilador {
    private AnalisisLexico lexico = new AnalisisLexico();
    private List<Character> buffer = new ArrayList<Character>();
    Map<Integer,String> tokens = new HashMap<>();

    public Compilador(){
        tokens.put(1,"identificador");
        tokens.put(2, "constante");
        tokens.put(3, "=");
        tokens.put(4, "=:");
        tokens.put(5, "=!");
        tokens.put(6, ">");
        tokens.put(7, ">=");
        tokens.put(8, "<");
        tokens.put(9, "<=");
        tokens.put(10, "cadena");
        tokens.put(11, "+");
        tokens.put(12, "-");
        tokens.put(13, "*");
        tokens.put(14, "/");
        tokens.put(15, "(");
        tokens.put(16, ")");
        tokens.put(17, "{");
        tokens.put(18, "}");
        tokens.put(19, ",");
        tokens.put(20, ";");
        tokens.put(21,"palabra reservada");
    }

    List<Character> crearBuffer() { // ta bom?
        String fileName = "C:\\Users\\Matias\\Desktop\\programa_prueba.txt"; // se podra poner un link de git?
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
        List<Token> t = lexico.leerCodigo(crearBuffer());
        System.out.println(t.toString());
    }
}

