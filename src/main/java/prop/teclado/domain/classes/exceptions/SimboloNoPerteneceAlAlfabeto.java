package prop.teclado.domain.classes.exceptions;

/**
 * Excepcion para cuando se encuentra un simbolo en una palabra que no pertenece a un simbolo.
 * Author: Joan Martínez Soria
 */
public class SimboloNoPerteneceAlAlfabeto extends Exception{
    public SimboloNoPerteneceAlAlfabeto() {
        super("Aparece un simbolo que no pertenece al alfabeto");
    }
}
