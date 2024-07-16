package prop.teclado.presentation.views.components;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.VistaModificarLenguaje;

import javax.swing.*;
import java.util.List;

/**
 * Dialogo para eliminar un texto de un lenguaje
 * Author: Joan Martínez
 */
public class DialogEliminarTextoLenguaje {
    public DialogEliminarTextoLenguaje(CtrPresentation cp, String nombreTeclado,
                                       List<String> nombresTextos, VistaModificarLenguaje vp) {
        // Muestra un cuadro de diálogo para seleccionar un teclado
        String textoSeleccionado = (String) JOptionPane.showInputDialog(
                vp,
                "Selecciona un texto que quieras eliminar del lenguaje:",
                "Seleccionar Texto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresTextos.toArray(),
                nombresTextos.toArray()[0]
        );

        // Si el usuario selecciona "Okey" y elige un teclado
        if (textoSeleccionado != null && !textoSeleccionado.isEmpty()) {
            // Muestra un mensaje de confirmación
            int confirmacion = JOptionPane.showConfirmDialog(
                    vp,
                    "Estas seguro de que deseas eliminar el texto '" + textoSeleccionado + "'?",
                    "Confirmar Eliminacion",
                    JOptionPane.YES_NO_OPTION
            );

            // Si el usuario confirma la eliminación
            if (confirmacion == JOptionPane.YES_OPTION) {

                try {
                    cp.eliminarTexto(textoSeleccionado, nombreTeclado);
                    // Muestra un mensaje de éxito si no salta excepcion
                    JOptionPane.showMessageDialog(
                            vp,
                            "El texto '" + textoSeleccionado + "' ha sido eliminado correctamente.",
                            "Texto Eliminado",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception e) {
                    DialogError.show(e.getMessage());
                }
                //volvemos a la vista de visualizar teclado
                cp.runVistaVisualizarTeclado(nombreTeclado);
            } else {
                //volvemos a la vista de visualizar teclado
                DialogSuccess.show("El texto '" + textoSeleccionado + "' no ha sido eliminado.");
                cp.runVistaVisualizarTeclado(nombreTeclado);
            }
        }
        else cp.runVistaModificarLenguaje(nombreTeclado);
    }
}

