package EtapaLexico;

public class Atributo {
    Lexema lexema;

    public Atributo(Lexema lexema) {
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Atributo{" +
                "lexema=" + lexema +
                '}';
    }
}
