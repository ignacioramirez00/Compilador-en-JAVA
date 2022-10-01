package EtapaLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PalabrasReservadas {
    private static Map<String,Integer> palabras = new HashMap<>();
//if then else end-if out fun return break when while
    public PalabrasReservadas() {
        palabras = Map.of(
                "if", 100,
                "then", 101,
                "else", 102,
                "end-if", 103,
                "out", 104,
                "fun", 105,
                "return", 106,
                "break", 107,
                "when", 108,
                "while", 109);
        //palabras.put("ui8",110);
        //palabras.put("f64",111);
    }

    public static Integer getIdentificador(String s) {
        return palabras.get(s);
    }
}
