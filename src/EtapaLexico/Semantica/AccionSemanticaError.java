package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemanticaError implements AccionSemantica {

    public AccionSemanticaError(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        //FALTA IMPLEMENTAR, DONDE CARGAMOS LOS ERRORES? LUEGO DE UN ERROR EL PROGRAMA SIGUE CORRIENDO?
        return null;
    }

    @Override
    public String toString() {
        return "ASE";
    }

}
