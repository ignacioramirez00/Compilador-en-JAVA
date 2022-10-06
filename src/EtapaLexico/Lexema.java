package EtapaLexico;

public class Lexema {
    Integer atrEntero;
    Double atrDouble;
    String atrString;

    public Lexema(int atrEntero) {
        this.atrEntero = atrEntero;
        this.atrDouble = null;
        this.atrString = null;
    }

    public Lexema(double atrDouble) {
        this.atrDouble = atrDouble;
        this.atrString = null;
        this.atrEntero = null;
    }

    public Lexema(String atrString) {
        this.atrString = atrString;
        this.atrDouble = null;
        this.atrEntero = null;
    }

    public String getTipo() {
        if(atrString != null)
            return "string";
        if(atrDouble != null)
            return "double";
        if(atrEntero != null)
            return "entero";
        return null;
    }

    @Override
    public String toString() {
        if(atrString != null)
            return atrString;
        if(atrDouble != null)
            return atrDouble.toString();
        if(atrEntero != null)
            return atrEntero.toString();
        return null;
    }
}
