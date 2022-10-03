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
            return new Token(43);
        }
        if(c.equals('-')){
            return new Token(45);
        }
        if(c.equals('*')){
            return new Token(42);
        }
        if(c.equals('/')){
            return new Token(47);
        }
        if(c.equals('(')){
            return new Token(40);
        }
        if(c.equals(')')){
            return new Token(41);
        }
        if(c.equals('{')){
            return new Token(123);
        }
        if(c.equals('}')){
            return new Token(125);
        }
        if(c.equals(',')){
            return new Token(44);
        }
        if(c.equals(';')){
            return new Token(59);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS5";
    }

}
