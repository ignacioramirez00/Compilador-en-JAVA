package EtapaLexico;

import EtapaLexico.Semantica.AccionesSemanticas;

public class AnalisisLexico {
    private PalabrasReservadas palabrasReservadas;
    private TablaSimbolos tablaSimbolos;
    private Integer transicionEstados[][] = new Integer[18][18];
    private AccionesSemanticas accionesSemanticas[][]; // ???
}
