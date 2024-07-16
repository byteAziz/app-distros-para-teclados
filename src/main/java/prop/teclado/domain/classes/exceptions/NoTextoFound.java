package prop.teclado.domain.classes.exceptions;

public class NoTextoFound extends Exception{
    public NoTextoFound() {
        super("No se ha encontrado el texto");
    }
}
