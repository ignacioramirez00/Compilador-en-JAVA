package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.Lexema;
import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica2 implements AccionSemantica {

    public AccionSemantica2(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        String simbolo = token.toString();
        Character c = buffer.remove(0);
        Integer idPalabraReservada = PalabrasReservadas.getIdentificador(simbolo);
        if (idPalabraReservada != null) {
            return new Token(idPalabraReservada,null);
        } else {
            if (simbolo.length() > 25) {
                AnalisisLexico.agregarError("lexico","se produjo un error de rango de " + simbolo + ", contiene mas de 25 caracteres.");
                String nuevoToken = token.substring(0,24);
                token = new StringBuilder();
                token.append(nuevoToken);
                simbolo = token.toString();
            }
            if (TablaSimbolos.obtenerSimbolo(simbolo) != null) { // esta
                System.out.println("Existe ya el id");
                return new Token(257,TablaSimbolos.obtenerSimbolo(simbolo));
            } else { // no esta
                System.out.println("Entro a nuevo id");
                Lexema lexema = new Lexema(simbolo);
                TablaSimbolos.agregarSimbolo(simbolo,lexema);
                return new Token(257,TablaSimbolos.obtenerSimbolo(simbolo));
            }
        }
    }

    @Override
    public String toString() {
        return "AS2";
    }

}
