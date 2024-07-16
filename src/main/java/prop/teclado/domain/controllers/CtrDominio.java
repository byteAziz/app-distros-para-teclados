package prop.teclado.domain.controllers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.persistence.controllers.CtrPersistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de dominio de la aplicación.
 * Author: Joan Martínez Soria y Guillem Angulo Hidalgo
 */

//Centarlización de todos los controladores de dominio para poder acceder a ellos desde la capa de presentación
//de manera sencilla y rapida para los diferentes casos de uso de la aplicación
public class CtrDominio {
    //Clase SINGLETON

    // ----------------------------------------- ATRIBUTOS -----------------------------------------
    private static CtrLenguaje cl;
    private static CtrTeclado ct;
    private static CtrPersistence cp;
    //inizialización de la instancia "aeger" de la clase para hacer el Singleton "thread safe"
    private static CtrDominio instance = new CtrDominio();


    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    //la constructora es privada para evitar la libre instanciación de la clase
    private CtrDominio() {
        try {
            initCtr();
        } catch (NoTxt e) {
            System.out.println("No se ha encontrado el fichero de texto");
        } catch (WrongTextoFrequencias e) {
            System.out.println("El texto no tiene el formato correcto");
        } catch (FileNotFound e) {
            System.out.println("No se ha encontrado el fichero");
        } catch (NoLenguaje e) {
            System.out.println("No se ha encontrado el lenguaje");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("No se han encontrado simbolos en el alfabeto");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("No se han encontrado palabras en el texto");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("No se ha encontrado el simbolo en el alfabeto");
        }
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------
    //funcion para inicializar los controladores de dominio y obtener los datos de persistencia
    public void initCtr()
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoLenguaje, NoSimbolosOnAlfabeto,
            NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        cp = CtrPersistence.getInstance();
        List<List<String>> lenguajesPersistencia = cp.obtenerLenguajes();
        cl = new CtrLenguaje(lenguajesPersistencia);
        List<List<String>> tecladosPersistencia = cp.obtenerTeclados();
        ct = new CtrTeclado(tecladosPersistencia, this);
    }

