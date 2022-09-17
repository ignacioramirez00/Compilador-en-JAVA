package EtapaLexico;

import EtapaLexico.Semantica.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalisisLexico {
    private PalabrasReservadas palabrasReservadas;
    private TablaSimbolos tablaSimbolos;
    private Integer transicionEstados[][] = new Integer[12][26];
    private List<AccionesSemanticas> accionesSemanticas[][] = new ArrayList[12][26];

    public static int estadoActual = 0;
    private static int lineaActual = 1;

    public AnalisisLexico(){

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

                List<AccionesSemanticas> as = new ArrayList<AccionesSemanticas>();
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
                    as.add(new AccionSemantica4());
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

    void mostrarMatrizAS(List<AccionesSemanticas> t[][]) {
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
}
