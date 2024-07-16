package prop.teclado.domain.classes.functions;

import prop.teclado.domain.classes.Alfabeto;


/**
 * Funcion que se encarga de poner la frecuencia entre dos simbolos
 * dentro de la matriz segun esten los simbolos ordenados dentro del
 * alfabeto.
 * Author: Joan Martínez Soria
 */

public class PlaceFrecuenciaOnMatrix {

    public static int[][] placeFrecuenciaOnMatrix(int[][] matrix, char simbolo1, char simbolo2, Alfabeto alfabeto, int frecuencia) {
        int indexSimbolo1 = findSymbolIndex(simbolo1, alfabeto);
        int indexSimbolo2 = findSymbolIndex(simbolo2, alfabeto);

        if (indexSimbolo1 != -1 && indexSimbolo2 != -1) {
            matrix[indexSimbolo1][indexSimbolo2] += frecuencia;
            matrix[indexSimbolo2][indexSimbolo1] += frecuencia;
        }

        return matrix;
    }

    private static int findSymbolIndex(char simbolo, Alfabeto alfabeto) {
        for (int i = 0; i < alfabeto.getSize(); i++) {
            char alfabetoSimbolo = alfabeto.getSimbolos().get(i).charValue();
            if (alfabetoSimbolo == simbolo) {
                return i;
            }
        }
        return -1; // No se encontro el simbolo en el alfabeto
    }
}
