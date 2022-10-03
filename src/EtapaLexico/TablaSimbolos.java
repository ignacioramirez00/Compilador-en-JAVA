package EtapaLexico;


import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

public class TablaSimbolos {
    //private static Map<Integer,HashMap<Integer,String>> tabla = new HashMap<>();

    private static Map<String,Atributo> tabla = new HashMap<>();
    //private static Integer cantidadSimbolos = 1;

    public TablaSimbolos() {

    }
    public static void agregarSimbolo(String token, Lexema lexema) {
        Atributo atributo = new Atributo(lexema);
        tabla.put(token,atributo);
        //cantidadSimbolos++;
    }
    public static String obtenerSimbolo(String simbolo){
        if (tabla.containsKey(simbolo)) {
            return simbolo;
        }
        return null;
    }

}
