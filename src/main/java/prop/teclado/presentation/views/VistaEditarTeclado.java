package prop.teclado.presentation.views;

import prop.teclado.presentation.controllers.CtrPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Vista de editar un teclado
 * Author: Guillem Angulo
 */
public class VistaEditarTeclado extends JFrame {
    private JLabel labelTitulo = new JLabel("Selecciona una opcion para modificar el teclado:");
    private JButton botonNombreTeclado = new JButton("Cambiar nombre del Teclado");
    private JButton botonPosicionTeclas = new JButton("Cambiar posicion de dos teclas");
    private JButton botonModificarLenguaje = new JButton("Modificar Lenguaje");
    private JButton botonAtras = new JButton("Volver a Visualizar Teclado");

    public VistaEditarTeclado(CtrPresentation cp, String teclado) {
        super("Aplicacion de gestion de teclados de PROP");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener volverAtras = e -> {
            System.out.println("Volver atras");
            cp.runVistaVisualizarTeclado(teclado);
            dispose();
        };


        ActionListener nombre = e -> {
            System.out.println("Cambiar Nombre");
            cp.runVistaCambiarNombre(teclado);
            dispose();
        };


        ActionListener Posicion = e -> {
            System.out.println("Cambiar Posicion");
            cp.runVistaCambiarPosicion(teclado);
            dispose();
        };


        ActionListener Lenguaje = e -> {
            System.out.println("Modificar Lenguaje");
            cp.runVistaModificarLenguaje(teclado);
            dispose();
        };

        botonAtras.addActionListener(volverAtras);
        botonNombreTeclado.addActionListener(nombre);
        botonPosicionTeclas.addActionListener(Posicion);
        botonModificarLenguaje.addActionListener(Lenguaje);



        botonPosicionTeclas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al seleccionar el botón de Posición de dos teclas
                System.out.println("Seleccionado: Posicion de dos teclas");
                // Puedes agregar aquí la lógica para cambiar la posición de las teclas
            }
        });

        botonModificarLenguaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al seleccionar el botón de Modificar Lenguaje
                System.out.println("Seleccionado: Modificar Lenguaje");
                // Puedes agregar aquí la lógica para modificar el lenguaje
            }
        });


        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelTitulo = crearPanelTitulo();
        JPanel panelBotones = crearPanelBotones();

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);


        add(panelPrincipal);
        setVisible(true);
    }

    //funcion que crea el panel del titulo
    private JPanel crearPanelTitulo() {
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1, 0, 30));
        panelTitulo.add(botonAtras);

        Font tam = new Font("Arial", Font.PLAIN, 20);
        float fontSize = 20.0f;
        Font larger = tam.deriveFont(fontSize);
        labelTitulo.setFont(larger);

        panelTitulo.add(labelTitulo);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel centrado =  new JPanel(new FlowLayout(FlowLayout.CENTER));
        centrado.add(panelTitulo);
        return centrado;
    }
    //funcion que crea el panel de los botones
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonModificarLenguaje.setBackground(Color.WHITE);
        botonModificarLenguaje.setForeground(Color.blue);

        botonNombreTeclado.setBackground(Color.WHITE);
        botonNombreTeclado.setForeground(Color.blue);

        botonPosicionTeclas.setBackground(Color.WHITE);
        botonPosicionTeclas.setForeground(Color.blue);

        panelBotones.add(botonNombreTeclado);
        panelBotones.add(botonPosicionTeclas);
        panelBotones.add(botonModificarLenguaje);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return panelBotones;
    }

}
