package functions;

import prop.teclado.domain.classes.Alfabeto;
/**
 * Clase para crear un alfabeto de prueba para los Tests
 * Author: Joan Martínez Soria
 */
public class CreateAlfabetoTest {
    public static Alfabeto alfabetoTest() throws Exception {
        return new Alfabeto("alfabeto", "H o l a", false);
    }
}
