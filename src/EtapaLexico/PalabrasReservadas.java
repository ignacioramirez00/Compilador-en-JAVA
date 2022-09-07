package EtapaLexico;

import java.util.ArrayList;
import java.util.Map;

public class PalabrasReservadas {
    Map<Integer,String> palabras; //when, while, if, then, else, end-if, out, fun, return, break

    public PalabrasReservadas() {
        palabras = Map.of(
                1, "when",
                2, "while",
                3, "if",
                4, "then",
                5, "else",
                6, "end-if",
                7, "out",
                8, "fun",
                9, "return",
                10, "break");
        palabras.put(11, "ui8");
        palabras.put(12, "f64");
    }
}
