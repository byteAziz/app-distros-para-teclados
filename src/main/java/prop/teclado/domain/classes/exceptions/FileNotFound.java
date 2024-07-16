package prop.teclado.domain.classes.exceptions;

import java.io.FileNotFoundException;

/**
 * Excepcion para cuando no se encuentra el archivo.
 * Author: Joan Martínez Soria
 */
public class FileNotFound extends FileNotFoundException {
    public FileNotFound() {
        super("No se encuentra el archivo");
    }
}
