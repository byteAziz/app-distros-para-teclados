package prop.teclado.domain.classes.exceptions;

/**
 * Excepcion para cuando el lenguaje está vacío.
 * Author: Guillem Angulo Hidalgo
 */
public class NoLenguaje extends Exception{
    public NoLenguaje() { super("No se puede crear el teclado porque no hay un Lenguaje disponible");
    }
}
