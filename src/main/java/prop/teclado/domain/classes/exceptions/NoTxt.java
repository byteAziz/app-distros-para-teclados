package prop.teclado.domain.classes.exceptions;

/**
 * Excepcion para cuando se pasa un archivo que no es del tipo .txt.
 * Author: Joan Martínez Soria
 */
public class NoTxt extends Exception{
    public NoTxt() {
        super("El archivo no es un .txt");
    }
}
