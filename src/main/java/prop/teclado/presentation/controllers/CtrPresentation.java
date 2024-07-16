package prop.teclado.presentation.controllers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.domain.controllers.CtrDominio;
import prop.teclado.presentation.views.*;
import prop.teclado.presentation.views.components.DialogEliminarTextoLenguaje;
import prop.teclado.presentation.views.components.DialogError;
import prop.teclado.presentation.views.components.DialogRemoveLenguaje;
import prop.teclado.presentation.views.components.DialogRemoveTeclado;

import java.util.List;

/**
 * Controlador de la capa de presentación
 * Author: Guillem Angulo y Joan Martínez
 */
public class CtrPresentation {
    private static CtrDominio cd;

    //funcion de inicializacion de la presentacion
    public void init() {
        cd = CtrDominio.getInstance();
    }

    //------------------------------------------ RUN VISTES------------------------------------------
    public void runVistaPrincipal() {
        List<List<String>> datosTeclados = cd.getDatosTeclados();
        VistaPrincipal vistaPrincipal = new VistaPrincipal(this,datosTeclados);
    }

    public void runVistaCrearTeclado() {
        VistaCrearTeclado vistaCrearTeclado = new VistaCrearTeclado(this);
    }

    public void runVistaCrearLenguaje() {
        VistaCrearLenguaje vistaCrearLenguaje = new VistaCrearLenguaje(this);
    }

    public void runVistaVisualizarTeclado(String tecladoAVisualizar) {
        VistaVisualizarTeclado vistaVisualizarTeclado = new VistaVisualizarTeclado(this, tecladoAVisualizar);
    }

    public void runVistaEditarTeclado(String teclado) {
        VistaEditarTeclado vistaEditarTeclado = new VistaEditarTeclado(this, teclado);
    }

    public void runVistaModificarLenguaje(String teclado) {
        VistaModificarLenguaje vistaModificarLenguaje = new VistaModificarLenguaje(this, teclado);
    }
    public void runVistaCambiarPosicion(String teclado) {
        VistaCambiarPosicion vistaCambiarPosicion = new VistaCambiarPosicion(this, teclado);
    }
    public void runVistaCambiarNombre(String teclado) {
        VistaCambiarNombre vistaCambiarNombre = new VistaCambiarNombre(this, teclado);
    }

    public void runVistaAnadirTexto(String teclado) {
        VistaAnadirTexto vistaAnadirTexto = new VistaAnadirTexto(this, teclado);
    }

    //------------------------------------------ RUN DIALOGS------------------------------------------
    public void runDialogRemoveTeclado(VistaPrincipal vp) {
        List<String> nombresTeclados = cd.getNombresTeclados();
        if (nombresTeclados.isEmpty()) {
            DialogError.show("No hay teclados para eliminar");
        } else {
            DialogRemoveTeclado dialogRemoveTeclado = new DialogRemoveTeclado(this, nombresTeclados, vp);
        }
    }

    public void runDialogRemoveLenguaje(VistaPrincipal vp) {
        List<String> nombresLenguajes = cd.getNombresLenguajes();
        if (nombresLenguajes.isEmpty()) {
            DialogError.show("No hay lenguajes para eliminar");
        } else {
            DialogRemoveLenguaje dialogRemoveLenguaje = new DialogRemoveLenguaje(this, nombresLenguajes, vp);
        }
    }

    public void runDialogEliminarTextoLenguaje(String nombreTeclado, VistaModificarLenguaje vp) {
        List<String> nombresTextos = cd.getNombresTextos(nombreTeclado);
        if (nombresTextos.isEmpty()) {
            DialogError.show("No hay textos para eliminar");
        } else {
            DialogEliminarTextoLenguaje dialogEliminarTextoLenguaje =
                    new DialogEliminarTextoLenguaje(this, nombreTeclado, nombresTextos, vp);
        }
    }

    //------------------------------------------ LLAMADAS A DOMINIO-------------------------------------
    //obtiene los datos de los teclados para poderlos mostrar
    public List<List<String>> getDatosTeclados() {
        return cd.getDatosTeclados();
    }

