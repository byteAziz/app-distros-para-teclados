package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.NoLenguaje;
import prop.teclado.domain.classes.exceptions.NoSimbolosOnAlfabeto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un teclado formado por teclas y mediante un algoritmo
 * Author: Guillem Angulo Hidalgo y Tahir Muhammad Aziz
 */


public class Teclado {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private String nombre;                  // nombre asociado al teclado
    private Lenguaje lenguaje;              // lenguaje en el que se basa el teclado
    private List<Tecla> teclas;             // lista de teclas que forman el teclado

    /* Para minimzar las distancias del teclado, se ha decidido que el teclado sea cuadrado o casi cuadrado
    *  Para ello se ha decidido que el numero de filas y columnas sea el entero mas cercano a la raiz cuadrada
    *  del numero de simbolos del lenguaje
    *
    *  Para que las teclas de la ultima fila, que puede que no este completa, no esten ajustadas a la izquierda
    *  se ha decidido que esten centradas, con un offset, asi el caso peor es mejor que el caso peor sin el offset
    *
    *  Por ejemplo si tenemos simbolos.size() = 7, queremos que las teclas representen:
    *
    *          [ ] [a] [b]                          [ ] [a] [b]
    *          [c] [d] [e]          y no            [c] [d] [e]
    *              [f]                              [f]
    *
    *  Donde tamUltimaFila = 1 y offset = 1
    *
    *  La justificacion de esto es que la distancia total entre las teclas es menor que si no se usara offset,
    *  por lo que a gran escala el caso peor es mejor que el caso peor sin el offset.
    */

    private int numTeclas;      // numero de teclas que tiene el teclado
    private int numFilas;       // numero de filas del teclado
    private int numColumnas;    // numero de columnas del teclado
    private int tamUltimaFila;  // numero de teclas utiles de la ultima fila
    private int offset;         // numero de espacios vacios que hay que al principio de la ultima fila, explicacion detallada:
    private String fechaCreacion;
    private String fechaModificacion;

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    public Teclado(String nombre, Lenguaje lenguaje) throws NoLenguaje, NoSimbolosOnAlfabeto {
            // Se establece el nombre y el lenguaje
        this.nombre = nombre;

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        // Formatear la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaHoraActual.format(formatter);
        this.fechaCreacion = fechaFormateada;
        this.fechaModificacion = fechaFormateada;

        if (lenguaje == null)
            throw new NoLenguaje();
        this.lenguaje = lenguaje;
        List<Character> simbolos = lenguaje.getAlfabeto().getSimbolos();

            // Se calculan las dimensiones del teclado junto al offset con tamUltimaFila
        numTeclas = simbolos.size();
        numFilas = (int)Math.sqrt(numTeclas);
        numColumnas = numFilas;
        if (numFilas*numColumnas < numTeclas)
            ++numFilas;
        if (numFilas*numColumnas < numTeclas)
            ++numColumnas;

            // Ahora se calcula el offset y tamUltimaFila
        int espaciosVacios = numFilas*numColumnas - numTeclas;
        tamUltimaFila = numColumnas - espaciosVacios;
        offset = espaciosVacios / 2;

            // Se asignan los simbolos a las teclas
        asignarSimbolosATeclasDirectamente(simbolos);

            // Se aplica el algoritmo de disposicion
        aplicarAlgoritmo();
    }

