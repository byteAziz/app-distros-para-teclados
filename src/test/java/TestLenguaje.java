import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;
import prop.teclado.domain.classes.Alfabeto;
import prop.teclado.domain.classes.Lenguaje;
import functions.CreateAlfabetoTest;
import prop.teclado.domain.classes.exceptions.*;

import java.net.URL;
import java.util.Objects;


/**
 * Tests Unitaris de la clase Lenguaje
 * Author: Joan Martínez Soria
 */
public class TestLenguaje {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCrearLenguajeTN() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
        assertEquals("Test", lenguaje.getNombre());
        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("Texto", lenguaje.getTextos().get(0).getNombre());
        assertEquals(2, lenguaje.getPalabras().size());
    }

    @Test
    public void testCrearLenguajeTF() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola 10", alfabeto, false);
        assertEquals("Test", lenguaje.getNombre());
        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("Texto", lenguaje.getTextos().get(0).getNombre());
        assertEquals(1, lenguaje.getPalabras().size());
        assertEquals(10, lenguaje.getTextos().get(0).getPalabrasEnTexto().get(0).getNumApariciones());
    }

    @Test
    public void testAddNewTN() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
        lenguaje.addNewTextoEstandar("Texto2", "Hola");

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(2, lenguaje.getTextos().size());
        assertEquals("Texto2", lenguaje.getTextos().get(1).getNombre());
        assertEquals(2, lenguaje.getPalabras().size());
    }

    @Test
    public void testAddNewTF() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
        lenguaje.addNewTextoFrequencia("Texto2", "Hola 20");

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(2, lenguaje.getTextos().size());
        assertEquals("Texto2", lenguaje.getTextos().get(1).getNombre());
        assertEquals(2, lenguaje.getPalabras().size());
        assertEquals(20, lenguaje.getTextos().get(1).getPalabrasEnTexto().get(0).getNumApariciones());
    }

    @Test
    public void testLenguajeDesdeFicheroTN() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/Texto_test.txt"))
                .getPath();
        Lenguaje lenguaje = new Lenguaje("Test", path, alfabeto, true);

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("Texto_test.txt", lenguaje.getTextos().get(0).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());
    }

    @Test
    public void testLenguajeDesdeFicheroTF() throws Exception{
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        Lenguaje lenguaje = new Lenguaje("Test", path, alfabeto, false);

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("TextoFrequencia_test.txt", lenguaje.getTextos().get(0).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());
    }

    @Test
    public void testLenguajeDesdeFicheroNoTxt() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(NoTxt.class);
        exceptionRule.expectMessage("El archivo no es un .txt");
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TestNoTxt.java"))
                .getPath();
        Lenguaje lenguaje = new Lenguaje("Test", path, alfabeto, true);

    }

    @Test
    public void testLenguajeDesdeFicheroWrongPath() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(FileNotFound.class);
        exceptionRule.expectMessage("No se encuentra el archivo");
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("PruebaDeTxt/Texto_test2.txt");
        if (resourceUrl == null) {
            //no se encuentra el archivo por lo tanto lanza la excepcion
            throw new FileNotFound();
        }
        Lenguaje lenguaje = new Lenguaje("Test", resourceUrl.getPath(), alfabeto, true);
    }

    @Test
    public void testCrearLenguajeTextoVacio() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(NoPalabrasEnTexto.class);
        exceptionRule.expectMessage("No hay palabras en el texto");
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoVacio.txt"))
                .getPath();
        Lenguaje lenguaje = new Lenguaje("Test", path, alfabeto, true);
    }

    @Test
    public void testCrearLenguajeNewSimbolo() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(SimboloNoPerteneceAlAlfabeto.class);
        exceptionRule.expectMessage("Aparece un simbolo que no pertenece al alfabeto");
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoNewSimbolo.txt"))
                .getPath();
        Lenguaje lenguaje = new Lenguaje("Test", path, alfabeto, true);
    }

    @Test
    public void testAddNewTestFicheroTN() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/Texto_test.txt"))
                .getPath();
        lenguaje.addNewTextoFicheroTE(path);

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(2, lenguaje.getTextos().size());
        assertEquals("Texto_test.txt", lenguaje.getTextos().get(1).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());
    }

    @Test
    public void testAddNewTestFicheroTF() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        lenguaje.addNewTextoFicheroTF(path);

        assertEquals("Test", lenguaje.getNombre());
        assertEquals(2, lenguaje.getTextos().size());
        assertEquals("TextoFrequencia_test.txt", lenguaje.getTextos().get(1).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());
    }

    @Test
    public void testEliminateTexto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = crearLenguajeTN(alfabeto);

        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("Texto", lenguaje.getTextos().get(0).getNombre());
        assertEquals(2, lenguaje.getPalabras().size());

        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();

        lenguaje.addNewTextoFicheroTF(path);

        assertEquals(2, lenguaje.getTextos().size());
        assertEquals("TextoFrequencia_test.txt", lenguaje.getTextos().get(1).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());

        lenguaje.eliminateTexto("Texto");

        assertEquals(1, lenguaje.getTextos().size());
        assertEquals("TextoFrequencia_test.txt", lenguaje.getTextos().get(0).getNombre());
        assertEquals(3, lenguaje.getPalabras().size());
    }

    @Test
    public void testEliminateTextoException() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = crearLenguajeTN(alfabeto);

        exceptionRule.expect(CantRemoveTexto.class);
        exceptionRule.expectMessage("No se puede eliminar el texto del lenguaje porque es el único que hay.");

        lenguaje.eliminateTexto("Texto");
    }

    @Test
    public void testFrecuenciaEntreSimbolosMatrizLenguaje() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = crearLenguajeTN(alfabeto);

        int[][] MatrizEsperada = {
                {0, 0, 0, 0, 2},
                {0, 0, 2, 0, 1},
                {0, 2, 0, 2, 0},
                {0, 0, 2, 0, 2},
                {2, 1, 0, 2, 0}
        };

        int[][] actualMatrix = lenguaje.getFrecuencias();

        // Verifica que las matrices sean iguales
        assertEquals(MatrizEsperada.length, actualMatrix.length);
        assertArrayEquals(MatrizEsperada, actualMatrix);
    }

    @Test
    public void testFrecuenciaEntreSimbolosMatrizLenguajeModification() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Lenguaje lenguaje = crearLenguajeTN(alfabeto);
        lenguaje.addNewTextoEstandar("Texto2", "hola");
        int[][] MatrizEsperada = {
                {0, 0, 0, 0, 2},
                {0, 0, 3, 0, 1},
                {0, 3, 0, 3, 0},
                {0, 0, 3, 0, 3},
                {2, 1, 0, 3, 0}
        };

        int[][] actualMatrix = lenguaje.getFrecuencias();

        // Verifica que las matrices sean iguales
        assertEquals(MatrizEsperada.length, actualMatrix.length);
        assertArrayEquals(MatrizEsperada, actualMatrix);

        assertTrue(lenguaje.getUpdate());
        ClassLoader classLoader = TestLenguaje.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        lenguaje.addNewTextoFicheroTF(path);
        assertFalse(lenguaje.getUpdate());

        int[][] MatrizEsperadaMod = {
                {0, 0, 50, 0, 122},
                {0, 0, 123, 0, 101},
                {50, 123, 0, 173, 0},
                {0, 0, 173, 0, 173},
                {122, 101, 0, 173, 0}
        };

        int[][] actualMatrixMod = lenguaje.getFrecuencias();
        assertEquals(MatrizEsperadaMod.length, actualMatrixMod.length);
        assertArrayEquals(MatrizEsperadaMod, actualMatrixMod);
    }

    private Lenguaje crearLenguajeTN(Alfabeto alfabeto) throws Exception {
        return new Lenguaje("Test", "Texto", "Hola aloha", alfabeto, true);
    }

}
