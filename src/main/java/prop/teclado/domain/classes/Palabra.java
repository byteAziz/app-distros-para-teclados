package prop.teclado.domain.classes;

import java.util.ArrayList;
import java.util.List;

import prop.teclado.domain.classes.exceptions.SimboloNoPerteneceAlAlfabeto;
import prop.teclado.domain.classes.exceptions.WrongTextoFrequencias;
import prop.teclado.domain.classes.functions.PlaceFrecuenciaOnMatrix;

/**
 * Clase que representa una palabra con los símbolos que contiene.
 * Author: Joan Martínez Soria
 */

public class Palabra {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private String nombre;          // la palabra en si
    private Alfabeto alfabeto;      // (lista) de alfabetos a los que pertenece la palabra

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    // crea una palabra a partir de un string que contiene los símbolos
    public Palabra(String nombre, Alfabeto alfabeto) throws SimboloNoPerteneceAlAlfabeto {
        // Si la palabra contiene simbolos que no pertenecen al alfabeto, lanza excepcion
        if ((nombre != null && alfabeto != null)) {
            if (!palabraPerteneceAlAlfabeto(nombre, alfabeto))
                throw new SimboloNoPerteneceAlAlfabeto();
            this.nombre = nombre.toLowerCase();
            this.alfabeto = alfabeto;
        }
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------
    
    // crea una lista de palabras a partir de un string que contiene las palabras
    public List<Palabra> crearPalabrasDesdeTexto(String texto, Alfabeto alfabeto) throws SimboloNoPerteneceAlAlfabeto {
        List<Palabra> palabras = new ArrayList<>();

        // separa el texto (que tambien puede verse como "las palabras en el texto") en palabras sueltas
        String[] palabrasEnTexto = texto.split(" ");
        for (String palabraEnTexto : palabrasEnTexto) {
            // para cada una de las palabras sueltas, creamos una instancia de Palabra y la añadimos a la lista a devolver
            Palabra palabra = new Palabra(palabraEnTexto, alfabeto);
            palabras.add(palabra);
        }
        
        // retorna la lista de palabras
        return palabras;
    }

    // crea una lista de palabras a partir de un string que contiene las palabras y sus frecuencias
    public List<Palabra> crearPalabrasConFrequenciasDesdeTexto(String texto, Alfabeto alfabeto, List<Integer> frecuencias)
            throws SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        List<Palabra> palabras = new ArrayList<>();

        // separa el texto (que tambien puede verse como "las palabras en el texto") en palabras sueltas
        String[] palabrasEnTexto = texto.split(" ");

        // para cada par de palabras, creamos una palabra y agregamos su frecuencia a la lista de frecuencias
        // si palabrasString es impar, retorna error
        if (palabrasEnTexto.length % 2 != 0) 
            throw new WrongTextoFrequencias();

        for (int i = 0; i < palabrasEnTexto.length; i += 2) {
            Palabra palabra = new Palabra(palabrasEnTexto[i], alfabeto);
            palabras.add(palabra);

            // comprueba que el siguiente elemento sea un numero, y si lo es añade la frecuencia a la lista de frecuencias
            if (!palabrasEnTexto[i + 1].matches("[0-9]+"))
                throw new WrongTextoFrequencias();
            else
                frecuencias.add(Integer.parseInt(palabrasEnTexto[i + 1]));
        }

        // retorna la lista de palabras con la lista de frecuencias actualizada
        return palabras;
    }

    //funcion que calcula la frequencia (veces que un simbolo aparece al lado de otro) entre simbolos de una palabra
    public int[][] frequenciaEntreSimbolos (Alfabeto alfabeto) {
        int alfabetoSize = alfabeto.getSize();
        int[][] matrizDistancias = new int[alfabetoSize][alfabetoSize];
        int palabraSize = getSize();
        for (int i = 0; i < palabraSize; i++) {
            //para cada un de los simbolos de la palabra, calculamos la frequencia con el resto de simbolos
            if (i == 0) PlaceFrecuenciaOnMatrix.placeFrecuenciaOnMatrix(matrizDistancias, ' ', getNombre().charAt(i), alfabeto, 1);
            if (i < palabraSize-1)PlaceFrecuenciaOnMatrix.placeFrecuenciaOnMatrix(matrizDistancias, getNombre().charAt(i), getNombre().charAt(i+1), alfabeto, 1);
            else if (i == palabraSize-1) PlaceFrecuenciaOnMatrix.placeFrecuenciaOnMatrix(matrizDistancias, getNombre().charAt(i), ' ', alfabeto, 1);
        }
        return matrizDistancias;
    }

    public boolean palabraPerteneceAlAlfabeto(String nombre, Alfabeto alfabeto) {
        for (int i = 0; i < nombre.length(); i++) {
            if (!alfabeto.getSimbolos().contains(nombre.toLowerCase().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------ GETTERS ------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public int getSize() {
        return this.nombre.length();
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public int getApariciones(char simbolo) {
        int apariciones = 0;
        for (int i = 0; i < getSize(); i++) {
            if (getNombre().charAt(i) == simbolo) {
                apariciones++;
            }
        }
        return apariciones;
    }
}
