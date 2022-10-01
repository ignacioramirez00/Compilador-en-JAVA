package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica10 implements AccionSemantica {

    public AccionSemantica10(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        buffer.add(0,ch);
        System.out.println(buffer);
        return null;
    }

    @Override
    public String toString() {
        return "AS10";
    }

}
