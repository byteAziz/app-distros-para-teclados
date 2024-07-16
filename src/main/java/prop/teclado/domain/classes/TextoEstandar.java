package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.domain.classes.functions.PlaceFrecuenciaOnMatrix;

import java.util.List;

/**
 * Clase que representa un texto normal y corriente.
 * Author: Joan Martínez Soria
 */

public class TextoEstandar extends Texto{
    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    
    public TextoEstandar(String nombre, String texto) {
        super(nombre, texto, TipoTexto.ESTANDAR);
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------

    //crea un texto a partir de un string de palabras
    public Texto crearTexto(String nombre, Alfabeto alfabeto, String textoN) throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        String trimmedString = textoN.trim();
        if (trimmedString.isEmpty()) {
            //si no hay palabras, lanza una excepcion
            throw new NoPalabrasEnTexto();
        } else {
            //se crean las palabras a partir del string de palabras
            Palabra aux = new Palabra(null, null);
            List<Palabra> palabras;
            palabras = aux.crearPalabrasDesdeTexto(textoN, alfabeto);
            //se crea el texto a partir de las palabras
            Texto texto = new TextoEstandar(nombre, textoN);
            for (Palabra palabra : palabras) {
                //se crean las palabrasEnTexto a partir de las palabras y se añaden a este texto
                if (texto.getPalabrasEnTexto().contains(texto.getPalabraEnTextoByPalabra(palabra.getNombre()))) {
                    texto.getPalabraEnTextoByPalabra(palabra.getNombre()).increseNumApariciones(1);
                } else {
                    texto.addPalabraEnTexto(new PalabraEnTexto(palabra, texto));
                }
            }
            //se devuelve el texto
            return texto;
        }
    }

    public int[][] calcularMatrizParDeSimbolosTexto(Texto textoT, String textoN, Alfabeto alfabeto) {
        int alfabetoSize = alfabeto.getSize();
        textoN = textoN.toLowerCase();
        int[][] matriz = new int[alfabetoSize][alfabetoSize];
        for (int i = 0; i < textoN.length()-1; i++) {
            PlaceFrecuenciaOnMatrix.placeFrecuenciaOnMatrix(matriz, textoN.charAt(i), textoN.charAt(i + 1), alfabeto, 1);
        }
        return matriz;
    }
}
