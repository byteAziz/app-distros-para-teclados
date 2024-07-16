package prop.teclado.presentation.views.components;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.VistaPrincipal;

import javax.swing.*;
import java.util.List;

/**
 * Dialogo para eliminar un lenguaje
 * Author: Joan Martínez
 */
public class DialogRemoveLenguaje {
    public DialogRemoveLenguaje(CtrPresentation cp, List<String> nombresLenguajes, VistaPrincipal vp) {
        // Muestra un cuadro de diálogo para seleccionar un teclado
        String lenguajeSeleccionado = (String) JOptionPane.showInputDialog(
                vp,
                "Selecciona un lenguaje que quieras eliminar:",
                "Seleccionar Lenguaje",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresLenguajes.toArray(),
                nombresLenguajes.toArray()[0]
        );

        // Si el usuario selecciona "Okey" y elige un teclado
        if (lenguajeSeleccionado != null && !lenguajeSeleccionado.isEmpty()) {
            // Muestra un mensaje de confirmación
            int confirmacion = JOptionPane.showConfirmDialog(
                    vp,
                    "Estas seguro de que deseas eliminar el lenguaje '" + lenguajeSeleccionado + "'?",
                    "Confirmar Eliminacion",
                    JOptionPane.YES_NO_OPTION
            );

            // Si el usuario confirma la eliminación
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Llama a la función para eliminar el teclado
                cp.eliminarLenguaje(lenguajeSeleccionado);

                // Muestra un mensaje de éxito
                JOptionPane.showMessageDialog(
                        vp,
                        "El lenguaje '" + lenguajeSeleccionado + "' ha sido eliminado correctamente.",
                        "Lenguaje Eliminado",
                        JOptionPane.INFORMATION_MESSAGE
                );

            }
        }
    }
}
