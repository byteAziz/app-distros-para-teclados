package prop.teclado.presentation.views.components;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.VistaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Barra de busqueda que se puede añadir a cualquier vista (de momento solo util en la vista del menu principal).
 * Author: Joan Martínez Soria
 */
public class SearchBar {
    public JComponent crearBarraBusqueda(CtrPresentation cp, VistaPrincipal vp) {
        // Puedes personalizar esto según tus necesidades
        JTextField campoBusqueda = new JTextField();
        JButton botonBuscar = new JButton("Search");
        ActionListener buscar = e -> {
            //se realiza la busqueda de los teclados y se muestran en la vista principal
            //aquellos teclados que tengan el nombre que se ha introducido en el campo de busqueda
            System.out.println("Buscando: " + campoBusqueda.getText());
            // Cerrar la vista anterior
            vp.buscarTeclados(cp, campoBusqueda.getText());
        };
        botonBuscar.addActionListener(buscar);
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(botonBuscar, BorderLayout.EAST);

        return panelBusqueda;
    }
}
