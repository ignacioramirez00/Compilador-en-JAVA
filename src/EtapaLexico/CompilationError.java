package EtapaLexico;

public class CompilationError {
    private String tipo;
    private String mensaje;
    private int linea;

    public CompilationError(String tipo, String mensaje, int linea) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "Se detecto un error de compilacion en la etapa de: " + tipo + ", en la linea: " + linea + ", razon: " + mensaje;
    }
}
