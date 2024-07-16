package prop.teclado.presentation.views;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.components.DialogSuccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Vista de cambiar la posicion de dos teclas de un teclado
 * Author: Guillem Angulo
 */
public class VistaCambiarPosicion extends JFrame {
   // private JLabel labelTitulo = new JLabel("Editar teclado");
    private JButton botonGuardar = new JButton("Guardar");
    private JButton botonCancelar = new JButton("Cancelar");

    private JLabel labelTeclado = new JLabel("Teclado");

    private JTextField campoChar1 = new JTextField(5);

    private JTextField campoChar2 = new JTextField(5);

    public VistaCambiarPosicion(CtrPresentation cp, String teclado) {
        super("Aplicacion de gestion de teclados de PROP");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener volverAtras = e -> {
            cp.runVistaEditarTeclado(teclado);
            setVisible(false);
            System.out.println("Volver atras");
        };

        ActionListener guardarTeclado = e -> {
            //implementar logica de guardado del teclado
            System.out.println("Guardar teclado");

            String s1 = campoChar1.getText();
            String s2 = campoChar2.getText();

            Character char1 = s1.charAt(0);
            Character char2 = s2.charAt(0);

            if (!s1.equals(s2)) cp.cambiarPosicion(char1, char2, teclado);
            else {
                DialogSuccess.show_wrong("Las teclas deben ser diferentes");
            }

            cp.runVistaVisualizarTeclado(teclado);
            setVisible(false);

            /*AL DONARLI AL BOTO DE GUARDAR, S'ACTUALITZA LA DATA DE MODIFICACIO*/
            String fecha_formateada = obtenerfecha();
            cp.setFechaModificacion(fecha_formateada, teclado);
            dispose();
        };

        botonCancelar.addActionListener(volverAtras);
        botonGuardar.addActionListener(guardarTeclado);


        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelBotones= new JPanel(new FlowLayout());
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        JPanel panelCambio = new JPanel(new FlowLayout());
        JLabel uso = new JLabel("Ingresar el caracter que está representado en la tecla que se desea cambiar de posicion");
        JLabel C1 = new JLabel("TECLA1");
        JLabel C2 = new JLabel("TECLA2");

        Font tam = new Font("Arial", Font.PLAIN, 20);
        float fontSize = 20.0f;
        Font larger = tam.deriveFont(fontSize);
        C1.setFont(larger);
        C2.setFont(larger);

        uso.setFont(new Font("Arial", Font.BOLD, 18));

        panelCambio.add(new JLabel()); // vacio
        panelCambio.add(C1);
        panelCambio.add(campoChar1);
        panelCambio.add(C2);
        panelCambio.add(campoChar2);
        panelCambio.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel panelUso = new JPanel(new GridLayout(2, 1));
        JLabel space = new JLabel("En caso de querer cambiar la tecla SPACE, ingresar directamente un carácter espacio (' ')");
        space.setFont(new Font("Arial", Font.BOLD, 18));

        panelUso.add(uso);
        panelUso.add(space);
        panelUso.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JPanel centrado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centrado.add(panelUso);

        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        panelPrincipal.add(panelCambio, BorderLayout.CENTER);
        panelPrincipal.add(centrado, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);

    }

    private String obtenerfecha() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaHoraActual.format(formatter);
    }

}
