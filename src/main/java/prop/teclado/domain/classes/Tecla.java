package prop.teclado.domain.classes;

/**
 * Clase que representa una tecla
 * Author: Guillem Angulo Hidalgo
 */

public class Tecla {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private char simbolo;

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    public Tecla(char simbolo) {
        this.simbolo = simbolo;
    }

    // ------------------------------------------ GETTERS ------------------------------------------
    public char getSimbolo() {
        return simbolo;
    }

}
