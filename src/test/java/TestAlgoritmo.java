// test class for AlgoritmoDisposicion.java in src/main/java/prop/teclado/domain/classes/AlgoritmoDisposicion.java
// JUnit 4.x

import prop.teclado.domain.classes.AlgoritmoDisposicion;
import prop.teclado.domain.classes.Alfabeto;
import prop.teclado.domain.classes.Lenguaje;
import prop.teclado.domain.classes.Teclado;

import static org.junit.Assert.*;
import org.junit.Test;

// Tests de la clase AlgoritmoDisposicion
// Author: Tahir Muhammad Aziz

public class TestAlgoritmo {

    @Test       // la letras que componen "prueba" deberian de estar lo mas juntas posibles 
    public void testFuncionamientoAlgoritmo() throws Exception {
        Teclado teclado = new Teclado("teclado", new Lenguaje("lenguaje", "texto1", "pruebapruebapruebapruebapruebapruebapruebaprueba", 
                new Alfabeto("alfabeto", "abcdefghijklmnopqrstuvwxyz", false), true));
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
        Character[][] teclas = teclado.getTeclasComoMatriz();
        Character[][] teclasEsperadas = {   {'b', 'e', 'r', ' ', 'c'},      // Es una de las soluciones posibles: no la unica planteada
                                            {'a', 'p', 'u', 'd', 'f'},
                                            {'g', 'h', 'i', 'j', 'k'},
                                            {'l', 'm', 'n', 'o', 'q'},
                                            {'s', 't', 'v', 'w', 'x'},
                                            {'X', 'y', 'z', 'X', 'X'}       // las 'X' son valores en nulos
                                        };

        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                if (teclas[i][j] != null) {
                    assertEquals(teclasEsperadas[i][j], teclas[i][j]);
                }
            }
        }
    }

    @Test       // mix
    public void testFuncionamientoAlgoritmo2() throws Exception {
        Teclado teclado = new Teclado("teclado", new Lenguaje("lenguaje", "texto1", 
        "pkpkpkpkpkdhdhddbbd d d vsvdv d vd vdv d vdv  dv vdjasodohasodjhasjd aojhsdoasidujhaoisdjh aoisduaoisdujoaisduj qwerqwerqwerqwerqwer qwerqwerqwer nbnbnbnbcvmmcmcvmcvmcvbnbncvmcnvmcbnbmb", 
                new Alfabeto("alfabeto", "abcdefghijklmnopqrstuvwxyz", false), true));
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
        Character[][] teclas = teclado.getTeclasComoMatriz();
        Character[][] teclasEsperadas = {   {'e', 'w', 'k', 'p', 'f'},      // Es una de las soluciones posibles: no la unica planteada
                                            {'r', 'q', 'h', 'j', 'g'},
                                            {'n', ' ', 'd', 'a', 'u'},
                                            {'b', 'v', 's', 'o', 'l'},
                                            {'c', 'm', 'i', 't', 'x'},
                                            {'X', 'y', 'z', 'X', 'X'}       // las 'X' son valores en nulos
                                        };

        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                if (teclas[i][j] != null) {
                    assertEquals(teclasEsperadas[i][j], teclas[i][j]);
                }
            }
        }
    }

    @Test           // numeral mediamente explicito
    public void testFuncionamientoAlgoritmo3() throws Exception {
        Teclado teclado = new Teclado("teclado", new Lenguaje("lenguaje", "texto1", 
        "1515151515161616161611 11 1 1 1 1 1  1989898888484844343434111122226262626626263334344488448844884", 
                new Alfabeto("alfabeto", "0123456789", false), true));
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
        Character[][] teclas = teclado.getTeclasComoMatriz();
        Character[][] teclasEsperadas = {   {'3', '4', '8'},        // Es una de las soluciones posibles: no la unica planteada
                                            {'6', '1', '9'},
                                            {'2', ' ', '5'},
                                            {'0', '7', 'X'}         // las 'X' son valores en nulos
                                        };

        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                if (teclas[i][j] != null) {
                    assertEquals(teclasEsperadas[i][j], teclas[i][j]);
                }
            }
        }
    }

    @Test           // "tri" tendrian que formar un triangulo
    public void testFuncionamientoAlgoritmo4() throws Exception {
        Teclado teclado = new Teclado("teclado", new Lenguaje("lenguaje", "texto1", 
        "tritritritritritritritritritritritritritritritritritri", 
                new Alfabeto("alfabeto", "triangulo", false), true));
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
        Character[][] teclas = teclado.getTeclasComoMatriz();
        Character[][] teclasEsperadas = {   {'r', 't', ' '},        // Es una de las soluciones posibles: no la unica planteada
                                            {'i', 'a', 'n'},
                                            {'g', 'u', 'l'},
                                            {'X', 'o', 'X'}         // las 'X' son valores en nulos
                                        };

        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                if (teclas[i][j] != null) {
                    assertEquals(teclasEsperadas[i][j], teclas[i][j]);
                }
            }
        }
    }

@Test           // x deberia de estar en el centro
    public void testFuncionamientoAlgoritmo5() throws Exception {
        Teclado teclado = new Teclado("teclado", new Lenguaje("lenguaje", "texto1", "xox xsxnxcxexrxtx",
                new Alfabeto("alfabeto", "xescentro", false), true));
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        algoritmo.asignarMejorSolucion();
        Character[][] teclas = teclado.getTeclasComoMatriz();
        Character[][] teclasEsperadas = {   {' ', 'e', 's'},        // Es una de las soluciones posibles: no la unica planteada
                                            {'c', 'x', 'n'},
                                            {'t', 'r', 'o'},
                                        };

        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                if (teclas[i][j] != null) {
                    assertEquals(teclasEsperadas[i][j], teclas[i][j]);
                }
            }
        }
    }

    // logicamente, durante la comprobacion de si el teclado muestra lo que deberia, 
    // se han hecho muchisimas mas pruebas que se han decidido omitir para no sobrecargar el codigo
    // pero todas han tenido un resultado aparentemente positivo

    @Test
    public void testCalcularFrecuencias() throws Exception {
        Alfabeto alfabeto = new Alfabeto("alfabeto", "abcdef", false);
        Lenguaje lenguaje = new Lenguaje("lenguaje", "texto1", "adef ab ab bcd fefefefe", alfabeto, true);
        Teclado teclado = new Teclado("teclado", lenguaje);
        AlgoritmoDisposicion algoritmo = new AlgoritmoDisposicion(teclado, AlgoritmoDisposicion.TipoAlgoritmo.QAP);
        int[][] matrizFrecuencias = algoritmo.getMatrizFrecuencias();
        
        int[][] expected =  {   {0, 2, 3, 0, 1, 0, 2},
                                {2, 0, 2, 0, 1, 0, 0}, 
                                {3, 2, 0, 1, 0, 0, 0},
                                {0, 0, 1, 0, 1, 0, 0}, 
                                {1, 1, 0, 1, 0, 1, 0}, 
                                {0, 0, 0, 0, 1, 0, 8},
                                {2, 0, 0, 0, 0, 8, 0}
                            };
        
        assertArrayEquals(expected, matrizFrecuencias);
    }

}
