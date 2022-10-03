package EtapaLexico.Semantica;

import EtapaLexico.Lexema;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;
import EtapaLexico.Tokens.TokenAtributo;

import java.util.List;

public class AccionSemantica8 implements AccionSemantica {

    public AccionSemantica8(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        String simbolo = token.toString();
        if(TablaSimbolos.obtenerSimbolo(simbolo) != null){
            return new TokenAtributo(10,TablaSimbolos.obtenerSimbolo(simbolo));
        } else{
            Lexema lexema = new Lexema(simbolo);
            TablaSimbolos.agregarSimbolo(simbolo,lexema);
            return new TokenAtributo(10,TablaSimbolos.obtenerSimbolo(simbolo));
        }
    }

    @Override
    public String toString() {
        return "AS8";
    }

}
