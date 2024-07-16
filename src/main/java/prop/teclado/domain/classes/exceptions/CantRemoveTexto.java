package prop.teclado.domain.classes.exceptions;

public class CantRemoveTexto extends Exception{
    public CantRemoveTexto() {
        super("No se puede eliminar el texto del lenguaje porque es el único que hay.");
    }
}
