package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica6 implements AccionSemantica {

    public AccionSemantica6(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        //ESTA BIEN HACER LOS IFS O AGREGAMOS A LA LISTA DE PALABRAS RESERVADAS Y QUE COMPARE AHI?
        Character c = buffer.remove(0);
        token.append(c);
        String simbolo = token.toString();
        if(simbolo.equals("=:")){
            return new Token(4);
        }
        if(simbolo.equals("=!")){
            return new Token(5);
        }
        if(simbolo.equals(">=")){
            return new Token(7);
        }
        if(simbolo.equals("<=")){
            return new Token(9);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS6";
    }

}
