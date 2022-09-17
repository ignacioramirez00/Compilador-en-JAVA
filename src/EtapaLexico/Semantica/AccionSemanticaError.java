package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemanticaError implements AccionesSemanticas{

    public AccionSemanticaError(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        return null;
    }

    @Override
    public String toString() {
        return "ASE";
    }

}
