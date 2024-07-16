package prop.teclado.domain.classes;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

//  Clase que resuelve el problema de asignacion lineal (LAP) con el algoritmo húngaro
//  Autor: Tahir Muhammad Aziz

public class Hungarian {

    int[][] matriz; // matriz inicial (matriz de costos)

    // Marcadores en la matriz
    int[] cuadroEnFila, cuadroEnColumna, filaEstaCubierta, columnaEstaCubierta, cerosMarcadosEnFila;

    public Hungarian(int[][] matriz) {
        if (matriz.length != matriz[0].length) {
            try {
                throw new IllegalAccessException("¡La matriz no es cuadrada!");
            } catch (IllegalAccessException ex) {
                System.err.println(ex);
                System.exit(1);
            }
        }

        this.matriz = matriz;
        cuadroEnFila = new int[matriz.length];       // cuadroEnFila & cuadroEnColumna indican la posición
        cuadroEnColumna = new int[matriz[0].length];    // de los ceros marcados

        filaEstaCubierta = new int[matriz.length];      // indica si una fila está cubierta
        columnaEstaCubierta = new int[matriz[0].length];   // indica si una columna está cubierta
        cerosMarcadosEnFila = new int[matriz.length]; // almacenamiento para los 0*
        Arrays.fill(cerosMarcadosEnFila, -1);
        Arrays.fill(cuadroEnFila, -1);
        Arrays.fill(cuadroEnColumna, -1);
    }

    /**
     * Encuentra una asignación óptima
     *
     * @return asignación óptima
     */
    public int[][] encontrarAsignacionOptima() {
        paso1();    // reducir la matriz
        paso2();    // marcar los ceros independientes
        paso3();    // cubrir las columnas que contienen un cero marcado

        while (!todasLasColumnasEstanCubiertas()) {
            int[] ceroPrincipal = paso4();
            while (ceroPrincipal == null) {      // mientras no se encuentre ningún cero en el paso4
                paso7();
                ceroPrincipal = paso4();
            }
            if (cuadroEnFila[ceroPrincipal[0]] == -1) {
                // no hay marca de cuadro en la línea del ceroPrincipal
                paso6(ceroPrincipal);
                paso3();    // cubrir las columnas que contienen un cero marcado
            } else {
                // hay marca de cuadro en la línea del ceroPrincipal
                // paso 5
                filaEstaCubierta[ceroPrincipal[0]] = 1;  // cubrir fila de ceroPrincipal
                columnaEstaCubierta[cuadroEnFila[ceroPrincipal[0]]] = 0;  // descubrir columna de ceroPrincipal
                paso7();
            }
        }

        int[][] asignacionOptima = new int[matriz.length][];
        for (int i = 0; i < cuadroEnColumna.length; i++) {
            asignacionOptima[i] = new int[]{i, cuadroEnColumna[i]};
        }
        return asignacionOptima;
    }

