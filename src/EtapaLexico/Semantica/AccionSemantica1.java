package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;

import java.util.List;

public class AccionSemantica1 implements AccionesSemanticas{

    public AccionSemantica1(){

    }
    @Override
    public String toString() {
        return "AS1";
    }
    @Override
    public void ejecutar(List<Character> buffer, TablaSimbolos ts, PalabrasReservadas pr) {

    }
}
