import org.junit.Assert;
import org.junit.Test;
import prop.teclado.domain.classes.Alfabeto;
import prop.teclado.domain.classes.exceptions.FileNotFound;
import prop.teclado.domain.classes.exceptions.NoTxt;
import prop.teclado.persistence.controllers.CtrPersistence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersistencaiAlfabeto {

    @Test
    public void testObtenerAlfabetos() throws NoTxt, FileNotFound {
        CtrPersistence cp = new CtrPersistence();
        List<List<String>> datosalfabeto = cp.obtenerAlfabetos();
        List<Alfabeto> alfabetos = new ArrayList<>();
        for (List<String> alfabeto : datosalfabeto) {
            Alfabeto a = new Alfabeto(alfabeto.get(0), alfabeto.get(1), false);
            alfabetos.add(a);
        }
        assertEquals(alfabetos.get(0).getNombre(), "abecedario");
        assertEquals(alfabetos.get(0).getSimbolos().get(1).charValue(), 'a');
        assertEquals(alfabetos.get(1).getNombre(), "Alfabeto_ABC");
    }
}
