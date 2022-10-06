package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.Lexema;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica4 implements AccionSemantica {

    public AccionSemantica4(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        String simbolo = token.toString();
        Character c = buffer.remove(0);
        Double number = getDouble(simbolo);
        if( ! chequeoRango(number)){ //?
            token = new StringBuilder();
            AnalisisLexico.agregarError("lexico","se produjo un error de rango de " + simbolo + ", esta fuera de rango");
            return null;
        }
        if (TablaSimbolos.obtenerSimbolo(simbolo) != null){
            return new Token(258,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            Lexema lexema = new Lexema(number);
            TablaSimbolos.agregarSimbolo(simbolo,lexema);
            return new Token(258,TablaSimbolos.obtenerSimbolo(simbolo));
        }
    }

    public Double getDouble(String d){
        if (d.contains("D")){
            var w = d.split("D");
            return Math.pow(Double.valueOf(w[0]),Double.valueOf(w[1]));
        } else {
            return Double.valueOf(d);
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
