import EtapaLexico.AnalisisLexico;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Compilador c = new Compilador();
        c.ejecutarCompilador("ejemplo_programas/program_3.txt");
    }
}