    //obtiene los datos de los lenguajes para poder crear nuevos teclados
    public List<List<String>> getDatosLenguajes() {
        return cd.getDatosLenguajes();
    }

    //obtiene los datos de los teclados que contienen "nombre" en su nombre
    public List<List<String>> busquedaTeclados(String nombre) {
        return cd.busquedaTeclados(nombre);
    }

    //funcion que crea un nuevo teclado desde la presentacion
    public void crearTeclado(String nombreTeclado, String nombreLenguaje, List<String> datosAlfabeto,
                             List<String> datosTexto, boolean guardarLenguaje) throws Exception {
        cd.generarTeclado(nombreTeclado, nombreLenguaje, datosAlfabeto, datosTexto, guardarLenguaje);
    }

    //funcion que crea un nuevo lenguaje desde la presentacion
    public void crearLenguaje(String nombreLenguaje, List<String> datosAlfabeto, List<String> datosTexto) throws Exception {
        cd.generarLenguaje(nombreLenguaje, datosAlfabeto, datosTexto);
    }

    //funcion que obtiene los alfabetos para poder crear nuevos lenguajes o teclados
    public List<List<String>> getAlfabetosPredefinidos() {
        return cd.getDatosAlfabetos();
    }

    //funcion que obtiene la disposicion de las teclas del teclado pasado por parametro
    public Character[][] getDisposicionTeclas(String nombreTeclado) {return cd.getDisposicionTeclas(nombreTeclado);}

    public Teclado getTeclado(String nombreTeclado) {return cd.getTeclado(nombreTeclado); }

    //funcion que realiza el cambio de nombre de un teclado
    public void cambiarNombreTeclado(String nombredeseado, String nombreactual) {
        cd.cambiarNombreTeclado(nombredeseado, nombreactual);
    }

    //funcion que pone una nueva fecha de modificacion a un teclado
    public void setFechaModificacion(String fecha, String nombreteclado){
        cd.setFechaModificacion(fecha, nombreteclado);
    }

    //funcion que cambia la posicion de dos teclas
    public void cambiarPosicion(Character char1, Character char2, String nteclado) {
        cd.cambiarPosicion(char1, char2, nteclado);
    }

    //funcion que elimina un teclado
    public void eliminarTeclado(String nombreTeclado) {
        cd.eliminarTeclado(nombreTeclado);
    }

    //funcion que elimina un lenguaje
    public void eliminarLenguaje(String nombreLenguaje) {
        cd.eliminarLenguaje(nombreLenguaje);
    }

    //funcion que elimina un texto de un teclado
    public void eliminarTexto(String nombreTexto, String nombreTeclado)
            throws CantRemoveTexto, NoTextoFound {
        cd.eliminarTextoTeclado(nombreTexto, nombreTeclado);
    }

    //funcion que se encarga de añadir un nuevo texto estandar a un lenguaje
    public void addNewTextoEstandar(String nombreT, String texto, String nombreteclado)
            throws NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, WrongTextoFrequencias {cd.addNewTextoEstandar(nombreT, texto, nombreteclado);}

    //funcion que se encarga de añadir un nuevo texto de frecuencias a un lenguaje
    public void addNewTextoFrequencia(String nombreT, String texto, String nombreTeclado)
            throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        cd.addNewTextoFrequencia(nombreT, texto, nombreTeclado);
    }
    //funcion que se encarga de añadir un nuevo texto de fichero a un lenguaje
    public void addNewTextoFicheroTE(String path, String nombreTeclado)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        cd.addNewTextoFicheroTE(path, nombreTeclado);
    }
    //funcion que se encarga de añadir un nuevo texto de fichero a un lenguaje
    public void addNewTextoFicheroTF(String path, String nombreTeclado)
            throws NoTxt, WrongTextoFrequencias, FileNotFound, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto {
        cd.addNewTextoFicheroTF(path, nombreTeclado);
    }
}
