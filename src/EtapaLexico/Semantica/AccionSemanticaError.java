package EtapaLexico.Semantica;

import EtapaLexico.PalabrasReservadas;
import EtapaLexico.TablaSimbolos;

import java.util.List;

public class AccionSemanticaError implements AccionesSemanticas{

    public AccionSemanticaError(){

    }
    @Override
    public String toString() {
        return "ASE";
    }
    @Override
    public void ejecutar(List<Character> buffer, TablaSimbolos ts, PalabrasReservadas pr) {

    }
}
