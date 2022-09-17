package EtapaLexico.Tokens;

public class TokenAtributo extends Token{
    Integer atributo;

    public TokenAtributo(Integer id, Integer atributo) {
        super(id);
        this.atributo = atributo;
    }
}