    //funcion para crear un alfabeto a partir de los datos obtenidos
    public Alfabeto crearAlfabeto(List<String> datosAlfabeto) throws NoTxt, FileNotFound {
        return (datosAlfabeto.get(0) == "manual") ?
                new Alfabeto(datosAlfabeto.get(1), datosAlfabeto.get(2), false) :
                new Alfabeto(datosAlfabeto.get(1), datosAlfabeto.get(2), true);
    }
    //funcion para crear un lenguaje a partir de los datos obtenidos
    public Lenguaje crearLenguaje(List<String> datosTexto, String nombreLenguaje, Alfabeto alfabeto)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        Lenguaje lenguaje;
        //dependiendo del numero de datos que nos lleguen, se creara un texto a partir de un fichero o de un string
        if (datosTexto.size() == 2) {
            lenguaje = new Lenguaje(nombreLenguaje, datosTexto.get(0), alfabeto, datosTexto.get(1).equals("estandar"));
        } else {
            lenguaje = new Lenguaje(nombreLenguaje, datosTexto.get(0), datosTexto.get(1), alfabeto, datosTexto.get(2).equals("estandar"));
        }
        checkNombresLenguajes(lenguaje);
        return lenguaje;
    }
    //funcion para crear un lenguaje a partir de los datos obtenidos en su respectivo controlador
    public Lenguaje lenguajeControlador(List<String> lenguaje)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        return cl.crearLenguaje(lenguaje);
    }
    //funcion para crear un teclado a partir de los datos obtenidos en su respectivo controlador y comprueba el nombre
    public Teclado crearTeclado (Lenguaje lenguaje, String nombreTeclado) throws NoLenguaje, NoSimbolosOnAlfabeto {
        Teclado teclado = new Teclado(nombreTeclado, lenguaje);
        checkNombresTeclados(teclado);
        return teclado;
    }

    // CASO DE USO 1 -> Crear teclado
    // Falta revisar que lo que nos llega ya exista en el sistema (que no haya elementos iguales en el sistema)
    public void generarTeclado(String nombreTeclado, String nombreLenguaje, List<String> datosAlfabeto,
                               List<String> datosTexto, boolean guardarLenguaje) throws Exception {
        //A partir de los datos obtenidos, se crean el Alfabeto, el Lenguaje, el Texto y finalmente el Teclado
        Alfabeto alfabeto = crearAlfabeto(datosAlfabeto);
        //aqui se crea el texto que tendra el lenguaje para despues poder crear el lenguaje
        Lenguaje lenguaje = crearLenguaje(datosTexto, nombreLenguaje, alfabeto);
        //finalmente se crea el teclado
        if (nombreTeclado == null || nombreTeclado.isEmpty()) nombreTeclado = "Teclado " + lenguaje.getNombre();
        Teclado teclado = crearTeclado(lenguaje, nombreTeclado);
        //Finalmente se añaden los elementos creados a sus respectivas listas para ser guardados
        if (guardarLenguaje)
            guardarLenguaje(lenguaje);
        guardarTeclado(teclado);
    }

    //funcion para generar y guardar lenguajes desde la pantalla de creación de lenguajes
    public void generarLenguaje(String nombreLenguaje, List<String> datosAlfabeto, List<String> datosTexto)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        Alfabeto alfabeto = crearAlfabeto(datosAlfabeto);
        Lenguaje lenguaje = crearLenguaje(datosTexto, nombreLenguaje, alfabeto);
        guardarLenguaje(lenguaje);
    }

    //funcion recursiva para comprobar que no hay elementos con el mismo nombre en el sistema
    public void checkNombresLenguajes(Lenguaje lenguaje) {
        cl.checkNombresLenguajes(lenguaje, 1);
    }

    //funcion recursiva para comprobar que no hay elementos con el mismo nombre en el sistema
    public void checkNombresTeclados(Teclado teclado) {
        ct.checkNombresTeclados(teclado, 1);
    }

    //funcion para obtener los datos de un teclado a partir de su nombre
    private List<String> datosLenguajePersistencia(Lenguaje lenguaje) {
        List<String> datosLenguajePersistencia = new ArrayList<>();
        datosLenguajePersistencia.add(lenguaje.getNombre());
        for(Texto texto : lenguaje.getTextos()) {
            datosLenguajePersistencia.add(texto.getNombre());
            datosLenguajePersistencia.add(texto.getTexto());
            String tipo;
            if (texto.getType() == Texto.TipoTexto.FRECUENCIA) tipo = "false";
            else tipo = "true";
            datosLenguajePersistencia.add(tipo);
        }
        datosLenguajePersistencia.add(lenguaje.getAlfabeto().getNombre());
        StringBuilder simbolos = new StringBuilder();
        for (Character simbolo : lenguaje.getAlfabeto().getSimbolos()) {
                simbolos.append(simbolo);
        }
        datosLenguajePersistencia.add(simbolos.toString());
        return datosLenguajePersistencia;
    }
    private List<String> datosTecladoPersistencia(Teclado teclado) {
        List<String> datosTecladoPersistencia = new ArrayList<>();
        datosTecladoPersistencia.add(teclado.getNombre());
        datosTecladoPersistencia.add(teclado.getFCreacion());
        datosTecladoPersistencia.add(teclado.getFModificacion());
        datosTecladoPersistencia.add(String.valueOf(teclado.getTeclas().size()));
        for (Tecla tecla : teclado.getTeclas()) {
            datosTecladoPersistencia.add(""+tecla.getSimbolo());
        }
        return datosTecladoPersistencia;
    }

    //funcion para guardar los datos de un teclado en la persistencia
    private void guardarLenguaje(Lenguaje lenguaje) {
        addLenguaje(lenguaje);
        List<String> datosLenguajePersistencia = datosLenguajePersistencia(lenguaje);
        cp.guardarLenguaje(datosLenguajePersistencia);
    }

    //funcion para guardar los datos de un teclado en la persistencia
    private void guardarTeclado(Teclado teclado) {
        addTeclado(teclado);
        List<String> datosTecladoPersistencia = datosTecladoPersistencia(teclado);
        List<String> datosLenguajePersistencia = datosLenguajePersistencia(teclado.getLenguaje());
        cp.guardarTeclado(datosTecladoPersistencia, datosLenguajePersistencia);
    }

    // ----------------------------------------- GETTERS -----------------------------------------

    public static CtrDominio getInstance() {
        return instance;
    }

    //funcion para obtener lso nombres de todos los teclados del sistema
    public List<String> getNombresTeclados() {
        return ct.getNombresTeclados();
    }

    //funcion para obtener lso teclados del sistema, que se almacenen en el controlador de Teclados
    public Teclado getTeclado(String nombreTeclado) {
        return ct.getTeclado(nombreTeclado);
    }

    //funcion para obtener los datos necesarios para mostrar los teclados en la pantalla de teclados
    public List<List<String>> getDatosTeclados() {
        return ct.getDatosTeclados();
    }

    //Funcion encargada de Buscar los teclados que contengan la busqueda (Caso de uso)
    public List<List<String>> busquedaTeclados(String busqueda) {
        return ct.busquedaTeclados(busqueda);
    }

    //funcion que obtiene los nombres de todos los lenguajes del sistema
    public List<String> getNombresLenguajes() {
        return cl.getNombresLenguajes();
    }

    //funcion que obtiene los nombres de los textos que tiene el lenguaje de un teclado
    public List<String> getNombresTextos(String nombreTeclado) {
        return ct.getNombresTextos(nombreTeclado);
    }

    //funcion que obtiene los datos de los alfabetos almacenados en el sistema para su uso a la hora de crear un teclado
    public List<List<String>> getDatosAlfabetos() {
        return cp.obtenerAlfabetos();
    }

    //funcion que obtiene la disposicion de las teclas de un teclado para poder visualizar el teclado por pantalla
    //Caso de uso
    public Character[][] getDisposicionTeclas(String nombreTeclado) {return ct.getDisposicionTeclas(nombreTeclado);}

    //funcion que obtiene los datos de los lenguajes almacenados en el sistema para su uso a la hora de crear un teclado
    public List<List<String>> getDatosLenguajes() {
        return cl.getDatosLenguajes();
    }

    // ----------------------------------------- SETTERS -----------------------------------------
    //funcion que añade un lenguaje al controlador de lenguajes
    public void addLenguaje(Lenguaje lenguaje) {
        cl.addLenguaje(lenguaje);
    }
    //funcion que añade un teclado al controlador de teclados
    public void addTeclado(Teclado teclado) {
        ct.addTeclado(teclado);
    }

    //funcion que modifica el nombre de un teclado (Caso de uso)
    public void cambiarNombreTeclado(String nombredeseado, String nombreactual) {
        Teclado t = ct.getTeclado(nombreactual);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(t);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(t.getLenguaje());
        ct.cambiarNombreTeclado(nombreactual, nombredeseado);
        String fecha = obtenerfecha();
        t.setDModificacion(fecha);
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(t)+datosLenguajeAntiguos);
    }

    //funcion encargada de modificar la fecha de modificacion de un teclado
    public void setFechaModificacion(String fecha, String nombreteclado) {
        ct.setFechaModificacion(fecha, nombreteclado);
    }

    //funcion que modifica la disposicion de las teclas de un teclado (Caso de uso)
    public void cambiarPosicion(Character char1, Character char2, String nteclado) {
        Teclado t = ct.getTeclado(nteclado);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(t);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(t.getLenguaje());
        ct.cambiarPosicion(char1, char2, nteclado);
        String fecha = obtenerfecha();
        t.setDModificacion(fecha);
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(t)+datosLenguajeAntiguos);
    }
    //Funcion que elimina un teclado del sistema y de la persistencia
    public void eliminarTeclado(String nombreTeclado) {
        Teclado teclado = ct.getTeclado(nombreTeclado);
        String datosLenguaje = cl.datosLenguajePersistencia(teclado.getLenguaje());
        String datosTecladoCompletos = ct.eliminarTeclado(nombreTeclado, datosLenguaje);
        cp.eliminarTeclado(datosTecladoCompletos);
    }

    //funcion que elimina un lenguaje del sistema y de la persistencia
    //eliminar un lenguaje no elimina los teclados que lo contienen, solo se elimina para poder crear un
    //nuevo teclado con ese lenguaje
    public void eliminarLenguaje(String nombreLenguaje) {
        String datosLenguajeAEliminar = cl.eliminarLenjuage(nombreLenguaje);
        cp.eliminarLenguaje(datosLenguajeAEliminar);
    }

    //funcion encargada de agregar un nuevo texto estandar a un teclado
    public void addNewTextoEstandar(String nombreT, String texto, String nombreteclado)
            throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {
        Teclado teclado = ct.getTeclado(nombreteclado);
        String datosTextoAntiguos = ct.datosPersistenciaTeclado(teclado);
        Lenguaje l = ct.obtenerLenguaje(nombreteclado);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(teclado.getLenguaje());
        cl.addNewTextoEstandar(nombreT, texto, l);
        String fechamodificacion = obtenerfecha();
        teclado.setDModificacion(fechamodificacion);
        //como se ha modificado el texto del lenguaje, se debe aplicar el algoritmo de nuevo
        teclado.aplicarAlgoritmo();
        cp.modificarTextoLenguajeTeclado(datosTextoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(teclado)+cl.datosLenguajePersistencia(l));
    }

    //funcion encargada de agregar un nuevo texto de frequencias a un teclado
    public void addNewTextoFrequencia(String nombreT, String texto, String nombreTeclado)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        Teclado teclado = ct.getTeclado(nombreTeclado);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(teclado);
        Lenguaje l = ct.obtenerLenguaje(nombreTeclado);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(teclado.getLenguaje());
        cl.addNewTextoFrequencia(nombreT, texto, l);
        String fechamodificacion = obtenerfecha();
        teclado.setDModificacion(fechamodificacion);
        //como se ha modificado el texto del lenguaje, se debe aplicar el algoritmo de nuevo
        teclado.aplicarAlgoritmo();
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(teclado)+cl.datosLenguajePersistencia(l));
    }

    //funcion encargada de agregar un nuevo texto estandar a un teclado a partir de un fichero
    public void addNewTextoFicheroTE(String path, String nombreTeclado)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, FileNotFound, NoTxt {
        Teclado teclado = ct.getTeclado(nombreTeclado);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(teclado);
        Lenguaje l = ct.obtenerLenguaje(nombreTeclado);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(teclado.getLenguaje());
        cl.addNewTextoFicheroTE(path, l);
        String fechamodificacion = obtenerfecha();
        teclado.setDModificacion(fechamodificacion);
        //como se ha modificado el texto del lenguaje, se debe aplicar el algoritmo de nuevo
        teclado.aplicarAlgoritmo();
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(teclado)+cl.datosLenguajePersistencia(l));
    }

    //funcion encargada de agregar un nuevo texto de frequencias a un teclado a partir de un fichero
    public void addNewTextoFicheroTF(String path, String nombreTeclado)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, FileNotFound, NoTxt {
        Teclado teclado = ct.getTeclado(nombreTeclado);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(teclado);
        Lenguaje l = ct.obtenerLenguaje(nombreTeclado);
        String datosLenguajeAntiguos = cl.datosLenguajePersistencia(teclado.getLenguaje());
        cl.addNewTextoFicheroTF(path, l);
        String fechamodificacion = obtenerfecha();
        teclado.setDModificacion(fechamodificacion);
        //como se ha modificado el texto del lenguaje, se debe aplicar el algoritmo de nuevo
        teclado.aplicarAlgoritmo();
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos+datosLenguajeAntiguos,
                ct.datosPersistenciaTeclado(teclado)+cl.datosLenguajePersistencia(l));
    }

    //funcion encargada de eliminar un texto de un teclado
    public void eliminarTextoTeclado(String nombreTexto, String nombreTeclado)
            throws CantRemoveTexto, NoTextoFound {
        Teclado teclado = ct.getTeclado(nombreTeclado);
        String datosTecladoAntiguos = ct.datosPersistenciaTeclado(teclado);
        Lenguaje l = ct.obtenerLenguaje(nombreTeclado);
        String datosLenjuageAntiguos = cl.datosLenguajePersistencia(teclado.getLenguaje());
        cl.eliminarTextoTeclado(nombreTexto, l);
        //como se ha eliminado un texto del lenguaje, se debe aplicar el algoritmo de nuevo
        teclado.aplicarAlgoritmo();
        cp.modificarTextoLenguajeTeclado(datosTecladoAntiguos + datosLenjuageAntiguos,
                ct.datosPersistenciaTeclado(teclado) + cl.datosLenguajePersistencia(l));
    }

    //funcion auxiliar para obtener la fecha actual y poder modificar la fecha de modificacion de un teclado
    private String obtenerfecha() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaHoraActual.format(formatter);
    }
}
