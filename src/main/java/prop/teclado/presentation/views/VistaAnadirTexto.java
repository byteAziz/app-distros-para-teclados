package prop.teclado.presentation.views;

import prop.teclado.domain.classes.exceptions.NoPalabrasEnTexto;
import prop.teclado.domain.classes.exceptions.SimboloNoPerteneceAlAlfabeto;
import prop.teclado.domain.classes.exceptions.WrongTextoFrequencias;
import prop.teclado.presentation.controllers.CtrPresentation;
import prop.teclado.presentation.views.components.DialogError;
import prop.teclado.presentation.views.components.DialogSuccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Vista de añadir un texto al lenguaje
 * Author: Guillem Angulo
 */
public class VistaAnadirTexto extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel titulo = new JLabel("Agregar un texto al lenguaje");
    private JComboBox<String> selectTexto = new JComboBox<>(new String[]{"Ingresar manualmente", "Seleccionar desde archivo"});
    private JRadioButton textoEstandarRadioButton = new JRadioButton("Texto Estandar");
    private JRadioButton textoFrecuenciaRadioButton = new JRadioButton("Texto de Frecuencia");
    private ButtonGroup radioButtonGrupoTexto = new ButtonGroup();
    private JLabel texto = new JLabel("Nombre del texto:");
    private JTextField textoInput = new JTextField(20);
    private JLabel contenidoTexto = new JLabel("Contenido del texto * :");
    private JTextField contenidoTextoInput = new JTextField(20);
    private JButton buscarTexto = new JButton("Buscar texto");
    private JButton anadirTexto = new JButton("Agregar texto");
    private JButton returnButton = new JButton("Return to main menu");
    private SpringLayout lyt = new SpringLayout();
    private CtrPresentation cp;

    private String teclado;


    public VistaAnadirTexto(CtrPresentation cp, String teclado) {
        super("Aplicacion de gestion de teclados de PROP");
        this.cp = cp;
        this.teclado = teclado;
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(lyt);


        configBuscarTexto();
        configTipoTexto();
        configAnadir();

        configReturnButton();

        addPanel();


        textoInput.setEnabled(true);
        buscarTexto.setEnabled(false);

        posicionarElementos();

        add(panel);
        setVisible(true);
    }

    //funcion privada que se encarga de añadir los elementos al panel
    private void addPanel() {
        panel.add(titulo);
        panel.add(textoEstandarRadioButton);
        panel.add(textoFrecuenciaRadioButton);
        panel.add(selectTexto);
        panel.add(texto);
        panel.add(textoInput);
        panel.add(contenidoTexto);
        panel.add(contenidoTextoInput);
        panel.add(buscarTexto);
        panel.add(returnButton);
        panel.add(anadirTexto);
        radioButtonGrupoTexto.add(textoEstandarRadioButton);
        textoEstandarRadioButton.setSelected(true);
        radioButtonGrupoTexto.add(textoFrecuenciaRadioButton);
    }

    //funcion privada que se encarga de posicionar los elementos en el panel
    private void posicionarElementos() {
        lyt.putConstraint(SpringLayout.WEST, titulo, 10, SpringLayout.HORIZONTAL_CENTER, panel);
        lyt.putConstraint(SpringLayout.NORTH, titulo, 10, SpringLayout.NORTH, panel);

        lyt.putConstraint(SpringLayout.WEST, selectTexto, 10, SpringLayout.WEST, panel);

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

        lyt.putConstraint(SpringLayout.WEST, textoEstandarRadioButton, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, textoEstandarRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, textoFrecuenciaRadioButton, 10,
                SpringLayout.EAST, textoEstandarRadioButton);
        lyt.putConstraint(SpringLayout.NORTH, textoFrecuenciaRadioButton, 10, SpringLayout.SOUTH, contenidoTexto);

        lyt.putConstraint(SpringLayout.WEST, anadirTexto, 10, SpringLayout.WEST, panel);
        lyt.putConstraint(SpringLayout.NORTH, anadirTexto, 10, SpringLayout.SOUTH, textoFrecuenciaRadioButton);

        lyt.putConstraint(SpringLayout.WEST, returnButton, 10, SpringLayout.EAST, anadirTexto);
        lyt.putConstraint(SpringLayout.NORTH, returnButton, 0, SpringLayout.NORTH, anadirTexto);
    }


    //funcion privada que se encarga de la configuracion del boton de buscar texto
    private void configBuscarTexto() {
        buscarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(VistaAnadirTexto.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    contenidoTextoInput.setText(selectedFile.getAbsolutePath());
                    textoInput.setText(selectedFile.getName());
                }
            }
        });
    }

    //funcion privada que se encarga de la configuracion de los RadioButton de los tipos de texto
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

    //funcion privada que se encarga de la configuracion del boton de añadir texto
    private void configAnadir() {
        anadirTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se ingresa manual
                if (selectTexto.getSelectedIndex() == 0) {
                    // texto normal
                    if (textoEstandarRadioButton.isSelected()) {
                        try {
                            cp.addNewTextoEstandar(textoInput.getText(), contenidoTextoInput.getText(), teclado);
                            DialogSuccess.show("Texto agregado correctamente al teclado " + teclado);
                            cp.runVistaPrincipal();
                            dispose();
                        } catch (NoPalabrasEnTexto | WrongTextoFrequencias | SimboloNoPerteneceAlAlfabeto ex) {
                            DialogError.show(ex.getMessage());
                        }
                    }
                    else {
                        try {
                            cp.addNewTextoFrequencia(textoInput.getText(), contenidoTextoInput.getText(), teclado);
                            DialogSuccess.show("Texto agragado correctamente al teclado " + teclado);
                            cp.runVistaPrincipal();
                            dispose();
                        } catch (Exception ex) {
                            DialogError.show(ex.getMessage());
                        }
                    }
                }
                // se ingresa por ficheros
                else {
                    try {
                        // texto normal
                        if (textoEstandarRadioButton.isSelected()) cp.addNewTextoFicheroTE(contenidoTextoInput.getText(), teclado);
                            // texto frecuencias
                        else cp.addNewTextoFicheroTF(contenidoTextoInput.getText(), teclado);
                        DialogSuccess.show("Texto agragado correctamente al teclado " + teclado);
                        cp.runVistaPrincipal();
                        dispose();
                    } catch (Exception ex) {
                        DialogError.show(ex.getMessage());
                    }
                }
            }
        });

    }
}