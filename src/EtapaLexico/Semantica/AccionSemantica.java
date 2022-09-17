package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public interface AccionSemantica {

    Token ejecutar(Character ch, List<Character> buffer, StringBuilder token);
    String toString();
}
