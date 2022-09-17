package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica0 implements AccionesSemanticas{

    public AccionSemantica0(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        if (c == '\n') {
            AnalisisLexico.setLineaActual(AnalisisLexico.getLineaActual()+1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS0";
    }


}
