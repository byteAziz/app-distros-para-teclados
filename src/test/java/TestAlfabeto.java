import org.junit.Test;
import prop.teclado.domain.classes.Alfabeto;

import java.net.URL;
import java.util.*;

import org.junit.rules.ExpectedException;
import org.junit.Rule;
import prop.teclado.domain.classes.exceptions.*;

import static org.junit.Assert.*;

/**
 * Tests Unitaris de la clase Alfabeto
 * Author: Joan Martínez Soria
 */

public class TestAlfabeto {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreateAlfabeto() throws NoTxt, FileNotFound{
        Alfabeto alfabeto = new Alfabeto("alfabeto", "A", false);

        assertEquals("alfabeto", alfabeto.getNombre());
        assertEquals(2, alfabeto.getSize());
        assertEquals('a', alfabeto.getSimbolos().get(1).charValue());
    }

    @Test
    public void testCrearAlfabetoNoTxt() throws FileNotFound, NoTxt {
        // Agrega la configuración para esperar la excepción
        exceptionRule.expect(NoTxt.class);
        exceptionRule.expectMessage("El archivo no es un .txt");

        // Llama al método que debe lanzar la excepción
        ClassLoader classLoader = TestAlfabeto.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TestNoTxt.java"))
                .getPath();
        new Alfabeto("nombre",path, true);
    }
    @Test
    public void testCrearAlfabetoWrongPath() throws FileNotFound, NoTxt {
        // Agrega la configuración para esperar la excepción
        exceptionRule.expect(FileNotFound.class);
        exceptionRule.expectMessage("No se encuentra el archivo");

        // Llama al método que debe lanzar la excepción
        ClassLoader classLoader = TestAlfabeto.class.getClassLoader();
        URL path = classLoader.getResource("PruebaDeTxt/Hola.txt");
        //no se ha encontrado el archivo
        if (path == null) throw new FileNotFound();

        new Alfabeto("nombre",path.getPath(), true);
    }

    @Test
    public void testCrearAlfabetoFichero() throws FileNotFound, NoTxt {
        ClassLoader classLoader = TestAlfabeto.class.getClassLoader();
        //se carga el archivo desde los recursos
        String path = Objects.requireNonNull(classLoader.getResource("PruebaDeTxt/TestAlfabeto.txt"))
                .getPath();
        // Agrega la configuración para esperar la excepción
        Alfabeto a = new Alfabeto("nombre", path, true);
        assertEquals("nombre", a.getNombre());
        assertEquals('a', a.getSimbolos().get(1).charValue());
        assertEquals('b', a.getSimbolos().get(2).charValue());
        assertEquals('c', a.getSimbolos().get(3).charValue());
        assertEquals(4, a.getSize());
    }

}

