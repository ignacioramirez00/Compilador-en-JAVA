package EtapaLexico;


import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

public class TablaSimbolos {
    private static Map<Integer,HashMap<Integer,String>> tabla = new HashMap<>();
    private static Integer cantidadSimbolos = 1;

    public TablaSimbolos() {

    }
    public static void agregarSimbolo(Integer token, String simbolo) {
        HashMap<Integer,String> atributo = new HashMap<>();
        atributo.put(token,simbolo);
        tabla.put(cantidadSimbolos,atributo);
        cantidadSimbolos++;
    }
    public static int obtenerSimbolo(String simbolo){
        for (Map.Entry<Integer,HashMap<Integer,String>> entrada: tabla.entrySet()) {
            if (entrada.getValue().containsValue(simbolo)) {
                return entrada.getKey();
            }
        }
        return -1;
    }

}
