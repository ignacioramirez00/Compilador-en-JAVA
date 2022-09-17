package EtapaLexico.Semantica;

import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;
import EtapaLexico.Tokens.TokenAtributo;

import java.util.List;

public class AccionSemantica3 implements AccionSemantica {

    public AccionSemantica3(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        String simbolo = token.toString();
        Character c = buffer.remove(0);
        if (TablaSimbolos.obtenerSimbolo(simbolo) != -1){
            return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            if(Integer.parseInt(simbolo) < 255){
                TablaSimbolos.agregarSimbolo(2,simbolo);
                return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
            } else {
                return null; //ERROR
            }
        }
    }

    @Override
    public String toString() {
        return "AS3";
    }

}
