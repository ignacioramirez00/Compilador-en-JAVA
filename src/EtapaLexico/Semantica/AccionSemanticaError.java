package EtapaLexico.Semantica;

import EtapaLexico.AnalisisLexico;
import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemanticaError implements AccionSemantica {

    public AccionSemanticaError(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        AnalisisLexico.agregarError("lexico","se produjo un error de semantica.");
        return null;
    }

    @Override
    public String toString() {
        return "ASE";
    }

}
