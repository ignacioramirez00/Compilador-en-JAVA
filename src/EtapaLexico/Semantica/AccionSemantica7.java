package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica7 implements AccionSemantica {

    public AccionSemantica7(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        //ESTA BIEN HACER LOS IFS O AGREGAMOS A LA LISTA DE PALABRAS RESERVADAS Y QUE COMPARE AHI?
        Character c = buffer.remove(0);
        String simbolo = token.toString();
        if(simbolo.equals("=")){
            return new Token(61,null);
        }
        if(simbolo.equals(">")){
            return new Token(62,null);
        }
        if(simbolo.equals("<")){
            return new Token(60,null);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS7";
    }

}
