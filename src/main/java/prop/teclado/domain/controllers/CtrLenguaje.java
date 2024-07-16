package prop.teclado.domain.controllers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de la clase Lenguaje
 * Author: Joan Martínez Soria
 */
public class CtrLenguaje {
    private List<Lenguaje> lenguajes;

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    public CtrLenguaje(List<List<String>> lenguajesPersistencia)
            throws NoTxt, FileNotFound, WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        this.lenguajes = new ArrayList<>(); //Se deben cargar los teclados de la persistencia
        for (List<String> lenguaje : lenguajesPersistencia) {
            Lenguaje lenguajeCreado = crearLenguaje(lenguaje);
            lenguajes.add(lenguajeCreado);
        }
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------
    //funcion encargada de crear un lenguaje a partir de una lista de strings de la persistencia
    public Lenguaje crearLenguaje(List<String> lenguaje)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, NoTxt, FileNotFound {
        String nombreLenguaje = lenguaje.get(0);
        List<String> nombresTextos = new ArrayList<>();
        List<String> contenidoTextos = new ArrayList<>();
        List<String> tipoTextos = new ArrayList<>();
        for (int i = 1; i < lenguaje.size() - 3; i+=3) {
            nombresTextos.add(lenguaje.get(i));
            contenidoTextos.add(lenguaje.get(i + 1));
            tipoTextos.add(lenguaje.get(i + 2));
        }
        String nombreAlfabeto = lenguaje.get(lenguaje.size() - 2);
        String simbolosAlfabeto = lenguaje.get(lenguaje.size() - 1);
        Alfabeto alfabeto = new Alfabeto(nombreAlfabeto, simbolosAlfabeto, false);
        Lenguaje lenguajeCreado = new Lenguaje(nombreLenguaje, nombresTextos.get(0), contenidoTextos.get(0),
                alfabeto, Boolean.parseBoolean(tipoTextos.get(0)));
        for (int i = 1; i < nombresTextos.size(); i++) {
            //si el tipo de texto es de frecuencias, se crea un texto de frecuencias
            if (tipoTextos.get(i).equals("false")) {
                lenguajeCreado.addNewTextoFrequencia(nombresTextos.get(i), contenidoTextos.get(i));
            } else {
                lenguajeCreado.addNewTextoEstandar(nombresTextos.get(i), contenidoTextos.get(i));
            }
        }
        return lenguajeCreado;
    }

    //funcion que comprueba que no haya mas nombres iguales en la lista de lenguajes
    public void checkNombresLenguajes(Lenguaje lenguaje, int rep) {
        // Verifica si el nombre del lenguaje ya existe en la lista
        boolean nombreExiste = lenguajes.stream()
                .anyMatch(l -> l.getNombre().equals(lenguaje.getNombre()));

        // Si el nombre ya existe, añade un "New" al nombre del lenguaje pasado por parámetro
        if (nombreExiste) {
            lenguaje.setNombre(lenguaje.getNombre() + " (" + rep + ")");
            checkNombresLenguajes(lenguaje, ++rep); // Comprueba si el nuevo nombre ya existe
        }
    }

    //funcion que elimina un lenguaje de la lista de lenguajes
    public String eliminarLenjuage(String nombreLenguaje) {
        Lenguaje lenguaje = getLenguaje(nombreLenguaje);
        String datosLenguaje = datosLenguajePersistencia(lenguaje);
        lenguajes.remove(lenguaje);
        return datosLenguaje;
    }

