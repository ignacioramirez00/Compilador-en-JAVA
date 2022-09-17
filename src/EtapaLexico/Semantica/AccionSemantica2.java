package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;
import EtapaLexico.Tokens.Token;
import EtapaLexico.Tokens.TokenAtributo;

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
            return new Token(idPalabraReservada); //DEVOLVER 21?
        } else {
            if (simbolo.length() < 25) {
                if (TablaSimbolos.obtenerSimbolo(simbolo) != -1) { // esta
                    return new TokenAtributo(1,TablaSimbolos.obtenerSimbolo(simbolo));
                } else { // no esta
                    TablaSimbolos.agregarSimbolo(1,simbolo);
                    return new TokenAtributo(1,TablaSimbolos.obtenerSimbolo(simbolo));
                }
            } else {
                return null; // ERROR
            }
        }


    }

    @Override
    public String toString() {
        return "AS2";
    }

}
