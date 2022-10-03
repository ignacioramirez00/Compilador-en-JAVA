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
                new AbstractMap.SimpleEntry<>("if", 100),
                new AbstractMap.SimpleEntry<>("then", 101),
                new AbstractMap.SimpleEntry<>("else", 102),
                new AbstractMap.SimpleEntry<>("end-if", 103),
                new AbstractMap.SimpleEntry<>("out", 104),
                new AbstractMap.SimpleEntry<>("fun", 105),
                new AbstractMap.SimpleEntry<>("return", 106),
                new AbstractMap.SimpleEntry<>("break", 107),
                new AbstractMap.SimpleEntry<>("when", 108),
                new AbstractMap.SimpleEntry<>("while", 109),
                new AbstractMap.SimpleEntry<>("ui8",110),
                new AbstractMap.SimpleEntry<>("f64",111));
    }

    public static Integer getIdentificador(String s) {
        return palabras.get(s);
    }
}
