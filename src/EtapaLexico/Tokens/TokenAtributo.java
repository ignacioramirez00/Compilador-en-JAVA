package EtapaLexico.Tokens;

public class TokenAtributo extends Token{
    Integer atributo;

    public TokenAtributo(Integer id, Integer atributo) {
        super(id);
        this.atributo = atributo;
    }

    @Override
    public String toString() {
        return "T="+getId()+"_"+atributo;
    }
}
