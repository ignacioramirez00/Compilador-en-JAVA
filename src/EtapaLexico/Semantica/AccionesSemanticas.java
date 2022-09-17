package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;

import java.util.List;

public interface AccionesSemanticas {

    void ejecutar(List<Character> buffer, TablaSimbolos ts, PalabrasReservadas pr);
    String toString();
}
