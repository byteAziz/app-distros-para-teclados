package prop.teclado.presentation.views;

import prop.teclado.presentation.controllers.CtrPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista de modificar lenguaje y sus diferentes opciones
 * Author: Guillem Angulo
 */
public class VistaModificarLenguaje extends JFrame {

    private JLabel titulo = new JLabel("Selecciona una opcion para modificar el lenguaje:");
    private JButton botonCancelar = new JButton("Volver a Editar Teclado");
    private  JButton botonAdd = new JButton("Agregar Texto");
    private JButton botonEliminar = new JButton("Eliminar Texto");

    public VistaModificarLenguaje(CtrPresentation cp, String teclado) {
        super("Aplicacion de gestion de teclados de PROP");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener volverAtras = e -> {
            cp.runVistaEditarTeclado(teclado);
            setVisible(false);
            System.out.println("Volver atras");
            dispose();
        };

        ActionListener anadir = e -> {
            cp.runVistaAnadirTexto(teclado);
            setVisible(false);
            System.out.println("Añadir un texto al lenguaje");
            dispose();
        };

        ActionListener eliminar = e -> {
            cp.runDialogEliminarTextoLenguaje(teclado, this);
            setVisible(false);
            System.out.println("Eliminar un texto al lenguaje");
            dispose();
        };

        botonAdd.addActionListener(anadir);
        botonEliminar.addActionListener(eliminar);
        botonCancelar.addActionListener(volverAtras);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonCancelar);

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1, 0, 30));
        panelTitulo.add(panelBotones);

        // Añadir un texto debajo los botones
        Font tam = new Font("Arial", Font.PLAIN, 20);
        float fontSize = 20.0f;
        Font larger = tam.deriveFont(fontSize);
        titulo.setFont(larger);

        panelTitulo.add(titulo);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // para que boton y texto esten centrados
        JPanel centrado =  new JPanel(new FlowLayout(FlowLayout.CENTER));
        centrado.add(panelTitulo);

        panelPrincipal.add(centrado, BorderLayout.NORTH);

        JPanel opciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        botonAdd.setBackground(Color.WHITE);
        botonAdd.setForeground(Color.blue);

        botonEliminar.setBackground(Color.WHITE);
        botonEliminar.setForeground(Color.blue);

        opciones.add(botonAdd);
        opciones.add(botonEliminar);

        panelPrincipal.add(opciones, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }
}
