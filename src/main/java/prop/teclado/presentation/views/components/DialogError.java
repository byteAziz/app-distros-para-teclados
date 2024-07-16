package prop.teclado.presentation.views.components;

import javax.swing.JOptionPane;

/**
 * Dialogo de error que muestra un mensaje de error que se le pasa por parametro.
 * Author: Joan Martínez Soria
 */
public class DialogError {
    public static void show(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}