import functions.CreateAlfabetoTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import prop.teclado.domain.classes.Alfabeto;
import prop.teclado.domain.classes.Lenguaje;
import prop.teclado.domain.classes.Teclado;

import prop.teclado.domain.classes.exceptions.NoLenguaje;

import static org.junit.Assert.*;

/**
 * Tests Unitaris de la clase Teclado
 * Author: Guillem Angulo Hidalgo
 */

public class TestTeclado {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCrearTeclado() throws Exception {

        // Crear un lenguaje con un alfabeto y algunos símbolos
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);

        // Act
        Teclado teclado = new Teclado("TecladodePrueba", lenguaje);

        // Assert
        assertNotNull(teclado);
        assertEquals("TecladodePrueba", teclado.getNombre());
        assertEquals(lenguaje, teclado.getLenguaje());
    }

    @Test(expected = NoLenguaje.class)
    public void testTecladoWithNullLanguage() throws Exception {
        // Act
        Teclado teclado = new Teclado("TecladoDePrueba", null);
        // No Assert needed; expect NoLenguaje exception
    }

    @Test
    public void testVariasInstanciasDeTeclado() throws Exception {
        int cantidadDeTeclados = 50;
        Teclado[] teclados = new Teclado[cantidadDeTeclados];

        for (int i = 0; i < cantidadDeTeclados; i++) {
            String nombre = "Teclado" + (i + 1);
            Alfabeto alfabeto = new Alfabeto("Alfabeto" + (i + 1), "A B C D E F G H I K", false);
            Lenguaje lenguaje = new Lenguaje("Test", "Texto", "abcd efgh", alfabeto, true);
            teclados[i] = new Teclado(nombre, lenguaje);
        }

        // Verificar que todas las instancias se hayan creado correctamente
        for (Teclado teclado : teclados) {
            assertNotNull(teclado);
        }

        // Verificar que las instancias sean diferentes entre sí
        for (int i = 0; i < cantidadDeTeclados; i++) {
            for (int j = i + 1; j < cantidadDeTeclados; j++) {
                assertNotEquals(teclados[i].getNombre(), teclados[j].getNombre());
                assertNotEquals(teclados[i].getLenguaje(), teclados[j].getLenguaje());
            }
        }
    }

    @Test
    public void getTeclasComoMatriz() throws Exception {
        Alfabeto alfabeto = new Alfabeto("Alfabeto", "A B C D E F G H", false);
        Lenguaje lenguaje = new Lenguaje("Test", "Texto",
                "fbfbfbfbf f f f fafa a a a a ababababagagagagagacacgcgcgcgcgbfbfegegegegegdgdedededededadadadadhdhdhdhdh h h h haha", alfabeto, true);
        Teclado teclado = new Teclado("TecladodePrueba", lenguaje);

        // el algoritmo deberia de generar el siguiente teclado con ese texto:
        /*
         *          [h] [d] [e]
         *          [ ] [a] [g]
         *          [f] [b] [c]
         */

        Character[][] expected = {
                {'h', 'd', 'e'},
                {' ', 'a', 'g'},
                {'f', 'b', 'c'}
        };

        Character[][] teclas = teclado.getTeclasComoMatriz();
        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                assertEquals(expected[i][j], teclas[i][j]);
            }
        }

    }

    @Test       // con teclado con offset
    public void getTeclasComoMatrizV2() throws Exception {
        Alfabeto alfabeto = new Alfabeto("Alfabeto", "123456", false);
        Lenguaje lenguaje = new Lenguaje("Test", "Texto",
                "1 1 1 1 1 121212121232323232323 3 3 3 3 3 6 6 6 6 6 6 6565656565654542424243434343434", alfabeto, true);
        Teclado teclado = new Teclado("TecladodePrueba", lenguaje);

        // el algoritmo deberia de generar el siguiente teclado con ese texto:
        /*
         *          [1] [ ] [6]
         *          [2] [3] [5]
         *              [4]
         */

        Character[][] expected = {
                {'1', ' ', '6'},
                {'2', '3', '5'},
                {null, '4', null}
        };

        Character[][] teclas = teclado.getTeclasComoMatriz();
        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                assertEquals(expected[i][j], teclas[i][j]);
            }
        }

    }

    @Test
    public void testCambiarTeclas() throws Exception {
        Alfabeto alfabeto = new Alfabeto("Alfabeto", "A B C D E F G H", false);
        Lenguaje lenguaje = new Lenguaje("Test", "Texto",
                "fbfbfbfbf f f f fafa a a a a ababababagagagagagacacgcgcgcgcgbfbfegegegegegdgdedededededadadadadhdhdhdhdh h h h haha", alfabeto, true);
        Teclado teclado = new Teclado("TecladodePrueba", lenguaje);

        Character[][] expected = {
                {'h', 'a', 'e'},
                {' ', 'c', 'g'},
                {'f', 'b', 'd'}
        };

        teclado.cambiarTeclas('a', 'd');
        teclado.cambiarTeclas('d', 'c');

        Character[][] teclas = teclado.getTeclasComoMatriz();
        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                assertEquals(expected[i][j], teclas[i][j]);
            }
        }
    }

    @Test
    public void testCambiarTeclasv2() throws Exception {
        Alfabeto alfabeto = new Alfabeto("Alfabeto", "123456", false);
        Lenguaje lenguaje = new Lenguaje("Test", "Texto",
                "1 1 1 1 1 121212121232323232323 3 3 3 3 3 6 6 6 6 6 6 6565656565654542424243434343434", alfabeto, true);
        Teclado teclado = new Teclado("TecladodePrueba", lenguaje);

        Character[][] expected = {
                {'2', ' ', '6'},
                {'1', '3', '5'},
                {null, '4', null}
        };

        teclado.cambiarTeclas('2', '1');

        Character[][] teclas = teclado.getTeclasComoMatriz();
        for (int i = 0; i < teclas.length; i++) {
            for (int j = 0; j < teclas[i].length; j++) {
                assertEquals(expected[i][j], teclas[i][j]);
            }
        }

    }

}





