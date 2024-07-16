package prop.teclado.presentation.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import prop.teclado.presentation.controllers.CtrPresentation;

/**
 * Vista de visualizar un teclado
 * Author: Guillem Angulo
 */
public class VistaVisualizarTeclado extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel titulo = new JLabel("Visualizar un teclado");
    private JButton returnButton = new JButton("Return to main menu");
    private JButton editButton = new JButton("Editar el teclado");
    private JPanel tecladoPanel = new JPanel();
    private JLabel tecladoTitle = new JLabel();

    public VistaVisualizarTeclado(CtrPresentation cp, String tecladoAVisualizar) {
        super("Aplicacion de gestion de teclados de PROP");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener returnToMainMenu = e -> {
            cp.runVistaPrincipal();
            dispose();
        };

        ActionListener editTeclado = e -> {
            cp.runVistaEditarTeclado(tecladoAVisualizar);
            dispose();
        };

        panel.setLayout(new BorderLayout());
        panel.add(titulo, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(returnButton);
        buttonPanel.add(editButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        returnButton.addActionListener(returnToMainMenu);
        editButton.addActionListener(editTeclado);
        add(panel);

        // Agrega el título del teclado debajo del teclado
        tecladoTitle.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(new EmptyBorder(0, 0, 50, 0));
        tecladoTitle.setFont(tecladoTitle.getFont().deriveFont(Font.BOLD, 24));
        titlePanel.add(tecladoTitle);
        panel.add(titlePanel, BorderLayout.SOUTH);


        tecladoPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.add(tecladoPanel, BorderLayout.CENTER);

        setVisible(true);

        Character[][] teclado = cp.getDisposicionTeclas(tecladoAVisualizar);

        desplegarTeclado(teclado);
        mostrarTituloTeclado(tecladoAVisualizar);
    }

    //funcion que se encarga de pintar las teclas del teclado
    public void desplegarTeclado(Character[][] teclado) {
        tecladoPanel.setLayout(new GridLayout(teclado.length, teclado[0].length));

        for (Character[] fila : teclado) {
            for (Character tecla : fila) {
                JButton botonTecla;
                if (tecla != null) {
                    if (String.valueOf(tecla).equals(" ")) {
                    botonTecla = new JButton("SPACE");
                    }
                    else botonTecla = new JButton(String.valueOf(tecla));
                }
                else {
                    botonTecla = new JButton("");
                }

                botonTecla.setPreferredSize(new Dimension(80, 80));

                Font tam = botonTecla.getFont();
                float fontSize = tam.getSize() + 10.0f;
                Font largerFont = tam.deriveFont(fontSize);
                botonTecla.setFont(largerFont);
                tecladoPanel.add(botonTecla);
            }
        }
    }

    //funcion que se encarga de mostrar el titulo del teclado
    private void mostrarTituloTeclado(String nombreTeclado) {
        String nombreMayusc = nombreTeclado.toUpperCase();
        tecladoTitle.setText("Visualizando el teclado " + nombreMayusc + "");
    }

}

