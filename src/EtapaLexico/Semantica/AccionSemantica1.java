package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica1 implements AccionesSemanticas{

    public AccionSemantica1(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        token.append(c);
        return null;
    }

    @Override
    public String toString() {
        return "AS1";
    }

}
