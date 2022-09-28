package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
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
        if(Integer.parseInt(simbolo) > 255){
            AnalisisLexico.agregarError("lexico","se produjo un error de rango de " + simbolo + ", es mayor a 255.");
            simbolo = "255";
        }
        if (TablaSimbolos.obtenerSimbolo(simbolo) != -1){
            return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            TablaSimbolos.agregarSimbolo(2,simbolo);
            return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
        }
    }

    @Override
    public String toString() {
        return "AS3";
    }

}