    public Teclado(String nombre, String creacion, String modificacion, Lenguaje lenguaje, List<Character> teclasOrdenadas)
            throws NoLenguaje {
        this.nombre = nombre;
        this.fechaCreacion = creacion;
        this.fechaModificacion = modificacion;
        if (lenguaje == null)
            throw new NoLenguaje();
        this.lenguaje = lenguaje;
        List<Character> simbolos = lenguaje.getAlfabeto().getSimbolos();

        // Se calculan las dimensiones del teclado junto al offset con tamUltimaFila
        numTeclas = simbolos.size();
        numFilas = (int)Math.sqrt(numTeclas);
        numColumnas = numFilas;
        if (numFilas*numColumnas < numTeclas)
            ++numFilas;
        if (numFilas*numColumnas < numTeclas)
            ++numColumnas;

        // Ahora se calcula el offset y tamUltimaFila
        int espaciosVacios = numFilas*numColumnas - numTeclas;
        tamUltimaFila = numColumnas - espaciosVacios;
        offset = espaciosVacios / 2;

        asignarSimbolosATeclasDirectamente(teclasOrdenadas);

        // En teoria, como ya vienen ordenados segun la aplicacion anterior del algoritmo, no hace falta aplicar el algoritmo
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------

    // Asigna los simbolos a las teclas del teclado directamente en el orden de entrada
    public void asignarSimbolosATeclasDirectamente(List<Character> simbolos) {
        if (simbolos.size() != numTeclas)
            throw new IllegalArgumentException("El numero de simbolos a asignar no corresponde al numero de teclas.");

            // se inicializa la lista de teclas
        teclas = new ArrayList<Tecla>();

        for (Character simbolo : simbolos) {
                // se convierte a minusculas
            simbolo = Character.toLowerCase(simbolo);

                // si el simbolo no pertenece al lenguaje, abortar
            if (!lenguaje.getAlfabeto().existeSimbolo(simbolo))
                throw new IllegalArgumentException("El simbolo " + simbolo + " no existe en el alfabeto del lenguaje.");

            teclas.add(new Tecla(simbolo));
        }
    }

    // Asigna los simbolos en los puntos dados, como prerequisito el teclado tiene que estar inicializado, pero se cumple siempre
    public void asignarSimbolosEnTeclasMedianteIesimasUbicaciones(List<Character> simbolos, int[] jesimaUbicacionDeIesimoSimbolo) {
        if (simbolos.size() != jesimaUbicacionDeIesimoSimbolo.length)
            throw new IllegalArgumentException("El numero de simbolos a asignar no corresponde al numero de puntos dados.");
        if (simbolos.size() != numTeclas)
            throw new IllegalArgumentException("El numero de simbolos a asignar no corresponde al numero de teclas.");

        for (int i = 0; i < numTeclas; ++i) {
            Character simbolo = simbolos.get(i);
                // se convierte el simbolo en minusculas
            simbolo = Character.toLowerCase(simbolo);
                // se obtiene la jesima ubicacion correspondiente del iesimo simbolo
            int iesimaUbicacion = jesimaUbicacionDeIesimoSimbolo[i];

            if (!lenguaje.getAlfabeto().existeSimbolo(simbolo))
                throw new IllegalArgumentException("El simbolo " + simbolo + " no existe en el alfabeto del lenguaje.");

            if (!existeIesimo(iesimaUbicacion))
                throw new IllegalArgumentException("La ubicacion " + iesimaUbicacion + " no existe en el teclado.");

            teclas.set(iesimaUbicacion, new Tecla(simbolo));
        }
    }

    // Aplica el algoritmo de disposicion al teclado
    public void aplicarAlgoritmo() {
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(this, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
    }

    // Cambia las teclas del teclado, para mayor facilidad de usuario se pasa el simbolo en concreto y no la posicion
    public void cambiarTeclas(char tecla1, char tecla2) {
        if (!lenguaje.getAlfabeto().existeSimbolo(tecla1) || !lenguaje.getAlfabeto().existeSimbolo(tecla2)) {
            throw new IllegalArgumentException("Alguno de los simbolos no existe en el alfabeto del lenguaje.");
        }

        int i = 0;
        int j = 0;
        for (Tecla tecla : teclas) {
            if (tecla.getSimbolo() == tecla1)
                i = teclas.indexOf(tecla);
            if (tecla.getSimbolo() == tecla2)
                j = teclas.indexOf(tecla);
        }

        Tecla aux = teclas.get(i);
        teclas.set(i, teclas.get(j));
        teclas.set(j, aux);
    }

    // Imprime el teclado por pantalla
    public void imprimirTeclado() {
        int iesimaTecla = 0;

        for (int i = 0; i < numFilas; ++i) {
            for (int j = 0; j < numColumnas; ++j) {
                // si es un punto valido se impime, si no se deja un espacio en blanco
                if (existePunto(new Point(i, j)))
                    System.out.print("[" + teclas.get(iesimaTecla++).getSimbolo() + "] ");
                else
                    System.out.print("    ");
            }

            System.out.println();
        }
    }

    // Comprueba si existe la iesima tecla
    public boolean existeIesimo(int i) {
        return i >= 0 && i < numTeclas;
    }

    // Comprueba si existe la tecla en el punto dado
    public boolean existePunto(Point punto) {
        int x = punto.x;
        int y = punto.y;
        // fuera de la matriz
        if (x < 0 || x > numFilas || y < 0 || y > numColumnas)
            return false;
        // fuera de la ultima fila
        if (x == numFilas-1 && (y < offset || y >= offset+tamUltimaFila))
            return false;
        return true;
    }

    // ------------------------------------------ GETTERS ------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public List<Tecla> getTeclas() {
        return teclas;
    }

    // retorna el teclado en forma de matriz, en caso que no haya tecla en un punto se pone null
    public Character[][] getTeclasComoMatriz() {
        Character[][] teclasEnFormaDeMatriz = new Character[numFilas][numColumnas];

        for (int i = 0; i < numFilas; ++i) {
            for (int j = 0; j < numColumnas; ++j) {
                Point punto = new Point(i, j);
                if (existePunto(punto))
                    teclasEnFormaDeMatriz[i][j] = getTeclaEnPunto(punto).getSimbolo();
                else
                    teclasEnFormaDeMatriz[i][j] = null;
            }
        }

        return teclasEnFormaDeMatriz;
    }

    public int getIesimoMediantePunto(Point punto) {
        if (!existePunto(punto))
            throw new IllegalArgumentException("Punto fuera de rango del teclado");

            // si es ultima fila se tiene en cuenta el offset
        if (punto.x == numFilas-1)
            return punto.x*numColumnas + punto.y - offset;
        else
            return punto.x*numColumnas + punto.y;
    }

    public Point getPuntoMedianteIesimo(int i) {
        if (!existeIesimo(i))
            throw new IllegalArgumentException("Iesima posicion fuera de rango del teclado");

        int x = i / numColumnas;
        int y = i % numColumnas;
        if (x == numFilas-1)
            y += offset;
        return new Point(x, y);
    }

    public Tecla getTeclaEnIesimo(int i) {
        if (!existeIesimo(i))
            throw new IllegalArgumentException("Iesima posicion fuera de rango del teclado");

        return teclas.get(i);
    }

    public Tecla getTeclaEnPunto(Point punto) {
        if (!existePunto(punto))
            throw new IllegalArgumentException("Punto fuera de rango del teclado");

        int i = getIesimoMediantePunto(punto);
        return teclas.get(i);
    }

    public double getDistanciaEntreDosTeclas(int i, int j) {
        if (!existeIesimo(i))
            throw new IllegalArgumentException("Iesima posicion fuera de rango del teclado");

        if (!existeIesimo(j))
            throw new IllegalArgumentException("Jesima posicion fuera de rango del teclado");

            // Se obtienen los puntos de las teclas
        Point puntoI = getPuntoMedianteIesimo(i);
        Point puntoJ = getPuntoMedianteIesimo(j);

            // se calcula la distancia entre los puntos, si es horizontal o vertical se devuelve directamente
        if (puntoI.x == puntoJ.x)
            return abs(puntoI.y - puntoJ.y);
        else if (puntoI.y == puntoJ.y)
            return abs(puntoI.x - puntoJ.x);

            // si no es horizontal ni vertical se calcula la distancia diagonal, y se devuelve
        return sqrt(pow((puntoI.x - puntoJ.x), 2) + pow((puntoI.y - puntoJ.y), 2));
    }

    public int getNumFilas() {
        return numFilas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public int getTamUltimaFila() {
        return tamUltimaFila;
    }

    public int getOffset() {
        return offset;
    }

    public String getFCreacion() {
        return fechaCreacion;
    }

    public String getFModificacion() {
        return fechaModificacion;
    }

    // ------------------------------------------ SETTERS ------------------------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDModificacion(String date) {
        this.fechaModificacion = date;
    }

}
