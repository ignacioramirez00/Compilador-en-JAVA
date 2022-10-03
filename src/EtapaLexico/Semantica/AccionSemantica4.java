package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.Lexema;
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
        if( ! chequeoRango(Double.valueOf(simbolo))){ //?
            AnalisisLexico.agregarError("lexico","se produjo un error de rango de " + simbolo + ", es mayor a 1.7976931348623157D+308.");
            simbolo = "1.7976931348623157D+308"; // el maximo
        }
        if (TablaSimbolos.obtenerSimbolo(simbolo) != null){
            return new TokenAtributo(258,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            Lexema lexema = new Lexema(Double.valueOf(simbolo));
            TablaSimbolos.agregarSimbolo(simbolo,lexema);
            return new TokenAtributo(258,TablaSimbolos.obtenerSimbolo(simbolo));
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
