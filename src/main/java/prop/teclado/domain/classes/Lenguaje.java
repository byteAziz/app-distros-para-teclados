package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un lenguaje formado a partir de un alfabeto y diferentes textos.
 * Author: Joan Martínez Soria
 */

 public class Lenguaje {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private String nombre;          // nombre del lenguaje
    private Alfabeto alfabeto;      // alfabeto del lenguaje
    private List<Palabra> palabras; // lista de palabras que hay en el lenguaje
    private List<Texto> textos;     // lista de textos que hay en el lenguaje
    private int[][] frecuencias;    // matriz de frecuencias entre los simbolos del alfabeto en un lenguaje con los diferentes textos
    private boolean update;         // variable que indica si se ha actualizado la matriz de frecuencias

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    // Constructora para crear un lenguaje a partir de un texto estandar o uno de frecuencias
    public Lenguaje(String nombre, String nombreT, String texto, Alfabeto alfabeto, boolean estandar)
            throws SimboloNoPerteneceAlAlfabeto, NoPalabrasEnTexto, WrongTextoFrequencias {
        initLenguaje();
        this.nombre = checkNombre(nombre);
        this.alfabeto = alfabeto;
        Texto textoLenguaje;
        if (estandar) {
            textoLenguaje = new TextoEstandar(nombreT, texto);
            textoLenguaje = textoLenguaje.crearTexto(nombreT, getAlfabeto(), texto);
        } else {
            textoLenguaje = new TextoFrecuencias(nombreT, texto);
            textoLenguaje = textoLenguaje.crearTexto(nombreT, getAlfabeto(), texto);
        }
        finishLenguaje(textoLenguaje);
    }

    // Constructora para crear un lenguaje a partir de un fichero de texto con un texto estandar o uno de frecuencias
    public Lenguaje(String nombre, String path, Alfabeto alfabeto, boolean estandar)
            throws NoTxt, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        initLenguaje();
        this.nombre = checkNombre(nombre);
        this.alfabeto = alfabeto;
        Texto textoLenguaje;
        if (estandar) {
            textoLenguaje = new TextoEstandar("", "");
            textoLenguaje = textoLenguaje.crearTextoFichero(path, getAlfabeto(), Texto.TipoTexto.ESTANDAR);
        } else {
            textoLenguaje = new TextoFrecuencias("", "");
            textoLenguaje = textoLenguaje.crearTextoFichero(path, getAlfabeto(), Texto.TipoTexto.FRECUENCIA);
        }
        finishLenguaje(textoLenguaje);
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------


                              // ------------ FUNCIONES PRIVDAS ------------

    // Se inicializan los atributos del lenguaje
    private void initLenguaje() {
        this.palabras = new ArrayList<>();
        this.textos = new ArrayList<>();
        this.update = false;
    }

    // Si el nombre es nulo, esta vacio o en blanco, se retorna un nombre por defecto, si no, se devuelve el nombre
    private String checkNombre(String nombre) {
        return (nombre == null || nombre.isBlank()) ? "LenguajeDefault" : nombre;
    }

    // Añade un texto al lenguaje y sus palabras (nombre raro)
    private void finishLenguaje(Texto textoLenguaje) {
        this.addTexto(textoLenguaje);
        textoLenguaje.addLenguaje(this);
        for (PalabraEnTexto palabra : textoLenguaje.getPalabrasEnTexto()) {
            if (getPalabras().stream().noneMatch(p -> p.getNombre().equalsIgnoreCase(palabra.getPalabra().getNombre()))) {
                this.addPalabra(palabra.getPalabra());
            }
        }
    }

                              // ------------ FUNCIONES PUBLICAS ------------
                              
    // Se añade el texto (de tipo Estandar) al lenguaje
    public void addNewTextoEstandar(String nombreT, String texto)
            throws SimboloNoPerteneceAlAlfabeto, NoPalabrasEnTexto, WrongTextoFrequencias {
        // Creamos el texto a partir del string de palabras
        Texto textoLenguaje = new TextoEstandar(nombreT, texto);
        textoLenguaje = textoLenguaje.crearTexto(nombreT, getAlfabeto(), texto);
        // Se añade el texto al lenguaje
        finishLenguaje(textoLenguaje);
    }

    //añade un texto de frecuencias a un lenguaje existente
    public void addNewTextoFrequencia(String nombreT, String texto)
            throws SimboloNoPerteneceAlAlfabeto, NoPalabrasEnTexto, WrongTextoFrequencias {
        //creamos el texto a partir del string de palabras
        Texto textoLenguaje = new TextoFrecuencias(nombreT, texto);
        textoLenguaje = textoLenguaje.crearTexto(nombreT, getAlfabeto(), texto);
        //se añade el texto al lenguaje
        finishLenguaje(textoLenguaje);
    }

    //añade un texto estandar a un lenguaje existente a partir de un fichero
    public void addNewTextoFicheroTE(String path)
            throws NoTxt, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        //se crea el texto a partir del fichero proporcionado
        Texto textoLenguaje = new TextoEstandar("", "");
        textoLenguaje = textoLenguaje.crearTextoFichero(path, getAlfabeto(), Texto.TipoTexto.ESTANDAR);
        finishLenguaje(textoLenguaje);
        this.update = false;
    }

    //añade un texto de frecuencias a un lenguaje existente a partir de un fichero
    public void addNewTextoFicheroTF(String path)
            throws NoTxt, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        //se crea el texto a partir del fichero proporcionado
        Texto textoLenguaje = new TextoFrecuencias("", "");
        textoLenguaje = textoLenguaje.crearTextoFichero(path, getAlfabeto(), Texto.TipoTexto.FRECUENCIA);
        finishLenguaje(textoLenguaje);
        this.update = false;
    }

    //elimina un texto de un lenguaje
    public void eliminateTexto(String nombre) throws CantRemoveTexto, NoTextoFound {
        if (getTextos().size() == 1) {
            //si solo hay un texto en el lenguaje, lanza una excepcion
            throw new CantRemoveTexto();
        }
        //buscamos el texto a eliminar
        Texto texto = findTexto(nombre);
        //lo eliminamos del lenguaje
        texto.eliminateLenguaje(this);
        // Crear una lista de palabras a eliminar del lenguaje
        List<Palabra> palabrasAEliminar = new ArrayList<>();

        //buscamos en el lenguaje las palabras que aparecen en los textos del lenguaje
        for (Palabra palabra : getPalabras()) {
            for (Texto textoLenguaje : getTextos()) {
                for (PalabraEnTexto palabraEnTexto : textoLenguaje.getPalabrasEnTexto()) {
                    if (palabraEnTexto.getPalabra().getNombre().equalsIgnoreCase(palabra.getNombre())) {
                        palabrasAEliminar.add(palabra);
                    }
                }
            }
        }
        //si una palabra no aparece en ningun texto del lenguaje la eliminamos
        for (Palabra palabra : getPalabras()) {
            if (!palabrasAEliminar.contains(palabra)) {
                eliminatePalabra(palabra);
            }
        }
        //eliminamos el texto del lenguaje
        eliminateTexto(texto);
        this.update = false;
    }

    public int[][] calcularFrecuenciaParDeSimbolosPalabra() {
        Alfabeto alfabeto = getAlfabeto();
        int alfabetoSize = alfabeto.getSize();
        int[][] matrizFrecuencias = new int[alfabetoSize][alfabetoSize];
        // Iterar sobre cada palabra en el lenguaje
        for (Texto texto: getTextos()) {
            int[][] newMat;
            if (texto.getType() == Texto.TipoTexto.ESTANDAR) {
                newMat = texto.calcularMatrizParDeSimbolosTexto(null, texto.getTexto(), alfabeto);
            } else {
                newMat = texto.calcularMatrizParDeSimbolosTexto(texto, null,alfabeto);
            }
            for (int i = 0; i < alfabetoSize; i++) {
                for (int j = 0; j < alfabetoSize; j++) {
                    matrizFrecuencias[i][j] += newMat[i][j];
                }
            }
        }
        return matrizFrecuencias;
    }


    //Elimina un texto de la lista de textos de un lenguaje
    public void eliminateTexto(Texto texto) throws CantRemoveTexto {
        if (textos.size() == 1) {
            throw new CantRemoveTexto();
        }
        textos.remove(texto);
    }

    //Elimina una palabra de la lista de palabras de un lenguaje
    public void eliminatePalabra(Palabra palabra) {
        palabras.remove(palabra);
    }


    // ------------------------------------------ GETTERS ------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public int getSize() {
        return palabras.size();
    }

    public List<Palabra> getPalabras() {
        return palabras;
    }

    public List<Texto> getTextos() {
        return textos;
    }

    public Texto findTexto(String nombre) throws NoTextoFound {
        for (Texto texto : textos) {
            if (texto.getNombre().equals(nombre)) {
                return texto;
            }
        }
        throw new NoTextoFound();
    }

    public int[][] getFrecuencias() {
        if (!update) {
            frecuencias = calcularFrecuenciaParDeSimbolosPalabra();
            update = true;
        }
        return frecuencias;
    }

    public boolean getUpdate() {
        return update;
    }

    // ------------------------------------------ SETTERS ------------------------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addPalabra(Palabra palabra) {
        palabras.add(palabra);
    }
    
    public void addTexto(Texto texto) {
        textos.add(texto);
    }
}
