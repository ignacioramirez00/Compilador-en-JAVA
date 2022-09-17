package EtapaLexico.Semantica;

import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;
import EtapaLexico.Tokens.TokenAtributo;

import java.util.List;

public class AccionSemantica4 implements AccionSemantica {

    public AccionSemantica4(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        String simbolo = token.toString();
        Character c = buffer.remove(0);
        if (TablaSimbolos.obtenerSimbolo(simbolo) != -1){
            return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            if(chequeoRango(Double.parseDouble(simbolo))){
                TablaSimbolos.agregarSimbolo(2,simbolo);
                return new TokenAtributo(2,TablaSimbolos.obtenerSimbolo(simbolo));
            } else {
                return null; //ERROR
            }
        }
    }

    public boolean chequeoRango(Double d){
        if((d == 0.0) || (d > Math.pow(2.2250738585072014,-308) && d < Math.pow(1.7976931348623157,308)) ||
                (d > Math.pow(-1.7976931348623157,308) && d < Math.pow(-2.2250738585072014,-308))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "AS4";
    }

}
