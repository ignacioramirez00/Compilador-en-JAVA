package EtapaLexico;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PalabrasReservadas {
    private static Map<String,Integer> palabras = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("if", 260),
            new AbstractMap.SimpleEntry<>("then", 261),
            new AbstractMap.SimpleEntry<>("else", 262),
            new AbstractMap.SimpleEntry<>("end_if", 263),
            new AbstractMap.SimpleEntry<>("out", 264),
            new AbstractMap.SimpleEntry<>("fun", 265),
            new AbstractMap.SimpleEntry<>("return", 266),
            new AbstractMap.SimpleEntry<>("break", 267),
            new AbstractMap.SimpleEntry<>("when", 268),
            new AbstractMap.SimpleEntry<>("while", 269),
            new AbstractMap.SimpleEntry<>("ui8",274),
            new AbstractMap.SimpleEntry<>("f64",275),
            new AbstractMap.SimpleEntry<>("const",276),
            new AbstractMap.SimpleEntry<>("defer",277));
//if then else end-if out fun return break when while

    public static Integer getIdentificador(String s) {
        if (palabras.containsKey(s))
            return palabras.get(s);
        return null;
    }
}
