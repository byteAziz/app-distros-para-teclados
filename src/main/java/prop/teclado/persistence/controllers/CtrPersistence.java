package prop.teclado.persistence.controllers;

import prop.teclado.persistence.classes.PersistenceTeclado;
import prop.teclado.persistence.classes.PersistenceLenguaje;
import prop.teclado.persistence.classes.PersistenceAlfabeto;

import java.util.List;

/**
 * Controlador de la persistencia
 * Author: Joan Martínez Soria
 */
public class CtrPersistence {
    private PersistenceTeclado pt;
    private PersistenceLenguaje pl;
    private static PersistenceAlfabeto pa;
    //inizialización de la instancia "aeger" de la clase para hacer el Singleton "thread safe"
    private static CtrPersistence instance = new CtrPersistence();

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    public CtrPersistence () {
        //obtenemos las instancias de las clases de persistencia (Son singletons)
        pt = PersistenceTeclado.getInstance();
        pl = PersistenceLenguaje.getInstance();
        pa = PersistenceAlfabeto.getInstance();
    }

    // ------------------------------------------ FUNCIONES------------------------------------------
    public void guardarTeclado(List<String> datosTeclado, List<String> datosLenguaje) {
        pt.guardarTeclado(datosTeclado, datosLenguaje);
    }

    public List<List<String>> obtenerTeclados(){
        return pt.obtenerTeclados();
    }

    public void eliminarTeclado(String datosTeclado) {
        pt.eliminarTeclado(datosTeclado);
    }

    public void modificarTextoLenguajeTeclado(String datosAntiguos, String nuevosDatos) {
        pt.modificarTextoLenguaje(datosAntiguos, nuevosDatos);
    }

    public void guardarLenguaje(List<String> datosLenguaje) {
        pl.guardarLenguaje(datosLenguaje);
    }

    public List<List<String>> obtenerLenguajes() {
        return pl.obtenerLenguajes();
    }

    public void eliminarLenguaje(String datosLenguaje) {
        pl.eliminarLenguaje(datosLenguaje);
    }

    public static List<List<String>> obtenerAlfabetos() {
        return pa.obtenerAlfabetos();
    }



    // ------------------------------------------ GETTERS ------------------------------------------
    public static CtrPersistence getInstance() {
        return instance;
    }
}

