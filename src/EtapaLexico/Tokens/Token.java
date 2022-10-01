package EtapaLexico.Tokens;

public class Token {
    Integer id;

    public Token(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "T="+id;
    }
}
