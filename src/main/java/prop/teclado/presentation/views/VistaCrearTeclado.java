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
 * Vista de crear un teclado
 * Author: Joan Martínez
 */
public class VistaCrearTeclado extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel titulo = new JLabel("Crear un teclado");
    private JLabel nombreTeclado = new JLabel("Nombre del teclado:");
    private JTextField nombreTecladoInput = new JTextField(20);
    private JComboBox<String> selectLenguaje = new JComboBox<>(new String[]{"Ingresar lenguaje manual", "Elegir lenguaje"});
    private JLabel nombreLenguaje = new JLabel("Nombre del lenguaje:");
    private JTextField nombreLenguajeInput = new JTextField(20);
    private JLabel alfabeto = new JLabel("Nombre del alfabeto:");
    private JTextField alfabetoInput = new JTextField(20);
    private JLabel contenidoAlfabeto = new JLabel("Contenido del alfabeto * :");
    private JTextField contenidoAlfabetoInput = new JTextField(20);
    private JComboBox<String> selectAlfabeto = new JComboBox<>(new String[]{"Ingresar manualmente", "Seleccionar desde archivo", "Seleccionar alfabeto predefinido"});
    private JButton buscarAlfabeto = new JButton("Buscar alfabeto");
    private JComboBox<String> selectTexto = new JComboBox<>(new String[]{"Ingresar manualmente", "Seleccionar desde archivo"});
    private JRadioButton textoEstandarRadioButton = new JRadioButton("Texto Estandar");
    private JRadioButton textoFrecuenciaRadioButton = new JRadioButton("Texto de Frecuencia");
    private ButtonGroup radioButtonGrupoTexto = new ButtonGroup();
    private JLabel texto = new JLabel("Nombre del texto:");
    private JTextField textoInput = new JTextField(20);
    private JLabel contenidoTexto = new JLabel("Contenido del texto * :");
    private JTextField contenidoTextoInput = new JTextField(100);
    private JButton buscarTexto = new JButton("Buscar texto");
    private JButton crearTeclado = new JButton("Crear teclado");
    private JButton returnButton = new JButton("Return to main menu");
    private SpringLayout lyt = new SpringLayout();
    private CtrPresentation cp;

    //funcion creadora que mustra la vista de crear teclado por pantalla
    public VistaCrearTeclado(CtrPresentation cp) {
        super("Aplicacion de gestion de teclados de PROP");
        this.cp = cp;
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(lyt);

        configSelectLenguaje();

        configSelectAlfabeto();
        configBuscarAlfabeto();

        configBuscarTexto();
        configTipoTexto();
        configCrearTeclado();
        configReturnButton();

        limitarCaracteresAlfabeto();

        addPanel();

        alfabetoInput.setEnabled(true);
        buscarAlfabeto.setEnabled(false);

        textoInput.setEnabled(true);
        buscarTexto.setEnabled(false);

        posicionarElementos();

        add(panel);
        setVisible(true);
    }


    //funcion que limita los caracteres que se pueden introducir en un JTextField de alfabeto
    private void limitarCaracteresAlfabeto() {
        contenidoAlfabetoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (contenidoAlfabetoInput.getText().length() == 50) {
                    e.consume();
                }
            }
        });
    }

    //funcion que quita el limite de caracteres que se pueden introducir en un JTextField de alfabeto
    private void quitarLimiteCaracteresAlfabeto() {
        contenidoAlfabetoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (contenidoAlfabetoInput.getText().length() == Integer.MAX_VALUE) {
                    e.consume();
                }
            }
        });
    }

    //funcion privada que se encarga de añadir los elementos al panel
    private void addPanel() {
        panel.add(titulo);
        panel.add(nombreTeclado);
        panel.add(nombreTecladoInput);
        panel.add(selectLenguaje);
        panel.add(nombreLenguaje);
        panel.add(nombreLenguajeInput);
        panel.add(selectAlfabeto);
        panel.add(alfabeto);
        panel.add(alfabetoInput);
        panel.add(contenidoAlfabeto);
        panel.add(contenidoAlfabetoInput);
        panel.add(buscarAlfabeto);
        panel.add(textoEstandarRadioButton);
        panel.add(textoFrecuenciaRadioButton);
        panel.add(selectTexto);
        panel.add(texto);
        panel.add(textoInput);
        panel.add(contenidoTexto);
        panel.add(contenidoTextoInput);
        panel.add(buscarTexto);
        panel.add(returnButton);
        panel.add(crearTeclado);
        radioButtonGrupoTexto.add(textoEstandarRadioButton);
        textoEstandarRadioButton.setSelected(true);
        radioButtonGrupoTexto.add(textoFrecuenciaRadioButton);
    }

    //funcion privada que se encarga de posicionar los elementos en el panel
    private void posicionarElementos() {
        lyt.putConstraint(SpringLayout.WEST, titulo, 10, SpringLayout.HORIZONTAL_CENTER, panel);
        lyt.putConstraint(SpringLayout.NORTH, titulo, 10, SpringLayout.NORTH, panel);

        lyt.putConstraint(SpringLayout.WEST, nombreTeclado, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, nombreTeclado, 10, SpringLayout.SOUTH, titulo);
        lyt.putConstraint(SpringLayout.WEST, nombreTecladoInput, 10, SpringLayout.EAST, nombreTeclado);
        lyt.putConstraint(SpringLayout.NORTH, nombreTecladoInput, 10, SpringLayout.SOUTH, titulo);

        lyt.putConstraint(SpringLayout.WEST, selectLenguaje, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, selectLenguaje, 10, SpringLayout.SOUTH, nombreTeclado);

        lyt.putConstraint(SpringLayout.WEST, nombreLenguaje, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, nombreLenguaje, 10, SpringLayout.SOUTH, selectLenguaje);
        lyt.putConstraint(SpringLayout.WEST, nombreLenguajeInput, 10, SpringLayout.EAST, nombreLenguaje);
        lyt.putConstraint(SpringLayout.NORTH, nombreLenguajeInput, 10, SpringLayout.SOUTH, selectLenguaje);

        lyt.putConstraint(SpringLayout.WEST, selectAlfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, selectAlfabeto, 10, SpringLayout.SOUTH, nombreLenguaje);

        lyt.putConstraint(SpringLayout.WEST, alfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, alfabeto, 10, SpringLayout.SOUTH, selectAlfabeto);
        lyt.putConstraint(SpringLayout.WEST, alfabetoInput, 10, SpringLayout.EAST, alfabeto);
        lyt.putConstraint(SpringLayout.NORTH, alfabetoInput, 10, SpringLayout.SOUTH, selectAlfabeto);
        lyt.putConstraint(SpringLayout.WEST, buscarAlfabeto, 10, SpringLayout.EAST, alfabetoInput);
        lyt.putConstraint(SpringLayout.NORTH, buscarAlfabeto, 10, SpringLayout.SOUTH, selectAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, contenidoAlfabeto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, contenidoAlfabeto, 10, SpringLayout.SOUTH, buscarAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, contenidoAlfabetoInput, 10, SpringLayout.EAST, contenidoAlfabeto);
        lyt.putConstraint(SpringLayout.NORTH, contenidoAlfabetoInput, 10, SpringLayout.SOUTH, buscarAlfabeto);
        lyt.putConstraint(SpringLayout.EAST, contenidoAlfabetoInput, 450, SpringLayout.WEST, contenidoAlfabeto);

        lyt.putConstraint(SpringLayout.WEST, selectTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, selectTexto, 10, SpringLayout.SOUTH, contenidoAlfabetoInput);

        lyt.putConstraint(SpringLayout.WEST, texto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, texto, 10, SpringLayout.SOUTH, selectTexto);
        lyt.putConstraint(SpringLayout.WEST, textoInput, 10, SpringLayout.EAST, texto);
        lyt.putConstraint(SpringLayout.NORTH, textoInput, 10, SpringLayout.SOUTH, selectTexto);
        lyt.putConstraint(SpringLayout.WEST, buscarTexto, 10, SpringLayout.EAST, textoInput);
        lyt.putConstraint(SpringLayout.NORTH, buscarTexto, 10, SpringLayout.SOUTH, selectTexto);

        lyt.putConstraint(SpringLayout.WEST, contenidoTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, contenidoTexto, 10, SpringLayout.SOUTH, buscarTexto);

        lyt.putConstraint(SpringLayout.WEST, contenidoTextoInput, 10, SpringLayout.EAST, contenidoTexto);
        lyt.putConstraint(SpringLayout.NORTH, contenidoTextoInput, 10, SpringLayout.SOUTH, buscarTexto);
        lyt.putConstraint(SpringLayout.EAST, contenidoTextoInput, 450, SpringLayout.WEST, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, textoEstandarRadioButton, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, textoEstandarRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, textoFrecuenciaRadioButton, 10,
                SpringLayout.EAST, textoEstandarRadioButton);
        lyt.putConstraint(SpringLayout.NORTH, textoFrecuenciaRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, crearTeclado, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, crearTeclado, 10, SpringLayout.SOUTH, textoFrecuenciaRadioButton);

        lyt.putConstraint(SpringLayout.WEST, returnButton, 10, SpringLayout.EAST, crearTeclado);
        lyt.putConstraint(SpringLayout.NORTH, returnButton, 0, SpringLayout.NORTH, crearTeclado);
    }

    //funcion privada que se encarga de obtener los datos del alfabeto a la hora de crear un teclado
    private List<String> obtenerDatosAlfabeto() {
        List<String> datosAlfabeto = new ArrayList<>();
        datosAlfabeto.add((selectAlfabeto.getSelectedIndex() == 0 || selectAlfabeto.getSelectedIndex() == 2)
                ? "manual" : "archivo");     //si el alfabeto es manual o predefinido, se guarda manual, si no, archivo
        datosAlfabeto.add(alfabetoInput.getText());
        datosAlfabeto.add(contenidoAlfabetoInput.getText());
        return datosAlfabeto;
    }

    //funcion privada que se encarga de configurar el select del alfabeto
    private void configSelectAlfabeto() {
        selectAlfabeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = selectAlfabeto.getSelectedIndex();
                if (selectedIndex == 0) {
                    buscarAlfabeto.setEnabled(false);
                    contenidoAlfabetoInput.setEnabled(true);
                    alfabetoInput.setEnabled(true);
                    contenidoAlfabeto.setText("Contenido del alfabeto:");
                    contenidoAlfabetoInput.setText("");
                    alfabetoInput.setText("");
                    limitarCaracteresAlfabeto();
                } else if (selectedIndex == 1) {
                    buscarAlfabeto.setEnabled(true);
                    contenidoAlfabetoInput.setEnabled(false);
                    alfabetoInput.setEnabled(true);
                    contenidoAlfabeto.setText("Ruta donde se encuentra el alfabeto:");
                    contenidoAlfabetoInput.setText("");
                    alfabetoInput.setText("");
                    quitarLimiteCaracteresAlfabeto();
                } else if (selectedIndex == 2) {
                    quitarLimiteCaracteresAlfabeto();
                    buscarAlfabeto.setEnabled(false);
                    contenidoAlfabetoInput.setEnabled(false);
                    alfabetoInput.setEnabled(false);

                    List<List<String>> alfabetosPredefinidos = cp.getAlfabetosPredefinidos();
                    String[] opcionesAlfabetos = new String[alfabetosPredefinidos.size()];

                    for (int i = 0; i < alfabetosPredefinidos.size(); i++) {
                        opcionesAlfabetos[i] = alfabetosPredefinidos.get(i).get(0);
                    }

                    String alfabetoSeleccionado = (String) JOptionPane.showInputDialog(
                            VistaCrearTeclado.this,
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

                        alfabetoInput.setText(alfabetoSeleccionadoInfo.get(0));
                        contenidoAlfabetoInput.setText(alfabetoSeleccionadoInfo.get(1));
                    }
                    contenidoAlfabeto.setText("Contenido del alfabeto:");
                }
            }
        });
    }

    //funcion que configura el select del lenguaje
    public void configSelectLenguaje() {
        selectLenguaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = selectLenguaje.getSelectedIndex();
                if (selectedIndex == 0) {
                    nombreLenguajeInput.setEnabled(true);
                    nombreLenguaje.setEnabled(true);
                    selectAlfabeto.setEnabled(true);
                    selectAlfabeto.setSelectedIndex(0);
                    alfabeto.setEnabled(true);
                    alfabetoInput.setEnabled(true);
                    contenidoAlfabeto.setEnabled(true);
                    contenidoAlfabetoInput.setEnabled(true);
                    buscarAlfabeto.setEnabled(false);
                    selectTexto.setEnabled(true);
                    selectTexto.setSelectedIndex(0);
                    texto.setEnabled(true);
                    textoInput.setEnabled(true);
                    contenidoTexto.setEnabled(true);
                    contenidoTextoInput.setEnabled(true);
                    buscarTexto.setEnabled(false);
                    //desbloquea los radio buttons
                    textoEstandarRadioButton.setEnabled(true);
                    textoFrecuenciaRadioButton.setEnabled(true);

                    //limpia los campos
                    nombreLenguajeInput.setText("");
                    alfabetoInput.setText("");
                    contenidoAlfabetoInput.setText("");
                    textoInput.setText("");
                    contenidoTextoInput.setText("");
                    textoEstandarRadioButton.setSelected(true);

                } else if (selectedIndex == 1) {
                    nombreLenguajeInput.setEnabled(false);
                    nombreLenguaje.setEnabled(false);
                    selectAlfabeto.setEnabled(false);
                    selectAlfabeto.setSelectedIndex(0);
                    alfabeto.setEnabled(false);
                    alfabetoInput.setEnabled(false);
                    contenidoAlfabeto.setEnabled(false);
                    contenidoAlfabetoInput.setEnabled(false);
                    buscarAlfabeto.setEnabled(false);
                    selectTexto.setEnabled(false);
                    selectTexto.setSelectedItem(0);
                    texto.setEnabled(false);
                    textoInput.setEnabled(false);
                    contenidoTexto.setEnabled(false);
                    contenidoTextoInput.setEnabled(false);
                    buscarTexto.setEnabled(false);
                    //bloquea los radio buttons
                    textoEstandarRadioButton.setEnabled(false);
                    textoFrecuenciaRadioButton.setEnabled(false);
                    List<List<String>> lenguajes = cp.getDatosLenguajes();
                    String[] opcionesLenguajes = new String[lenguajes.size()];

                    for (int i = 0; i < lenguajes.size(); i++) {
                        //obtenemos solo los nombres
                        opcionesLenguajes[i] = lenguajes.get(i).get(0);
                    }

                    String lenguajeSeleccionado = (String) JOptionPane.showInputDialog(
                            VistaCrearTeclado.this,
                            "Selecciona un lenguaje:",
                            "Seleccionar Lenguaje",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcionesLenguajes,
                            opcionesLenguajes[0]
                    );

                    if (lenguajeSeleccionado != null) {
                        int indiceSeleccionado = Arrays.asList(opcionesLenguajes).indexOf(lenguajeSeleccionado);
                        List<String> lenguajeSeleccionadoInfo = lenguajes.get(indiceSeleccionado);

                        nombreLenguajeInput.setText(lenguajeSeleccionadoInfo.get(0));
                        alfabetoInput.setText(lenguajeSeleccionadoInfo.get(1));
                        contenidoAlfabetoInput.setText(lenguajeSeleccionadoInfo.get(2));
                        textoInput.setText(lenguajeSeleccionadoInfo.get(3));
                        contenidoTextoInput.setText(lenguajeSeleccionadoInfo.get(4));
                        //selecciona un radio button u otro dependiendo del tipo de texto
                        if (lenguajeSeleccionadoInfo.get(5).equals("ESTANDAR")) {
                            textoEstandarRadioButton.setSelected(true);
                        } else {
                            textoFrecuenciaRadioButton.setSelected(true);
                        }
                    }
                }
            }
        });
    }

    //configura el boton de buscar alfabeto
    private void configBuscarAlfabeto() {
        buscarAlfabeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(VistaCrearTeclado.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    contenidoAlfabetoInput.setText(selectedFile.getAbsolutePath());
                    alfabetoInput.setText(selectedFile.getName());
                }
            }
        });
    }

    //funcion privada que se encarga de configurar el boton de buscar texto
    private void configBuscarTexto() {
        buscarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(VistaCrearTeclado.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    contenidoTextoInput.setText(selectedFile.getAbsolutePath());
                    textoInput.setText(selectedFile.getName());
                }
            }
        });
    }

    //funcion privada que se encarga de la configuracion de los texto
    private void configTipoTexto() {
        selectTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectTexto.getSelectedIndex() == 0) {
                    buscarTexto.setEnabled(false);
                    contenidoTextoInput.setEnabled(true);
                    textoInput.setEnabled(true);
                    contenidoTexto.setText("Contenido del texto:");
                    textoInput.setText("");
                    contenidoTextoInput.setText("");
                } else {
                    buscarTexto.setEnabled(true);
                    contenidoTextoInput.setEnabled(false);
                    textoInput.setEnabled(false);
                    contenidoTexto.setText("Ruta donde se encuentra el texto:");
                    textoInput.setText("");
                    contenidoTextoInput.setText("");
                }
            }
        });
    }

    //funcion privada que se encarga de la configuracion del boton de crear teclado
    private void configCrearTeclado() {
        crearTeclado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreTeclado = nombreTecladoInput.getText();
                String nombreLenguaje = nombreLenguajeInput.getText();
                List<String> datosAlfabeto = obtenerDatosAlfabeto();
                List<String> datosTexto = obtenerDatosTexto();
                try {
                    //si el indice de seleccion del lenguaje es 0, se guarda el lenguaje
                    if (selectLenguaje.getSelectedIndex() == 0) {
                        cp.crearTeclado(nombreTeclado, nombreLenguaje, datosAlfabeto, datosTexto, true);
                    } else
                        cp.crearTeclado(nombreTeclado, nombreLenguaje, datosAlfabeto, datosTexto, false);
                    DialogSuccess.show("Teclado creado correctamente, pulsa OK para " +
                            "volver al menu principal"); //dialogo de exito al crear un teclado
                    cp.runVistaPrincipal();
                    dispose();          //cuando el dialogo se cierra, se cierra la vista de crear teclado y
                                        //se vuelve a la vista principal
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    DialogError.show(ex.getMessage()); //dialogo de error al crear un teclado
                }
            }
        });
    }

    //funcion privada que se encarga de obtener los datos del texto a la hora de crear un teclado
    private List<String> obtenerDatosTexto() {
        //de momento el nombre del texto es el nombre del archivo en el caso de que se seleccione un archivo
        List<String> datosTexto = new ArrayList<>();
        if (selectTexto.getSelectedIndex() == 0)
            datosTexto.add(textoInput.getText());
        datosTexto.add(contenidoTextoInput.getText());
        datosTexto.add((textoEstandarRadioButton.isSelected()) ? "estandar" : "frecuencia");
        return datosTexto;
    }

    //funcion privada que se encarga de la configuracion del boton de volver atras (pagina principal)
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