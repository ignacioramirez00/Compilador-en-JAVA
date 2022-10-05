package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.Lexema;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

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
        if (TablaSimbolos.obtenerSimbolo(simbolo) != null){
            return new Token(258,TablaSimbolos.obtenerSimbolo(simbolo));
        } else {
            Lexema lexema = new Lexema(Integer.parseInt(simbolo));
            TablaSimbolos.agregarSimbolo(simbolo,lexema);
            return new Token(258,TablaSimbolos.obtenerSimbolo(simbolo));
        }
    }

    @Override
    public String toString() {
        return "AS3";
    }

}
