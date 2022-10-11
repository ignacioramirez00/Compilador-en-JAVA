import EtapaLexico.AnalisisLexico;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length > 1) {
            Compilador c = new Compilador(args[0],args[1]); // el 0 es para la transicion de estados, y el 1 para las acciones semanticas
            c.ejecutarCompilador(args[2]); // en el 2 esta la direccion del programa
        } else {
            System.out.println("No se encontro el archivo en el parametro.");
        }
    }
}
