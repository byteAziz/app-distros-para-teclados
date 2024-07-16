package prop.teclado.presentation.views;

import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.components.DialogError;
import prop.teclado.presentation.views.components.DialogSuccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Vista para crear un lenguaje
 * Author: Joan Martínez
 */
public class VistaCrearLenguaje extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel titulo = new JLabel("Crear un lenguaje");
    private JLabel nombreLenguaje = new JLabel("Nombre del lenguaje:");
    private JTextField nombreLenguajeInput = new JTextField(20);
    private JLabel nombreAlfabeto = new JLabel("Nombre del alfabeto:");
    private JTextField nombreAlfabetoInput = new JTextField(20);
    private JLabel simbolosAlfabeto = new JLabel("Simbolos del alfabeto * :");
    private JTextField simbolosAlfabetoInput = new JTextField(20);
    private JLabel nombreTexto = new JLabel("Nombre del texto:");
    private JTextField nombreTextoInput = new JTextField(20);
    private JLabel contenidoTexto = new JLabel("Contenido del texto * :");
    private JTextField contenidoTextoInput = new JTextField(100);
    private JComboBox<String> selectTexto = new JComboBox<>(new String[]{"Ingresar manualmente", "Seleccionar desde archivo"});
    private JComboBox<String> selectAlfabeto = new JComboBox<>(new String[]{"Ingresar manualmente", "Seleccionar desde archivo", "Seleccionar alfabeto predefinido"});
    private JRadioButton textoEstandarRadioButton = new JRadioButton("Texto Estandar");
    private JRadioButton textoFrecuenciaRadioButton = new JRadioButton("Texto de Frecuencia");
    private ButtonGroup radioButtonGrupoTexto = new ButtonGroup();
    private JButton buscarTexto = new JButton("Buscar texto");
    private JButton buscarAlfabeto = new JButton("Buscar alfabeto");
    private JButton crearLenguaje = new JButton("Crear lenguaje");
    private JButton returnButton = new JButton("Volver al menu principal");
    private SpringLayout lyt = new SpringLayout();
    private CtrPresentation cp;

    public VistaCrearLenguaje(CtrPresentation cp) {
        super("Aplicacion de gestion de lenguajes de PROP");
        this.cp = cp;
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(lyt);

        configSelectAlfabeto();
        configBuscarAlfabeto();
        configBuscarTexto();
        configTipoTexto();
        configCrearLenguaje();
        configReturnButton();

        limitarCaracteresAlfabeto();

        addPanel();

        nombreAlfabetoInput.setEnabled(true);
        simbolosAlfabetoInput.setEnabled(true);

        nombreTextoInput.setEnabled(true);
        buscarTexto.setEnabled(false);

        posicionarElementos();

        add(panel);
        setVisible(true);
    }

    //funcion que añade los elementos al panel
    private void addPanel() {
        panel.add(titulo);
        panel.add(nombreLenguaje);
        panel.add(nombreLenguajeInput);
        panel.add(nombreAlfabeto);
        panel.add(nombreAlfabetoInput);
        panel.add(simbolosAlfabeto);
        panel.add(simbolosAlfabetoInput);
        panel.add(selectAlfabeto);
        panel.add(buscarAlfabeto);
        panel.add(selectTexto);
        panel.add(nombreTexto);
        panel.add(nombreTextoInput);
        panel.add(contenidoTexto);
        panel.add(contenidoTextoInput);
        panel.add(textoEstandarRadioButton);
        panel.add(textoFrecuenciaRadioButton);
        panel.add(buscarTexto);
        panel.add(returnButton);
        panel.add(crearLenguaje);
        radioButtonGrupoTexto.add(textoEstandarRadioButton);
        textoEstandarRadioButton.setSelected(true);
        radioButtonGrupoTexto.add(textoFrecuenciaRadioButton);
    }

    //funcion que limita los caracteres que se pueden introducir en un JTextField de alfabeto
    private void limitarCaracteresAlfabeto() {
        simbolosAlfabetoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (simbolosAlfabetoInput.getText().length() == 50) {
                    e.consume();
                }
            }
        });
    }

    //funcion que quita el limite de caracteres que se pueden introducir en un JTextField de alfabeto
    private void quitarLimiteCaracteresAlfabeto() {
        simbolosAlfabetoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (simbolosAlfabetoInput.getText().length() == Integer.MAX_VALUE) {
                    e.consume();
                }
            }
        });
    }

    //funcion que posiciona los elementos en el panel
    private void posicionarElementos() {
        lyt.putConstraint(SpringLayout.WEST, titulo, 10, SpringLayout.HORIZONTAL_CENTER, panel);
        lyt.putConstraint(SpringLayout.NORTH, titulo, 10, SpringLayout.NORTH, panel);

        lyt.putConstraint(SpringLayout.WEST, nombreLenguaje, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, nombreLenguaje, 10, SpringLayout.SOUTH, titulo);
        lyt.putConstraint(SpringLayout.WEST, nombreLenguajeInput, 10, SpringLayout.EAST, nombreLenguaje);
        lyt.putConstraint(SpringLayout.NORTH, nombreLenguajeInput, 10, SpringLayout.SOUTH, titulo);

        lyt.putConstraint(SpringLayout.WEST, selectAlfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, selectAlfabeto, 10, SpringLayout.SOUTH, nombreLenguaje);

        lyt.putConstraint(SpringLayout.WEST, nombreAlfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, nombreAlfabeto, 10, SpringLayout.SOUTH, selectAlfabeto);
        lyt.putConstraint(SpringLayout.WEST, nombreAlfabetoInput, 10, SpringLayout.EAST, nombreAlfabeto);
        lyt.putConstraint(SpringLayout.NORTH, nombreAlfabetoInput, 10, SpringLayout.SOUTH, selectAlfabeto);
        lyt.putConstraint(SpringLayout.WEST, buscarAlfabeto, 10, SpringLayout.EAST, nombreAlfabetoInput);
        lyt.putConstraint(SpringLayout.NORTH, buscarAlfabeto, 10, SpringLayout.SOUTH, selectAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, simbolosAlfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, simbolosAlfabeto, 10, SpringLayout.SOUTH, buscarAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, simbolosAlfabetoInput, 10, SpringLayout.EAST, simbolosAlfabeto);
        lyt.putConstraint(SpringLayout.NORTH, simbolosAlfabetoInput, 10, SpringLayout.SOUTH, buscarAlfabeto);
        lyt.putConstraint(SpringLayout.EAST, simbolosAlfabetoInput, 450, SpringLayout.WEST, simbolosAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, selectTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, selectTexto, 10, SpringLayout.SOUTH, simbolosAlfabetoInput);

        lyt.putConstraint(SpringLayout.WEST, nombreTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, nombreTexto, 10, SpringLayout.SOUTH, selectTexto);
        lyt.putConstraint(SpringLayout.WEST, nombreTextoInput, 10, SpringLayout.EAST, nombreTexto);
        lyt.putConstraint(SpringLayout.NORTH, nombreTextoInput, 10, SpringLayout.SOUTH, selectTexto);
        lyt.putConstraint(SpringLayout.WEST, buscarTexto, 10, SpringLayout.EAST, nombreTextoInput);
        lyt.putConstraint(SpringLayout.NORTH, buscarTexto, 10, SpringLayout.SOUTH, selectTexto);

        lyt.putConstraint(SpringLayout.WEST, contenidoTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, contenidoTexto, 10, SpringLayout.SOUTH, buscarTexto);

        lyt.putConstraint(SpringLayout.WEST, contenidoTextoInput, 10, SpringLayout.EAST, contenidoTexto);
        lyt.putConstraint(SpringLayout.NORTH, contenidoTextoInput, 10, SpringLayout.SOUTH, buscarTexto);
        lyt.putConstraint(SpringLayout.EAST, contenidoTextoInput, 450, SpringLayout.WEST, simbolosAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, textoEstandarRadioButton, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, textoEstandarRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, textoFrecuenciaRadioButton, 10,
                SpringLayout.EAST, textoEstandarRadioButton);
        lyt.putConstraint(SpringLayout.NORTH, textoFrecuenciaRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, crearLenguaje, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, crearLenguaje, 10, SpringLayout.SOUTH, textoFrecuenciaRadioButton);

        lyt.putConstraint(SpringLayout.WEST, returnButton, 10, SpringLayout.EAST, crearLenguaje);
        lyt.putConstraint(SpringLayout.NORTH, returnButton, 0, SpringLayout.NORTH, crearLenguaje);
    }

    //funcion que configura el boton de buscar texto
    private void configBuscarTexto() {
        buscarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(VistaCrearLenguaje.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    contenidoTextoInput.setText(selectedFile.getAbsolutePath());
                    nombreTextoInput.setText(selectedFile.getName());
                }
            }
        });
    }

    //funcion que configura el boton de buscar alfabeto
    private void configBuscarAlfabeto() {
        buscarAlfabeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(VistaCrearLenguaje.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    simbolosAlfabetoInput.setText(selectedFile.getAbsolutePath());
                    nombreAlfabetoInput.setText(selectedFile.getName());
                }
            }
        });
    }

    //funcion que configura los radio buttons del tipo de texto
    private void configTipoTexto() {
        selectTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectTexto.getSelectedIndex() == 0) {
                    buscarTexto.setEnabled(false);
                    contenidoTextoInput.setEnabled(true);
                    nombreTextoInput.setEnabled(true);
                    contenidoTexto.setText("Contenido del texto:");
                    nombreTextoInput.setText("");
                    contenidoTextoInput.setText("");
                } else {
                    buscarTexto.setEnabled(true);
                    contenidoTextoInput.setEnabled(false);
                    nombreTextoInput.setEnabled(false);
                    contenidoTexto.setText("Ruta donde se encuentra el texto:");
                    nombreTextoInput.setText("");
                    contenidoTextoInput.setText("");
                }
            }
        });
    }

    //funcion que configura el select del alfabeto
    private void configSelectAlfabeto() {
        selectAlfabeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = selectAlfabeto.getSelectedIndex();
                if (selectedIndex == 0) {
                    buscarAlfabeto.setEnabled(false);
                    simbolosAlfabetoInput.setEnabled(true);
                    nombreAlfabetoInput.setEnabled(true);
                    simbolosAlfabeto.setText("Símbolos del alfabeto:");
                    simbolosAlfabetoInput.setText("");
                    nombreAlfabetoInput.setText("");
                    limitarCaracteresAlfabeto();
                } else if (selectedIndex == 1) {
                    buscarAlfabeto.setEnabled(true);
                    simbolosAlfabetoInput.setEnabled(false);
                    nombreAlfabetoInput.setEnabled(true);
                    simbolosAlfabeto.setText("Ruta donde se encuentra el alfabeto:");
                    simbolosAlfabetoInput.setText("");
                    nombreAlfabetoInput.setText("");
                    quitarLimiteCaracteresAlfabeto();
                } else if (selectedIndex == 2) {
                    quitarLimiteCaracteresAlfabeto();
                    buscarAlfabeto.setEnabled(false);
                    simbolosAlfabetoInput.setEnabled(false);
                    nombreAlfabetoInput.setEnabled(false);

                    List<List<String>> alfabetosPredefinidos = cp.getAlfabetosPredefinidos();
                    String[] opcionesAlfabetos = new String[alfabetosPredefinidos.size()];

                    for (int i = 0; i < alfabetosPredefinidos.size(); i++) {
                        opcionesAlfabetos[i] = alfabetosPredefinidos.get(i).get(0);
                    }

                    String alfabetoSeleccionado = (String) JOptionPane.showInputDialog(
                            VistaCrearLenguaje.this,
                            "Selecciona un alfabeto predefinido:",
                            "Seleccionar Alfabeto",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcionesAlfabetos,
                            opcionesAlfabetos[0]
                    );

                    if (alfabetoSeleccionado != null) {
                        int indiceSeleccionado = Arrays.asList(opcionesAlfabetos).indexOf(alfabetoSeleccionado);
                        List<String> alfabetoSeleccionadoInfo = alfabetosPredefinidos.get(indiceSeleccionado);

                        nombreAlfabetoInput.setText(alfabetoSeleccionadoInfo.get(0));
                        simbolosAlfabetoInput.setText(alfabetoSeleccionadoInfo.get(1));
                    }
                    simbolosAlfabeto.setText("Símbolos del alfabeto:");
                }
            }
        });
    }

    private void configCrearLenguaje() {
        crearLenguaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreLenguaje = nombreLenguajeInput.getText();
                List<String> datosAlfabeto = obtenerDatosAlfabeto();
                List<String> datosTexto = obtenerDatosTexto();
                try {
                    cp.crearLenguaje(nombreLenguaje, datosAlfabeto, datosTexto);
                    DialogSuccess.show("Lenguaje creado correctamente, pulsa OK para volver al menú principal");
                    cp.runVistaPrincipal();
                    dispose();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    DialogError.show(ex.getMessage());
                }
            }
        });
    }

    private List<String> obtenerDatosTexto() {
        List<String> datosTexto = new ArrayList<>();
        if (selectTexto.getSelectedIndex() == 0)
            datosTexto.add(nombreTextoInput.getText());
        datosTexto.add(contenidoTextoInput.getText());
        datosTexto.add((textoEstandarRadioButton.isSelected()) ? "estandar" : "frecuencia");
        return datosTexto;
    }

    private List<String> obtenerDatosAlfabeto() {
        List<String> datosAlfabeto = new ArrayList<>();
        datosAlfabeto.add((selectAlfabeto.getSelectedIndex() == 0 || selectAlfabeto.getSelectedIndex() == 2)
                ? "manual" : "archivo");
        datosAlfabeto.add(nombreAlfabetoInput.getText());
        datosAlfabeto.add(simbolosAlfabetoInput.getText());
        return datosAlfabeto;
    }

    private void configReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.runVistaPrincipal();
                dispose();
            }
        });
    }
}
