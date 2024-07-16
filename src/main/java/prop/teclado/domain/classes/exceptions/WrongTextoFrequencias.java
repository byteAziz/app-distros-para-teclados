package prop.teclado.domain.classes.exceptions;

/**
 * Excepción que se lanza cuando se intenta crear un un texto de frecuencias de manera incorrecta
 * Author: Joan Martinez Soria
 */
public class WrongTextoFrequencias extends Exception{
    public WrongTextoFrequencias() {
        super("Se ha introducido un texto de frecuencias incorrecto");
    }
}
