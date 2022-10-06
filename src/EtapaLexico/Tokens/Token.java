package EtapaLexico.Tokens;

public class Token {

    Integer id;
    String atributo;

    public Token(Integer id, String atributo) {
        this.id = id;
        this.atributo = atributo;
    }

    public Integer getId() {
        return id;
    }
    public String getAtributo(){
        return atributo;
    }

    @Override
    public String toString() {
        if (atributo != null)
            return "T="+id+"_"+atributo;
        else
            return "T="+id;
    }
}
