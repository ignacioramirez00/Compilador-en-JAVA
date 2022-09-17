package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public interface AccionesSemanticas {

    Token ejecutar(Character ch, List<Character> buffer, StringBuilder token);
    String toString();
}
