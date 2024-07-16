import org.junit.Test;
import prop.teclado.domain.classes.Tecla;

import static org.junit.Assert.*;

/**
 * Tests Unitaris de la clase Tecla
 * Author: Guillem Angulo Hidalgo
 */
public class TestTecla {
    @Test
    public void testConstructoraTecla() {
        // Arrange
        char simbolo = 'A';

        // Act
        Tecla tecla = new Tecla(simbolo);

        // Assert
        assertNotNull(tecla);
        assertEquals('A', tecla.getSimbolo());
    }
}
