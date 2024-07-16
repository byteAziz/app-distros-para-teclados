package prop.teclado.domain.classes.exceptions;

/**
 * Excepcion para cuando no se introduce ningun simbolo al alfabeto.
 * Author: Joan Martínez Soria
 */
public class NoSimbolosOnAlfabeto extends Exception {
    public NoSimbolosOnAlfabeto() {
        super("No hay simbolos en el alfabeto");
    }
}
