import functions.CreateAlfabetoTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;

import java.net.URL;
import java.util.Objects;


/**
 * Tests Unitaris de la clase Texto
 * Author: Joan Martínez Soria
 */
public class TestTexto {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCrearTextoEstandar() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Texto texto = new TextoEstandar("Test", "");
        texto = texto.crearTexto("Test", alfabeto, "Hola aloha");
        assertEquals("Test", texto.getNombre());
        assertEquals(2, texto.getPalabrasEnTexto().size());
        assertEquals("hola", texto.getPalabrasEnTexto().get(0).getPalabra().getNombre());
        assertEquals("aloha", texto.getPalabrasEnTexto().get(1).getPalabra().getNombre());
    }

    @Test
    public void testCrearTextoEstandarVacio() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(NoPalabrasEnTexto.class);
        exceptionRule.expectMessage("No hay palabras en el texto");

        Texto texto = new TextoEstandar("Test", "");
        texto.crearTexto("Test", alfabeto, "    ");
    }

    @Test
    public void testCrearTextoEstandarConSimbolosNoEnAlfabeto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(SimboloNoPerteneceAlAlfabeto.class);
        exceptionRule.expectMessage("Aparece un simbolo que no pertenece al alfabeto");

        Texto texto = new TextoEstandar("Test", "");
        texto.crearTexto("Test", alfabeto, "Hola aloha 123");
    }

    @Test
    public void testCrearTextoEstandarDesdeFichero() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Texto texto = new TextoEstandar("Test", "");
        ClassLoader classLoader = TestTexto.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/Texto_test.txt"))
                .getPath();
        texto = texto.crearTextoFichero(path, alfabeto, Texto.TipoTexto.ESTANDAR);
        assertEquals("Texto_test.txt", texto.getNombre());
        assertEquals(3, texto.getPalabrasEnTexto().size());
        assertEquals("hola", texto.getPalabrasEnTexto().get(0).getPalabra().getNombre());
        assertEquals("alo", texto.getPalabrasEnTexto().get(1).getPalabra().getNombre());
        assertEquals(2, texto.getPalabrasEnTexto().get(1).getNumApariciones());
    }

    @Test
    public void testCrearTextoEstandarDesdeFicheroNoTxt() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(NoTxt.class);
        exceptionRule.expectMessage("El archivo no es un .txt");

        Texto texto = new TextoEstandar("Test", "");
        ClassLoader classLoader = TestTexto.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TestNoTxt.java"))
                .getPath();
        texto = texto.crearTextoFichero(path, alfabeto, Texto.TipoTexto.ESTANDAR);
    }

    @Test
    public void testCrearTextoEstandarDesdeFicheroWrongPath() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(FileNotFound.class);
        exceptionRule.expectMessage("No se encuentra el archivo");

        Texto texto = new TextoEstandar("Test", "");

        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource("PruebaDeTxt/Texto_test2.txt");

        if (resourceUrl == null) {
            //no se encuentra el archivo por lo tanto lanza la excepcion
            throw new FileNotFound();
        }
        String path = resourceUrl.getPath();
        texto = texto.crearTextoFichero(path, alfabeto, Texto.TipoTexto.ESTANDAR);
    }

    @Test
    public void testCrearTextoFrequencias() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Texto texto = new TextoFrecuencias("Test", "");
        texto = texto.crearTexto("Test", alfabeto, "Hola 10 aloha 5");
        assertEquals("Test", texto.getNombre());
        assertEquals(2, texto.getPalabrasEnTexto().size());
        assertEquals("hola", texto.getPalabrasEnTexto().get(0).getPalabra().getNombre());
        assertEquals(10, texto.getPalabrasEnTexto().get(0).getNumApariciones());
        assertEquals("aloha", texto.getPalabrasEnTexto().get(1).getPalabra().getNombre());
        assertEquals(5, texto.getPalabrasEnTexto().get(1).getNumApariciones());
    }

    @Test
    public void testCrearTextoFrequenciasFichero() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Texto texto = new TextoEstandar("Test", "");
        ClassLoader classLoader = TestTexto.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        texto = texto.crearTextoFichero(path, alfabeto, Texto.TipoTexto.FRECUENCIA);
        assertEquals("TextoFrequencia_test.txt", texto.getNombre());
        assertEquals(3, texto.getPalabrasEnTexto().size());
        assertEquals("hola", texto.getPalabrasEnTexto().get(0).getPalabra().getNombre());
        assertEquals(20, texto.getPalabrasEnTexto().get(0).getNumApariciones());
        assertEquals("aloha", texto.getPalabrasEnTexto().get(1).getPalabra().getNombre());
        assertEquals(100, texto.getPalabrasEnTexto().get(1).getNumApariciones());
        assertEquals("alo", texto.getPalabrasEnTexto().get(2).getPalabra().getNombre());
        assertEquals(50, texto.getPalabrasEnTexto().get(2).getNumApariciones());
    }

    @Test
    public void testTextosIguales() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        ClassLoader classLoader = TestTexto.class.getClassLoader();
        String path1 = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        String path2 = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TextoFrequencia_test.txt"))
                .getPath();
        Texto texto1 = new TextoEstandar("Test", "");
        texto1 = texto1.crearTextoFichero(path1,
                alfabeto, Texto.TipoTexto.FRECUENCIA);
        Texto texto2 = new TextoEstandar("Test", "");
        texto2 = texto2.crearTextoFichero(path2,
                alfabeto, Texto.TipoTexto.FRECUENCIA);
        Texto texto3 = new TextoEstandar("Test", "");
        texto3.crearTexto("Test", alfabeto, "Hola aloha");

        assertFalse(texto1.textosIguales(texto3));
        assertTrue(texto1.textosIguales(texto2));
    }

}
