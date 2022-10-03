package EtapaLexico.Tokens;

public class TokenAtributo extends Token{
    String atributo;

    public TokenAtributo(Integer id, String atributo) {
        super(id);
        this.atributo = atributo;
    }

    @Override
    public String toString() {
        return "T="+getId()+"_"+atributo;
    }
}
