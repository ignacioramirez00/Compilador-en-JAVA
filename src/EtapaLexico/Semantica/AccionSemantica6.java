package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica6 implements AccionSemantica {

    public AccionSemantica6(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        token.append(c);
        String simbolo = token.toString();
        if(simbolo.equals("=:")){
            return new Token(272,null);
        }
        if(simbolo.equals("=!")){
            return new Token(273,null);
        }
        if(simbolo.equals(">=")){
            return new Token(270,null);
        }
        if(simbolo.equals("<=")){
            return new Token(270,null);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS6";
    }

}
