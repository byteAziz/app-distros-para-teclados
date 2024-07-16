package prop.teclado.presentation.views;
import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.components.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Vista principal de la aplicación
 * Author: Guillem Angulo y Joan Martínez
 */
public class VistaPrincipal extends JFrame {
    private JPanel panelTeclados = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuNew = new JMenu("Create");
    private JMenu menuDelete = new JMenu("Remove");
    private JMenuItem crearTecladoItem = new JMenuItem("Teclado");
    private JMenuItem crearLenguajeItem = new JMenuItem("Lenguaje");
    private JMenuItem deleteTeclado = new JMenuItem("Teclado");
    private JMenuItem deleteLenguaje = new JMenuItem("Lenguaje");
    private JScrollPane scrollPanel = new JScrollPane(panelTeclados);
    private List<List<String>> teclados = new ArrayList<>();
    private SearchBar searchBar = new SearchBar();

    public VistaPrincipal(CtrPresentation cp,List<List<String>> teclados) {
        super("Aplicacion de gestion de teclados de PROP");
        this.teclados = teclados;

        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurar el panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Configurar el panel superior con GridLayout para la barra de menú y la barra de búsqueda
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2));

        // Configurar y añadir la barra de menú
        menuBar.add(menuNew);
        menuNew.add(crearTecladoItem);
        menuNew.add(crearLenguajeItem);
        menuBar.add(menuDelete);
        menuDelete.add(deleteTeclado);
        menuDelete.add(deleteLenguaje);
        panelSuperior.add(menuBar);

        // Configurar y añadir la barra de búsqueda y los items para ordenar teclados
        panelSuperior.add(searchBar.crearBarraBusqueda(cp, this));
        ordenarTeclados(cp, panelSuperior);

        // Añadir el panel superior al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Configurar el panel de teclados
        panelTeclados.setLayout(new BoxLayout(panelTeclados, BoxLayout.Y_AXIS));

        // Añadir el panel de teclados al centro del panel principal
        panelPrincipal.add(scrollPanel, BorderLayout.CENTER);

        if (teclados.isEmpty()) {
            JLabel noTeclados = new JLabel("No hay teclados en el sistema");
            panelTeclados.add(noTeclados);
        } else {
            mostrarTeclados(cp);
        }

        // Añadir el panel principal a la ventana
        add(panelPrincipal);

        ActionListener vistaCrearTeclado = e -> {
            cp.runVistaCrearTeclado();
            dispose();
        };
        crearTecladoItem.addActionListener(vistaCrearTeclado);

        ActionListener vistaCrearLenguaje = e -> {
            cp.runVistaCrearLenguaje();
            dispose();
        };
        crearLenguajeItem.addActionListener(vistaCrearLenguaje);

        ActionListener dialogRemoveTeclado = e -> {
            cp.runDialogRemoveTeclado(this);
        };
        ActionListener dialogRemoveLenguaje = e -> {
            cp.runDialogRemoveLenguaje(this);
        };
        deleteTeclado.addActionListener(dialogRemoveTeclado);
        deleteLenguaje.addActionListener(dialogRemoveLenguaje);
        setVisible(true);


    }


    //funcion que musetra los teclados en la vista principal, con su respectiva informacion y boton para visualizarlos
    private void mostrarTeclados(CtrPresentation cp) {
        boolean first = true;
        for (List<String> teclado : teclados) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            //si es el primero, no dejamos tanto margen
            if (first) {
                gbc.insets = new Insets(15, 5, 5, 5); // Márgenes
                first = false;
            } else {
                gbc.insets = new Insets(30, 5, 5, 5); // Márgenes
            }

            JPanel panelTeclado = new JPanel(new GridBagLayout());

            JLabel tecladoLabel = new JLabel("Teclado: " + teclado.get(0));
            JLabel lenguajeLabel = new JLabel("Lenguaje: " + teclado.get(1));
            JLabel creacionLabel = new JLabel("Creacion: " + teclado.get(2));
            JLabel modificacionLabel = new JLabel("Modificacion: " + teclado.get(3));

            Font font = new Font("Arial", Font.PLAIN, 14);
            tecladoLabel.setFont(font);
            lenguajeLabel.setFont(font);
            creacionLabel.setFont(font);
            modificacionLabel.setFont(font);

            JButton botonVisualizarTeclado = new JButton("Visualizar");

            ActionListener vistaVisualizarTeclado = e -> {
                System.out.println("Visualizando teclado: " + teclado.get(0));
                // Lógica para visualizar el teclado específico
                cp.runVistaVisualizarTeclado(teclado.get(0));
                dispose();
            };

            botonVisualizarTeclado.addActionListener(vistaVisualizarTeclado);

            // Configuración para datos a la izquierda
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.anchor = GridBagConstraints.WEST;
            panelTeclado.add(tecladoLabel, gbc);

            //reconfiguramos para los datos de la derecha para mejorar la visualizacion
            gbc.insets = new Insets(5, 30, 5, 5); // Márgenes
            gbc.gridy = GridBagConstraints.RELATIVE;
            panelTeclado.add(lenguajeLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            panelTeclado.add(creacionLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            panelTeclado.add(modificacionLabel, gbc);

            // Configuración para el botón a la derecha con separación
            gbc.gridx = 1;
            gbc.gridy = 0;  // Comienza en la primera fila
            gbc.gridheight = 4;  // Ocupa 4 filas
            gbc.anchor = GridBagConstraints.CENTER;

            //separacion adaptable al tamaño de la pantalla
            // Obtener el ancho total de la pantalla
            int anchoPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;

            // Calcular el porcentaje de separación en función del ancho total
            double porcentajeSeparacion = 0.08; // 8% del ancho total de la pantalla
            int separacionIzquierda = (int) (anchoPantalla * porcentajeSeparacion);

            // Configuración para el botón a la derecha con separación adaptable al tamaño de la pantalla
            gbc.insets = new Insets(0, separacionIzquierda, 0, 0);
            panelTeclado.add(botonVisualizarTeclado, gbc);

            panelTeclados.add(panelTeclado);

        }
    }

    //funcion que ordena los teclados por nombre, fecha de creacion o fecha de modificacion
    private void ordenarTeclados(CtrPresentation cp, JPanel panelSuperior) {
        //Añadir opción de ordenar lista de teclados

        JButton botonOrdenar = new JButton("Ordenar por");

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem ordenarPorNombre = new JMenuItem("Nombre");
        JMenuItem ordenarPorCreacion = new JMenuItem("Fecha de Creacion");
        JMenuItem ordenarPorModificacion = new JMenuItem("Fecha de Modificacion");

        ordenarPorNombre.addActionListener(e -> ordenarTecladosPorNombre(cp));
        ordenarPorCreacion.addActionListener(e -> ordenarTecladosPorCreacion(cp));
        ordenarPorModificacion.addActionListener(e -> ordenarTecladosPorModificacion(cp));

        popupMenu.add(ordenarPorNombre);
        popupMenu.add(ordenarPorCreacion);
        popupMenu.add(ordenarPorModificacion);

        botonOrdenar.addActionListener(e -> popupMenu.show(botonOrdenar, 0, botonOrdenar.getHeight()));
        panelSuperior.add(botonOrdenar);


    }

    //--------------------funciones para ordenar los teclados--------------------
   private void ordenarTecladosPorNombre(CtrPresentation cp) {
        List<List<String>> datos = cp.getDatosTeclados();
        int n = datos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Comparamos los nombres (primer elemento de cada lista interna)
                if (datos.get(j).get(0).compareToIgnoreCase(datos.get(j + 1).get(0)) > 0) {
                    // Intercambiamos los elementos si el nombre actual es mayor al siguiente
                    List<String> temp = datos.get(j);
                    datos.set(j, datos.get(j + 1));
                    datos.set(j + 1, temp);
                }
            }
        }
        teclados = datos;
        actualizarVista(cp);
    }

    private void ordenarTecladosPorCreacion(CtrPresentation cp)  {
        List<List<String>> datos = cp.getDatosTeclados();
        int n = datos.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Comparamos las fechas de modificación (tercer elemento de cada lista interna)
                String fechaModificacion1 = datos.get(j).get(2);
                String fechaModificacion2 = datos.get(j + 1).get(2);

                try {
                    Date date1 = dateFormat.parse(fechaModificacion1);
                    Date date2 = dateFormat.parse(fechaModificacion2);

                    // Comparamos las fechas de modificación
                    if (date1 != null && date2 != null && date1.compareTo(date2) > 0) {
                        // Intercambiamos los elementos si la fecha actual es mayor que la siguiente
                        List<String> temp = datos.get(j);
                        datos.set(j, datos.get(j + 1));
                        datos.set(j + 1, temp);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        teclados = datos;
        actualizarVista(cp);
    }

    private void ordenarTecladosPorModificacion(CtrPresentation cp) {
        List<List<String>> datos = cp.getDatosTeclados();
        int n = datos.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Comparamos las fechas de modificación
                String fechaModificacion1 = datos.get(j).get(3);
                String fechaModificacion2 = datos.get(j + 1).get(3);

                try {
                    Date date1 = dateFormat.parse(fechaModificacion1);
                    Date date2 = dateFormat.parse(fechaModificacion2);

                    // Comparamos las fechas de modificación
                    if (date1 != null && date2 != null && date1.compareTo(date2) > 0) {
                        // Intercambiamos los elementos si la fecha actual es mayor que la siguiente
                        List<String> temp = datos.get(j);
                        datos.set(j, datos.get(j + 1));
                        datos.set(j + 1, temp);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        teclados = datos;
        actualizarVista(cp);
    }


    //actualiza la vista de la vista principal
    private void actualizarVista(CtrPresentation cp) {
        panelTeclados.removeAll(); // Elimina todos los teclados del panel
        if (teclados.isEmpty()) {
            JLabel noTeclados = new JLabel("No hay teclados en el sistema");
            panelTeclados.add(noTeclados);
        } else {
            mostrarTeclados(cp); // Vuelve a mostrar los teclados (ahora ordenados)
        }
        panelTeclados.revalidate();
        panelTeclados.repaint();
    }

    public void actualizarTeclados(CtrPresentation cp) {
        teclados = cp.getDatosTeclados();
        actualizarVista(cp);
    }

    public void buscarTeclados(CtrPresentation cp, String nombreTeclado) {
        teclados = cp.busquedaTeclados(nombreTeclado);
        actualizarVista(cp);
    }
}

