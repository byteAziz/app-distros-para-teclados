package prop.teclado.domain.classes;


/**
 * Clase que representa La relacion entre una palabra y un texto, con las veces que
 * la palabra aparece en el texto.
 * Author: Joan Martínez Soria
 */
public class PalabraEnTexto {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------
    private Palabra palabra; //Palabra que aparece en el texto
    private Texto texto; //Texto en el que aparece la palabra
    private int numApariciones; //Numero de veces que aparece la palabra en el texto

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    //Crea una palabraEnTexto a partir de una palabra y un texto con frequencia 1
    public PalabraEnTexto(Palabra palabra, Texto texto) {
        this.palabra = palabra;
        this.texto = texto;
        numApariciones = 1;
    }

    //crea una palabraEnTexto a partir de una palabra, un texto y una frequencia
    public PalabraEnTexto(Palabra palabra, Texto texto, int frequencia) {
        this.palabra = palabra;
        this.texto = texto;
        numApariciones = frequencia;
    }

    // ------------------------------------------ GETTERS ------------------------------------------
    public Palabra getPalabra() {
        return palabra;
    }

    public Texto getTexto() {
        return texto;
    }

    public int getNumApariciones() {
        return numApariciones;
    }

    //Setters
    public void increseNumApariciones(int frequencia) {
        numApariciones+=frequencia;
    }

}
