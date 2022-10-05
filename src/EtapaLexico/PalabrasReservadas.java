package EtapaLexico;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PalabrasReservadas {
    private static Map<String,Integer> palabras = new HashMap<>();
//if then else end-if out fun return break when while
    public PalabrasReservadas() {
        palabras = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("if", 260),
                new AbstractMap.SimpleEntry<>("then", 261),
                new AbstractMap.SimpleEntry<>("else", 262),
                new AbstractMap.SimpleEntry<>("end-if", 263),
                new AbstractMap.SimpleEntry<>("out", 264),
                new AbstractMap.SimpleEntry<>("fun", 265),
                new AbstractMap.SimpleEntry<>("return", 266),
                new AbstractMap.SimpleEntry<>("break", 267),
                new AbstractMap.SimpleEntry<>("when", 268),
                new AbstractMap.SimpleEntry<>("while", 269),
                new AbstractMap.SimpleEntry<>("ui8",270),
                new AbstractMap.SimpleEntry<>("f64",271),
                new AbstractMap.SimpleEntry<>("const",272),
                new AbstractMap.SimpleEntry<>("defer",273));
    }

    public static Integer getIdentificador(String s) {
        return palabras.get(s);
    }
}
