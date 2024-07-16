package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.domain.classes.functions.ReadFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un texto con sus palabras y su tamaño(tamano).
 * Author: Joan Martínez Soria
 */

public abstract class Texto {

    public enum TipoTexto {
        ESTANDAR,
        FRECUENCIA
    }

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private String nombre; // nombre del texto

    private String texto; // texto en si
    private List<PalabraEnTexto> palabrasEnTexto; // lista de palabras que hay en el texto
    private List<Lenguaje> lenguajes; // lista de lenguajes que contiene el texto
    private TipoTexto type; // tipo de texto (normal o frecuencia)

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    public Texto(String nombre, String texto, TipoTexto type) {
        this.nombre = nombre == null || nombre.isBlank() ? "TextoDefault" : nombre;
        this.texto = texto;
        this.palabrasEnTexto = new ArrayList<>();
        this.lenguajes = new ArrayList<>();
        this.type = type;
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------

    // Función que comprueba si 2 textos son iguales, no es equivalente a .equals()
    public boolean textosIguales(Texto t2) {
        if (this == null || t2 == null) {
            return false;
        }

        // Comprobar nombres
        if (!this.getNombre().equals(t2.getNombre())) {
            t2.setNombre("New " + t2.getNombre());
            return false;
        }

        // Comprobar tamaño
        if (this.getSize() != t2.getSize()) {
            t2.setNombre("New " + t2.getNombre());
            return false;
        }

        // Comprobar tipo
        if (!this.getType().equals(t2.getType())) {
            t2.setNombre("New " + t2.getNombre());
            return false;
        }

        // Comprobar nombres de palabras en el texto
        List<PalabraEnTexto> palabras1 = this.getPalabrasEnTexto();
        List<PalabraEnTexto> palabras2 = t2.getPalabrasEnTexto();

        if (palabras1.size() != palabras2.size()) {
            t2.setNombre("New " + t2.getNombre());
            return false;
        }

        for (int i = 0; i < palabras1.size(); i++) {
            String nombrePalabra1 = palabras1.get(i).getPalabra().getNombre();
            String nombrePalabra2 = palabras2.get(i).getPalabra().getNombre();
            if (!nombrePalabra1.equals(nombrePalabra2)) {
                t2.setNombre("New " + t2.getNombre());
                return false;
            }
        }
        return true;
    }

    //Funcion para crear un texto
    public abstract Texto crearTexto(String nombre, Alfabeto alfabeto, String texto)
            throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias;

    // Crear texto desde un archivo
    public Texto crearTextoFichero(String path, Alfabeto alfabeto, TipoTexto tipo)
            throws NoTxt, NoPalabrasEnTexto, FileNotFound, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        String texto = ReadFile.readFile(path);
        File archivo = new File(path);  //necesario para obtener el nombre del archivo
        if (texto.isBlank() || texto.isEmpty()) {
            throw new NoPalabrasEnTexto();
        }

        if (tipo == TipoTexto.ESTANDAR) {
            return new TextoEstandar(archivo.getName(), texto).crearTexto(archivo.getName(), alfabeto, texto);
        } else {
            return new TextoFrecuencias(archivo.getName(), texto).crearTexto(archivo.getName(), alfabeto, texto);
        }
    }

    //Elimina un lenguaje de la lista de lenguajes de un texto
    public void eliminateLenguaje(Lenguaje lenguaje) {
        lenguajes.remove(lenguaje);
    }

    public abstract int[][] calcularMatrizParDeSimbolosTexto(Texto textoT, String textoS, Alfabeto alfabeto);

    // ------------------------------------------ GETTERS ------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public String getTexto() {
        return texto;
    }

    public int getSize() {
        int size = 0;
        for (PalabraEnTexto palabraEnTexto : palabrasEnTexto) {
            size += palabraEnTexto.getNumApariciones();
        }
        return size;
    }

    public List<Lenguaje> getLenguajes() {
        return lenguajes;
    }

    public List<PalabraEnTexto> getPalabrasEnTexto() {
        return palabrasEnTexto;
    }

    public PalabraEnTexto getPalabraEnTextoByPalabra(String palabra) {
        for (PalabraEnTexto palabraEnTexto : palabrasEnTexto) {
            if (palabraEnTexto.getPalabra().getNombre().equals(palabra)) {
                return palabraEnTexto;
            }
        }
        return null;
    }

    public TipoTexto getType() {
        return type;
    }

    // ------------------------------------------ SETTERS ------------------------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addLenguaje(Lenguaje lenguaje) {
        lenguajes.add(lenguaje);
    }

    public void addPalabraEnTexto(PalabraEnTexto palabraEnTexto) {
        palabrasEnTexto.add(palabraEnTexto);
    }


}
