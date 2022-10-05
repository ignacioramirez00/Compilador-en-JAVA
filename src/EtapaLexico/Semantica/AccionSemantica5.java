package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica5 implements AccionSemantica {

    public AccionSemantica5(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        if(c.equals('+')){
            return new Token(43,null);
        }
        if(c.equals('-')){
            return new Token(45,null);
        }
        if(c.equals('*')){
            return new Token(42,null);
        }
        if(c.equals('/')){
            return new Token(47,null);
        }
        if(c.equals('(')){
            return new Token(40,null);
        }
        if(c.equals(')')){
            return new Token(41,null);
        }
        if(c.equals('{')){
            return new Token(123,null);
        }
        if(c.equals('}')){
            return new Token(125,null);
        }
        if(c.equals(',')){
            return new Token(44,null);
        }
        if(c.equals(';')){
            return new Token(59,null);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS5";
    }

}
