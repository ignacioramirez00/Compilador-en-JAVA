package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;

import java.util.List;

public class AccionSemantica0 implements AccionesSemanticas{

    public AccionSemantica0(){

    }

    @Override
    public String toString() {
        return "AS0";
    }

    @Override
    public void ejecutar(List<Character> buffer, TablaSimbolos ts, PalabrasReservadas pr) {

    }
}
