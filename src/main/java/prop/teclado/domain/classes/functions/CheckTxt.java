package prop.teclado.domain.classes.functions;

import prop.teclado.domain.classes.exceptions.NoTxt;

/**
 * Funcion que comprueba si un archivo es .txt.
 * Author: Joan Martínez Soria
 */
public class CheckTxt {
    public static void checkTxt(String path) throws NoTxt {
        if (!path.toLowerCase().endsWith(".txt")) {
            throw new NoTxt();
        }
    }
}
