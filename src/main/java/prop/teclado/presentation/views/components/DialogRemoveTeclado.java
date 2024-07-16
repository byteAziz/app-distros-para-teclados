package prop.teclado.presentation.views.components;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.VistaPrincipal;

import javax.swing.*;
import java.util.List;

/**
 * Dialogo para eliminar un teclado
 * Author: Joan Martínez
 */
public class DialogRemoveTeclado {
    public DialogRemoveTeclado(CtrPresentation cp, List<String> nombresTeclados, VistaPrincipal vp) {
        // Muestra un cuadro de diálogo para seleccionar un teclado
        String tecladoSeleccionado = (String) JOptionPane.showInputDialog(
                vp,
                "Selecciona un teclado que quieras eliminar:",
                "Seleccionar Teclado",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresTeclados.toArray(),
                nombresTeclados.toArray()[0]
        );

        // Si el usuario selecciona "Okey" y elige un teclado
        if (tecladoSeleccionado != null && !tecladoSeleccionado.isEmpty()) {
            // Muestra un mensaje de confirmación
            int confirmacion = JOptionPane.showConfirmDialog(
                    vp,
                    "Estas seguro de que deseas eliminar el teclado '" + tecladoSeleccionado + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            // Si el usuario confirma la eliminación
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Llama a la función para eliminar el teclado
                cp.eliminarTeclado(tecladoSeleccionado);

                // Muestra un mensaje de éxito
                JOptionPane.showMessageDialog(
                        vp,
                        "El teclado '" + tecladoSeleccionado + "' ha sido eliminado correctamente.",
                        "Teclado Eliminado",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Actualiza la lista de teclados
                vp.actualizarTeclados(cp);
            }
        }
    }
}