    //funcion que devuelve los datos a guardar en la persistencia de un lenguaje
    public String datosLenguajePersistencia(Lenguaje lenguaje) {
        StringBuilder datosLenguaje = new StringBuilder();
        datosLenguaje.append(lenguaje.getNombre()).append("%/%/%");
        for (Texto texto : lenguaje.getTextos()) {
            datosLenguaje.append(texto.getNombre()).append("%/%/%");
            datosLenguaje.append(texto.getTexto()).append("%/%/%");
            //si el texto es de frecuencias, se añade false, si no true
            if (texto.getType().equals(Texto.TipoTexto.FRECUENCIA))
                datosLenguaje.append("false").append("%/%/%");
            else
                datosLenguaje.append("true").append("%/%/%");
        }
        datosLenguaje.append(lenguaje.getAlfabeto().getNombre()).append("%/%/%");
        for (Character simbolo : lenguaje.getAlfabeto().getSimbolos()) {
            //si es el ultimo simbolo no añadimos la coma sino el punto y coma
            datosLenguaje.append(simbolo);
        }
        return datosLenguaje.toString();
    }

    //funcion que se encarga de eliminar un texto de un lenguaje
    public void eliminarTextoTeclado(String nombreTexto, Lenguaje lenguaje)
            throws CantRemoveTexto, NoTextoFound {
        lenguaje.eliminateTexto(nombreTexto);
    }

    // ----------------------------------------- GETTERS -----------------------------------------
    //funcion que debuelve el nombre de todos los lenguajes
    public List<String> getNombresLenguajes() {
        return lenguajes.stream().map(Lenguaje::getNombre).collect(Collectors.toList());
    }

    //funcion que busca un lenguaje por su nombre
    public Lenguaje getLenguaje(String nombreLenguaje) {
        return lenguajes.stream()
                .filter(l -> nombreLenguaje.equals(l.getNombre()))
                .findFirst()
                .orElse(null);
    }

    //funcion que devuelve los datos de todos los lenguajes
    public List<List<String>> getDatosLenguajes() {
        List<List<String>> datosLenguajes = new ArrayList<>();
        for (Lenguaje lenguaje : lenguajes) {
            datosLenguajes.add(rellenarDatos(lenguaje));
        }
        return datosLenguajes;

    }

    //funcion que encapsula los datos de un lenguaje en una lista de strings
    private List<String> rellenarDatos(Lenguaje lenguaje) {
        //falta aqui la implementacion
        List<String> datosLenguaje = new ArrayList<>();
        datosLenguaje.add(lenguaje.getNombre());
        datosLenguaje.add(lenguaje.getAlfabeto().getNombre());
        StringBuilder simbolos = new StringBuilder();
        for (Character simbolo : lenguaje.getAlfabeto().getSimbolos()) {
            simbolos.append(simbolo);
        }
        datosLenguaje.add(simbolos.toString());
        datosLenguaje.add(lenguaje.getTextos().get(0).getNombre());
        datosLenguaje.add(lenguaje.getTextos().get(0).getTexto());
        datosLenguaje.add(String.valueOf(lenguaje.getTextos().get(0).getType()));
        return datosLenguaje;
    }

    // ----------------------------------------- SETTERS -----------------------------------------

    //funcion que añade un lenguaje a la lista de lenguajes
    public void addLenguaje(Lenguaje lenguaje) {
        lenguajes.add(lenguaje);
    }

    //funcion que añade un texto estandar a un lenguaje
    public void addNewTextoEstandar(String nombreT, String texto, Lenguaje lenguaje)
            throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        lenguaje.addNewTextoEstandar(nombreT, texto);
    }

    //funcion que añade un texto de frecuencias a un lenguaje
    public void addNewTextoFrequencia(String nombreT, String texto, Lenguaje lenguaje)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        lenguaje.addNewTextoFrequencia(nombreT, texto);
    }

    //funcion que añade un texto estandar a un lenguaje a partir de un fichero
    public void addNewTextoFicheroTE(String path, Lenguaje l)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        l.addNewTextoFicheroTE(path);
    }

    //funcion que añade un texto de frecuencia a un lenguaje a partir de un fichero
    public void addNewTextoFicheroTF(String path, Lenguaje l)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        l.addNewTextoFicheroTF(path);
    }
}
