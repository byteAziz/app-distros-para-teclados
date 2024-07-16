package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.domain.classes.functions.PlaceFrecuenciaOnMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un texto de frequencias donde, primero encontramos la palabra, y luego su frequencia.
 * Author: Joan Martínez Soria
 */

public class TextoFrecuencias extends Texto{

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    
    public TextoFrecuencias(String nombre, String texto) {
        super(nombre, texto, TipoTexto.FRECUENCIA);
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------

    //crea un texto a partir de un string de palabras y frequencias
    public Texto crearTexto(String nombre, Alfabeto alfabeto, String textoN)
            throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        String trimmedString = textoN.trim();
        if (trimmedString.isEmpty()) {
            //si no hay palabras, lanza una excepcion
            throw new NoPalabrasEnTexto();
        } else {
            List<Integer> frequencias = new ArrayList<>();
            Palabra aux = new Palabra("", alfabeto);
            //se crean las palabras, y se calcula su frequencia a partir del string de palabras
            List<Palabra> palabras = aux.crearPalabrasConFrequenciasDesdeTexto(textoN, alfabeto, frequencias);
            //se crea el texto a partir de las palabras
            TextoFrecuencias texto = new TextoFrecuencias(nombre, textoN);
            for (Palabra palabra : palabras) {
                if (texto.getPalabrasEnTexto().contains(texto.getPalabraEnTextoByPalabra(palabra.getNombre()))) {
                    texto.getPalabraEnTextoByPalabra(palabra.getNombre()).increseNumApariciones(frequencias.get(palabras.indexOf(palabra)));
                } else {
                    texto.addPalabraEnTexto(new PalabraEnTexto(palabra, texto, frequencias.get(palabras.indexOf(palabra))));
                }
            }
            //se devuelve el texto
            return texto;
        }
    }

    public int[][] calcularMatrizParDeSimbolosTexto(Texto textoT, String textoN, Alfabeto alfabeto) {
        int alfabetoSize = alfabeto.getSize();
        int[][] matriz = new int[alfabetoSize][alfabetoSize];
        List<PalabraEnTexto> palabras = textoT.getPalabrasEnTexto();
        for (int i = 0; i < palabras.size(); i++) {
            String palabra = palabras.get(i).getPalabra().getNombre();
            palabra = palabra + " "; //se le añade el espacio al principio y al final porque no sabemos si son inicio o final de un texto
            for (int j = 0; j < palabra.length()-1; j++) {
                PlaceFrecuenciaOnMatrix.placeFrecuenciaOnMatrix(matriz, palabra.charAt(j), palabra.charAt(j + 1), alfabeto, palabras.get(i).getNumApariciones());
            }
        }
        return matriz;
    }
}