    /**
     * Verifica si todas las columnas están cubiertas. Si es así, se encuentra la
     * solución óptima
     *
     * @return true o false
     */
    private boolean todasLasColumnasEstanCubiertas() {
        for (int i : columnaEstaCubierta) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Paso 1:
     * Reducir la matriz para que en cada fila y columna haya al menos un cero:
     * 1. restar cada mínimo de fila de cada elemento de la fila
     * 2. restar cada mínimo de columna de cada elemento de la columna
     */
    private void paso1() {
        // filas
        for (int i = 0; i < matriz.length; i++) {
            // encontrar el valor mínimo de la fila actual
            int minimoFilaActual = Integer.MAX_VALUE;
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] < minimoFilaActual) {
                    minimoFilaActual = matriz[i][j];
                }
            }
            // restar el valor mínimo de cada elemento de la fila actual
            for (int k = 0; k < matriz[i].length; k++) {
                matriz[i][k] -= minimoFilaActual;
            }
        }

        // columnas
        for (int i = 0; i < matriz[0].length; i++) {
            // encontrar el valor mínimo de la columna actual
            int minimoColumnaActual = Integer.MAX_VALUE;
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[j][i] < minimoColumnaActual) {
                    minimoColumnaActual = matriz[j][i];
                }
            }
            // restar el valor mínimo de cada elemento de la columna actual
            for (int k = 0; k < matriz.length; k++) {
                matriz[k][i] -= minimoColumnaActual;
            }
        }
    }

    /**
     * Paso 2:
     * marcar cada 0 con un "cuadro", si no hay otros ceros marcados en la misma fila o columna
     */
    private void paso2() {
        int[] filaTieneCuadro = new int[matriz.length];
        int[] columnaTieneCuadro = new int[matriz[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                // marcar si el valor actual == 0 y no hay otros ceros marcados en la misma fila o columna
                if (matriz[i][j] == 0 && filaTieneCuadro[i] == 0 && columnaTieneCuadro[j] == 0) {
                    filaTieneCuadro[i] = 1;
                    columnaTieneCuadro[j] = 1;
                    cuadroEnFila[i] = j; // guardar la posición de la fila del cero
                    cuadroEnColumna[j] = i; // guardar la posición de la columna del cero
                    continue; // saltar a la siguiente fila
                }
            }
        }
    }

    /**
     * Paso 3:
     * Cubrir todas las columnas que están marcadas con un "cuadro"
     */
    private void paso3() {
        for (int i = 0; i < cuadroEnColumna.length; i++) {
            columnaEstaCubierta[i] = cuadroEnColumna[i] != -1 ? 1 : 0;
        }
    }

    /**
     * Paso 7:
     * 1. Encontrar el valor no cubierto más pequeño en la matriz.
     * 2. Restar ese valor de todos los valores no cubiertos.
     * 3. Sumar ese valor a todos los valores cubiertos dos veces.
     */
    private void paso7() {
        // Encontrar el valor no cubierto más pequeño en la matriz
        int valorNoCubiertoMinimo = Integer.MAX_VALUE;
        for (int i = 0; i < matriz.length; i++) {
            if (filaEstaCubierta[i] == 1) {
                continue;
            }
            for (int j = 0; j < matriz[0].length; j++) {
                if (columnaEstaCubierta[j] == 0 && matriz[i][j] < valorNoCubiertoMinimo) {
                    valorNoCubiertoMinimo = matriz[i][j];
                }
            }
        }

        if (valorNoCubiertoMinimo > 0) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    if (filaEstaCubierta[i] == 1 && columnaEstaCubierta[j] == 1) {
                        // Sumar el mínimo a todos los valores cubiertos dos veces
                        matriz[i][j] += valorNoCubiertoMinimo;
                    } else if (filaEstaCubierta[i] == 0 && columnaEstaCubierta[j] == 0) {
                        // Restar el mínimo de todos los valores no cubiertos
                        matriz[i][j] -= valorNoCubiertoMinimo;
                    }
                }
            }
        }
    }

    /**
     * Paso 4:
     * Encontrar el valor cero Z_0 y marcarlo como "0*".
     *
     * @return posición de Z_0 en la matriz
     */
    private int[] paso4() {
        for (int i = 0; i < matriz.length; i++) {
            if (filaEstaCubierta[i] == 0) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if (matriz[i][j] == 0 && columnaEstaCubierta[j] == 0) {
                        cerosMarcadosEnFila[i] = j; // marcar como 0*
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Paso 6:
     * Crear una cadena K de "cuadros" y "0*" alternantes.
     *
     * @param ceroPrincipal => Z_0 del Paso 4
     */
    private void paso6(int[] ceroPrincipal) {
        int i = ceroPrincipal[0];
        int j = ceroPrincipal[1];

        Set<int[]> K = new LinkedHashSet<>();
        // (a)
        // agregar Z_0 a K
        K.add(ceroPrincipal);
        boolean encontrado = false;
        do {
            // (b)
            // agregar Z_1 a K si
            // hay un cero Z_1 marcado con un "cuadro" en la columna de Z_0
            if (cuadroEnColumna[j] != -1) {
                K.add(new int[]{cuadroEnColumna[j], j});
                encontrado = true;
            } else {
                encontrado = false;
            }

            // si no existe un elemento cero Z_1 marcado con "cuadro" en la columna de Z_0, entonces cancelar el bucle
            if (!encontrado) {
                break;
            }

            // (c)
            // reemplazar Z_0 con el 0* en la fila de Z_1
            i = cuadroEnColumna[j];
            j = cerosMarcadosEnFila[i];
            // agregar el nuevo Z_0 a K
            if (j != -1) {
                K.add(new int[]{i, j});
                encontrado = true;
            } else {
                encontrado = false;
            }

        } while (encontrado); // (d) mientras no se encuentren nuevas marcas "cuadro"

        // (e)
        for (int[] cero : K) {
            // quitar todas las marcas "cuadro" en K
            if (cuadroEnColumna[cero[1]] == cero[0]) {
                cuadroEnColumna[cero[1]] = -1;
                cuadroEnFila[cero[0]] = -1;
            }
            // reemplazar las marcas 0* en K con marcas "cuadro"
            if (cerosMarcadosEnFila[cero[0]] == cero[1]) {
                cuadroEnFila[cero[0]] = cero[1];
                cuadroEnColumna[cero[1]] = cero[0];
            }
        }

        // (f)
        // quitar todas las marcas
        Arrays.fill(cerosMarcadosEnFila, -1);
        Arrays.fill(filaEstaCubierta, 0);
        Arrays.fill(columnaEstaCubierta, 0);
    }
}

