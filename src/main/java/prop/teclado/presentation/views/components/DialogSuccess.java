package prop.teclado.presentation.views.components;

import javax.swing.JOptionPane;

/**
 * Dialogo de error que muestra un mensaje de exito que se le pasa por parametro.
 * Author: Guillem Angulo
 */
public class DialogSuccess {
    public static void show(String successMessage) {
        JOptionPane.showMessageDialog(null, successMessage, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void show_wrong(String successMessage) {
        JOptionPane.showMessageDialog(null, successMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
