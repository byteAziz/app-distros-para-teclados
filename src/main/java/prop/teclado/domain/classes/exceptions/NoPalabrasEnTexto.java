package prop.teclado.domain.classes.exceptions;

/**
 * Excepcion para cuando no se introducen palabras en un texto.
 * Author: Joan Martínez Soria
 */
public class NoPalabrasEnTexto extends Exception{
    public NoPalabrasEnTexto() {
        super("No hay palabras en el texto");
    }
}
