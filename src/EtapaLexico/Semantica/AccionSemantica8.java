package EtapaLexico.Semantica;

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
        if(TablaSimbolos.obtenerSimbolo(simbolo) != -1){
            return new TokenAtributo(10,TablaSimbolos.obtenerSimbolo(simbolo));
            //EXISTE EL TOKEN PARA UNA CADENA O UTILIZAMOS EL TOKEN PARA UNA CONSTANTE?
        } else{
            TablaSimbolos.agregarSimbolo(10,simbolo);
            return new TokenAtributo(10,TablaSimbolos.obtenerSimbolo(simbolo));
        }
    }

    @Override
    public String toString() {
        return "AS8";
    }

}
