package prop.teclado.domain.controllers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de la clase Teclado
 * Author: Guillem Angulo Hidalgo
 */
public class CtrTeclado {
    private static List<Teclado> teclados;

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------
    public CtrTeclado(List<List<String>> tecladosPersistencia, CtrDominio cd) throws NoTxt, FileNotFound, WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, NoLenguaje, NoSimbolosOnAlfabeto {
        this.teclados = new ArrayList<>(); //Se deben cargar los teclados de la persistencia

        for (int i = 0; i < tecladosPersistencia.size(); i+=2) {
            Lenguaje lenguaje = cd.lenguajeControlador(tecladosPersistencia.get(i+1));
            String nombreTeclado = tecladosPersistencia.get(i).get(0);
            String creacionTeclado = tecladosPersistencia.get(i).get(1);
            String modificacionTeclado = tecladosPersistencia.get(i).get(2);
            //pasar el segundo elemento de la lista a int
            int numTecles = Integer.parseInt(tecladosPersistencia.get(i).get(3));
            //pasar  el resto de elementos a una lista de caracteres
            List<Character> teclas = new ArrayList<>();
            for (int j = 4; j < tecladosPersistencia.get(i).size(); j++) {
                teclas.add(tecladosPersistencia.get(i).get(j).charAt(0));
            }
            Teclado tecladoCreado = new Teclado(nombreTeclado, creacionTeclado, modificacionTeclado,
                    lenguaje, teclas);
            teclados.add(tecladoCreado);
        }
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------
    //funcion encargada de comprobar que no haya mas nombres iguales en la lista de teclados
    public void checkNombresTeclados(Teclado teclado, int rep) {
        // Verifica si el nombre del teclado ya existe en la lista
        boolean nombreExiste = teclados.stream()
                .anyMatch(t -> t.getNombre().equals(teclado.getNombre()));

        // Si el nombre ya existe, añade un "New" al nombre del teclado pasado por parámetro
        if (nombreExiste) {
            teclado.setNombre(teclado.getNombre() + " (" + rep + ")");
            checkNombresTeclados(teclado, ++rep); // Comprueba si el nuevo nombre ya existe
        }
    }

    //funcion encargada eliminar un teclado de la lista de teclados y retornar los datos para hacerlo en la persistencia
    public String eliminarTeclado(String nombreTeclado, String dt) {
        Teclado teclado = getTeclado(nombreTeclado);
        StringBuilder datosLenguaje = new StringBuilder();
        datosLenguaje.append(datosPersistenciaTeclado(teclado));
        datosLenguaje.append(dt);
        teclados.remove(teclado);
        return datosLenguaje.toString();
    }

    //funcion encargada de obtener los datos de un teclado para la persistencia
    public String datosPersistenciaTeclado(Teclado teclado) {
        StringBuilder datosTeclado = new StringBuilder();
        datosTeclado.append(teclado.getNombre()).append("%/%/%");
        datosTeclado.append(teclado.getFCreacion()).append("%/%/%");
        datosTeclado.append(teclado.getFModificacion()).append("%/%/%");
        datosTeclado.append(teclado.getTeclas().size()).append("%/%/%");
        for (Tecla tecla : teclado.getTeclas()) {

            //si es el ultimo elemento no añadimos punto y coma en vez de coma
            if (teclado.getTeclas().indexOf(tecla) == teclado.getTeclas().size() - 1)
                datosTeclado.append(tecla.getSimbolo()).append("/&/&/&/");
            else
                datosTeclado.append(tecla.getSimbolo()).append("%/%/%");
        }
        return datosTeclado.toString();
    }

    // ----------------------------------------- GETTERS -----------------------------------------
    //funcion que devuelve la lista de teclados
    public List<Teclado> getTeclados() {
        return teclados;
    }

    //funcion que devuelve el nombre de todos los teclados del sistema
    public List<String> getNombresTeclados() {
        return teclados.stream().map(Teclado::getNombre).collect(Collectors.toList());
    }

    //funcion que devuelve el teclado con el nombre pasado por parametro
    public Teclado getTeclado(String nombreTeclado) {
        return teclados.stream()
                .filter(t -> nombreTeclado.equals(t.getNombre()))
                .findFirst()
                .orElse(null);
    }

    //funcion que obtiene el nombre de todos los textos de un teclado
    public List<String> getNombresTextos(String nombreLenguaje) {
        List<String> nombresTextos = new ArrayList<>();
        Teclado teclado = getTeclado(nombreLenguaje);
        for (Texto texto : teclado.getLenguaje().getTextos()) {
            nombresTextos.add(texto.getNombre());
        }
        return nombresTextos;
    }

    //funcion que devuelve los datos de todos los teclados
    public List<List<String>> getDatosTeclados() {
        return obtenerDatos(teclados);
    }

    //funcion que se encarga de buscar los teclados dependiendo del nombre
    public List<List<String>> busquedaTeclados(String nombre) {
        List<Teclado> tecladosFiltrados = filtrarTecladosPorNombre(nombre);
        return obtenerDatos(tecladosFiltrados);
    }

    //funcion que se encarga de obtener los teclados del sistema segun el nombre a buscar
    private List<Teclado> filtrarTecladosPorNombre(String busqueda) {
        return teclados.stream()
                .filter(t -> t.getNombre().contains(busqueda))
                .collect(Collectors.toList());
    }

    //funcion que se encarga de obtener los datos de los teclados para mostrar en la vista
    private List<List<String>> obtenerDatos(List<Teclado> teclados) {
        List<List<String>> datosTeclados = new ArrayList<>();
        for (Teclado teclado : teclados) {
            List<String> datosTeclado = new ArrayList<>();
            datosTeclado.add(teclado.getNombre());
            datosTeclado.add(teclado.getLenguaje().getNombre());
            datosTeclado.add(teclado.getFCreacion());
            datosTeclado.add(teclado.getFModificacion());
            datosTeclados.add(datosTeclado);
        }
        return datosTeclados;
    }

    // ----------------------------------------- SETTERS -----------------------------------------
    //funcion que añade un teclado a la lista de teclados
    public void addTeclado(Teclado teclado) {
        teclados.add(teclado);
    }

    //funcion que se encarga de cambiar el nombre de un teclado
    public void cambiarNombreTeclado(String nombreTeclado, String nuevoNombre) {
        Teclado teclado = getTeclado(nombreTeclado);
        teclado.setNombre(nuevoNombre);
    }

    //funcion que obtiene la disposicion de las teclas de un teclado
    public Character[][] getDisposicionTeclas(String tecladoAVisualizar) {
        Teclado t = getTeclado(tecladoAVisualizar);
        return t.getTeclasComoMatriz();

    }

    //funcion que se encarga de poner la fecha de modificacion de un teclado
    public void setFechaModificacion(String fecha, String nombreteclado) {
        Teclado t = getTeclado(nombreteclado);
        t.setDModificacion(fecha);
    }

    //funcion que se encarga de cambiar la posicion de dos teclas de un teclado
    public void cambiarPosicion(Character c1, Character c2, String nteclado) {
        Teclado t = getTeclado(nteclado);
        t.cambiarTeclas(c1, c2);
    }

    //funcion que obtiene el lenguaje de un teclado concreto
    public Lenguaje obtenerLenguaje(String nombreteclado) {
        Teclado t = getTeclado(nombreteclado);
        return t.getLenguaje();
    }
}
