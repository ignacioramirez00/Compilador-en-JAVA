package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica9 implements AccionSemantica {

    public AccionSemantica9(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        token = new StringBuilder();
        return null;
    }

    @Override
    public String toString() {
        return "AS9";
    }

}
