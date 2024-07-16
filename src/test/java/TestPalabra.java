import functions.CreateAlfabetoTest;
import prop.teclado.domain.classes.Alfabeto;

import static org.junit.Assert.*;
import org.junit.Test;
import prop.teclado.domain.classes.Palabra;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExpectedException;
import org.junit.Rule;
import prop.teclado.domain.classes.exceptions.*;

/**
 * Tests Unitaris de la clase Palabra
 * Author: Joan Martínez Soria
 */
public class TestPalabra {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCrearPalabraExitosa() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Palabra palabra = new Palabra("Hola", alfabeto);
        assertNotNull(palabra);
        assertEquals("hola", palabra.getNombre());
        assertEquals(4, palabra.getSize());
    }

    @Test
    public void testCrearPalabraConSimbolosNoEnAlfabeto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        exceptionRule.expect(SimboloNoPerteneceAlAlfabeto.class);
        exceptionRule.expectMessage("Aparece un simbolo que no pertenece al alfabeto");
        new Palabra("Test", alfabeto);
    }

    @Test
    public void testCrearPalabraConSimbolosRepetidos() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();

        Palabra palabra = new Palabra("Holaa", alfabeto);
        assertNotNull(palabra);
        assertEquals("holaa", palabra.getNombre());
        assertEquals(2, palabra.getApariciones('a'));
        assertEquals(5, palabra.getSize());
    }

    @Test
    public void testCrearPalabrasDesdeTexto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        Palabra aux = new Palabra(null, null);
        List<Palabra> palabras = aux.crearPalabrasDesdeTexto("Hola aloha", alfabeto);
        assertEquals(2, palabras.size());
        assertEquals("hola", palabras.get(0).getNombre());
        assertEquals("aloha", palabras.get(1).getNombre());
    }

    @Test
    public void testCrearPalabrasConFrequenciaDesdeTexto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        List<Integer> frequencias = new ArrayList<>();
        Palabra aux = new Palabra(null, null);
        List<Palabra> palabras = aux.crearPalabrasConFrequenciasDesdeTexto("Hola 10 Aloha 20", alfabeto, frequencias);
        assertEquals(2, palabras.size());
        assertEquals("hola", palabras.get(0).getNombre());
        assertEquals(10, frequencias.get(0).intValue());
        assertEquals("aloha", palabras.get(1).getNombre());
        assertEquals(20, frequencias.get(1).intValue());
    }

    @Test
    public void testCrearPalabrasConFrequenciaDesdeTextoError() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        List<Integer> frequencias = new ArrayList<>();

        exceptionRule.expect(WrongTextoFrequencias.class);
        exceptionRule.expectMessage("Se ha introducido un texto de frecuencias incorrecto");
        Palabra aux = new Palabra(null, null);
        List<Palabra> palabras = aux.crearPalabrasConFrequenciasDesdeTexto("Hola 10 Aloha", alfabeto, frequencias);
    }

    @Test
    public void testCrearPalabrasConFrequenciaDesdeTextoErrorDeTexto() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        List<Integer> frequencias = new ArrayList<>();

        exceptionRule.expect(WrongTextoFrequencias.class);
        exceptionRule.expectMessage("Se ha introducido un texto de frecuencias incorrecto");

        Palabra aux = new Palabra(" ", null);
        List<Palabra> palabras = aux.crearPalabrasConFrequenciasDesdeTexto("Hola 10 Aloha alola", alfabeto, frequencias);

    }

    @Test
    public void testCrearPalabrasConFrequenciaDesdeTextoErrorDeFrequencia() throws Exception {
        Alfabeto alfabeto = new Alfabeto("alfabeto", "hola0123", false);
        List<Integer> frequencias = new ArrayList<>();

        exceptionRule.expect(WrongTextoFrequencias.class);
        exceptionRule.expectMessage("Se ha introducido un texto de frecuencias incorrecto");

        Palabra aux = new Palabra(null, null);
        List<Palabra> palabras = aux.crearPalabrasConFrequenciasDesdeTexto("10 Hola 20 Aloha", alfabeto, frequencias);
    }

    @Test
    public void testFrecuenciaSimbolosEnPalabra() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        Palabra palabra = new Palabra("hola", alfabeto);
        int[][] MatrizEsperada = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0}
        };
        int[][] frequencias = palabra.frequenciaEntreSimbolos(alfabeto);

        // Verifica que las matrices sean iguales
        assertEquals(MatrizEsperada.length, frequencias.length);
        assertArrayEquals(MatrizEsperada, frequencias);
    }
    @Test
    public void testFrecuenciaSimbolosEnPalabra2() throws Exception {
        Alfabeto alfabeto = CreateAlfabetoTest.alfabetoTest();
        Palabra palabra = new Palabra("aloha", alfabeto);
        int[][] MatrizEsperada = {
                {0, 0, 0, 0, 2},
                {0, 0, 1, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {2, 1, 0, 1, 0}
        };
        int[][] frequencias = palabra.frequenciaEntreSimbolos(alfabeto);

        // Verifica que las matrices sean iguales
        assertEquals(MatrizEsperada.length, frequencias.length);
        assertArrayEquals(MatrizEsperada, frequencias);
    }
}
