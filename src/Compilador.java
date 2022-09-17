import EtapaLexico.AnalisisLexico;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Compilador {
    private AnalisisLexico lexico;
    private List<Character> buffer = new ArrayList<Character>();

    public Compilador(){

    }
    List<Character> crearBuffer(String txt) { // ta bom?
        String fileName = "txt"; // se podra poner un link de git?
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (int i = 0; i< line.length(); i++) {
                    buffer.add(line.charAt(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}

