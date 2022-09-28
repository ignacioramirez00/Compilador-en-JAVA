package EtapaLexico.Semantica;

import EtapaLexico.Tokens.Token;

import java.util.List;

public class AccionSemantica5 implements AccionSemantica {

    public AccionSemantica5(){

    }

    @Override
    public Token ejecutar(Character ch, List<Character> buffer, StringBuilder token) {
        Character c = buffer.remove(0);
        if(c.equals("+")){
            return new Token(11);
        }
        if(c.equals("-")){
            return new Token(12);
        }
        if(c.equals("*")){
            return new Token(13);
        }
        if(c.equals("/")){
            return new Token(14);
        }
        if(c.equals("(")){
            return new Token(15);
        }
        if(c.equals(")")){
            return new Token(16);
        }
        if(c.equals("{")){
            return new Token(17);
        }
        if(c.equals("}")){
            return new Token(18);
        }
        if(c.equals(",")){
            return new Token(19);
        }
        if(c.equals(";")){
            return new Token(20);
        }
        return null;
    }

    @Override
    public String toString() {
        return "AS5";
    }

}
