package prop.teclado.presentation.views;

import prop.teclado.domain.classes.Teclado;
import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.components.DialogSuccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Vista de cambiar nombre de un teclado
 * Author: Guillem Angulo
 */
public class VistaCambiarNombre extends JFrame {
    //private JLabel labelTitulo = new JLabel("Editar teclado");
    private JButton botonGuardar = new JButton("Guardar");
    private JButton botonCancelar = new JButton("Cancelar");

    private JTextField campoNombre = new JTextField(20);

    public VistaCambiarNombre(CtrPresentation cp, String teclado) {
        super("Aplicacion de gestion de teclados de PROP");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener volverAtras = e -> {
            cp.runVistaEditarTeclado(teclado);
            System.out.println("Volver atras");
            dispose();
        };

        ActionListener guardarTeclado = e -> {

            String nombredeseado = campoNombre.getText();
            Teclado t = cp.getTeclado(teclado);
            String nombreactual = t.getNombre();
            boolean a = cambiarN(nombredeseado, nombreactual, cp);
            if (a) cp.runVistaVisualizarTeclado(nombredeseado);
            else cp.runVistaVisualizarTeclado(nombreactual);

            // al darle al boton se actualiza fechamodificacion

        };

        botonCancelar.addActionListener(volverAtras);
        botonGuardar.addActionListener(guardarTeclado);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout());

        panelBotones.add(new JLabel());
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel panelTexto = new JPanel(new FlowLayout());
        JLabel titulo = new JLabel("Nuevo nombre:");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        panelTexto.add(titulo);
        panelTexto.add(campoNombre);

        panelPrincipal.add(panelBotones, BorderLayout.NORTH); // Agregar el panel de botones en la primera fila
        panelPrincipal.add(panelTexto, BorderLayout.CENTER); // Agregar el panel de texto en la segunda fila

        add(panelPrincipal);
        setVisible(true);

    }

    private boolean cambiarN(String nombrenuevo, String nombreactual, CtrPresentation cp) {
        if (nombrenuevo.equals(nombreactual)) {
            DialogSuccess.show_wrong("El nombre nuevo debe ser diferente al actual ");
            dispose();
            return false;
        }
        else {
            if (nombrenuevo.equals(" ")) {
                DialogSuccess.show_wrong("El nombre no puede ser un espacio ");
                dispose();
                return false;
            }
            else {
                cp.cambiarNombreTeclado(nombrenuevo, nombreactual);
                //cambiar fecha modificacion
                String fecha_formateada = obtenerfecha();
                cp.setFechaModificacion(fecha_formateada, nombrenuevo);
                dispose();
                return true;
            }
        }
    }

    private String obtenerfecha() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaHoraActual.format(formatter);
    }
}
