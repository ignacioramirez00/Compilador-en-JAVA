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
    public static void agregarAtributo(String s, String atributo, String valor) {
        if (tabla.containsKey(s)) {
            var a = tabla.get(s);
            a.agregarAtributo(atributo,valor);
            tabla.put(s,a);
        }
    }

    public static void truncarEntero(String ptr, String constante) {
        Lexema lexema = new Lexema(Integer.valueOf(constante));
        var a = tabla.get(ptr);
        a.setLexema(lexema);
        tabla.put(ptr,a);
    }
    public static Double getDouble(String d){
        if (d.contains("D")){
            var w = d.split("D");
            return Math.pow(Double.valueOf(w[0]),Double.valueOf(w[1]));
        } else {
            return Double.valueOf(d);
        }
    }
    public static void negarConstante(String s, String constante){
        if (tabla.containsKey(s)) {
            var a = tabla.get(s);
            Lexema nuevo = new Lexema(getDouble(constante));
            a.setLexema(nuevo);
            tabla.put(s,a);
        }
    }
    public static Atributo getAtr(String s) {
        if (tabla.containsKey(s)) {
            return tabla.get(s);
        }
        return null;
    }
    public static boolean isInt(String s){
        if (tabla.containsKey(s)) {
            return (tabla.get(s).lexema.atrEntero != null);
        }
        return false;
    }

}
