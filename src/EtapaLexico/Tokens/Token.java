package EtapaLexico.Tokens;

public class Token {

    Integer id;
    String atributo;

    public Token(Integer id, String atributo) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getAtributo(){
        return atributo;
    }

    @Override
    public String toString() {
        return "T="+id+"_"+atributo;
    }
}
