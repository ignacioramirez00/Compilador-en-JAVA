package EtapaLexico;

import java.util.HashMap;
import java.util.Map;

public class Atributo {
    Lexema lexema;
    Map<String,String> etc = new HashMap<>();

    public Atributo(Lexema lexema) {
        this.lexema = lexema;
    }

    public void setLexema(Lexema lexema){
        this.lexema = lexema;
    }

    public String getTipo(){
        return lexema.getTipo();
    }

    public void agregarAtributo(String k, String v){
        etc.put(k,v);
    }

    @Override
    public String toString() {
        return "Atributo{" +
                "lexema=" + lexema +
                '}';
    }
}
