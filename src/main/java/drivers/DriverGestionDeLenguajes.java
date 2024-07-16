package drivers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;

import java.util.List;
import java.util.Scanner;

/**
 * Driver interactivo para testear las funcionalidades de lenguaje.
 * Author: Joan Martínez Soria
 */
public class DriverGestionDeLenguajes {
    private static Scanner in = new Scanner(System.in);
    private static Lenguaje lenguaje;

    public static void main(String[] args) {
        driverLenguaje();
    }

    public static void driverLenguaje() {
        System.out.println("Driver de la clase Lenguaje");
        System.out.println("-----------------------------");

        boolean run = true;

        while (run) {
            mostrarOpciones();
            String input = in.nextLine();
            switch (input) {
                case "1":
                    crearLenguajeTN();
                    break;
                case "2":
                    modificarLenguajeTE();
                    break;
                case "3":
                    crearLenguajeTF();
                    break;
                case "4":
                    modificarLenguajeTF();
                    break;
                case "5":
                    crearLenguajeDesdeFichero(true);
                    break;
                case "6":
                    modificarLenguajeDesdeFichero(true);
                    break;
                case "7":
                    crearLenguajeDesdeFichero(false);
                    break;
                case "8":
                    modificarLenguajeDesdeFichero(false);
                    break;
                case "9":
                    eliminarTexto();
                    break;
                case "0":
                    System.out.println("Cerrando el driver ...");
                    in.close();
                    System.exit(0);
                    run = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    //se muestran las opciones del driver
    public static void mostrarOpciones() {
        System.out.println("Introduce una opcion:");
        System.out.println("1) Crear un nuevo lenguaje desde un texto estandar");
        System.out.println("2) Modificar un lenguaje existente agregando un texto estandar");
        System.out.println("3) Crear un nuevo lenguaje desde un texto de frecuencia");
        System.out.println("4) Modificar un lenguaje existente agregando un texto de frecuencia");
        System.out.println("5) Crear un nuevo lenguaje con un texto estandar desde un fichero");
        System.out.println("6) Modificar un lenguaje existente agregando un texto estandar desde un fichero");
        System.out.println("7) Crear un nuevo lenguaje con un texto de frecuencia desde un fichero");
        System.out.println("8) Modificar un lenguaje existente agregando un texto de frecuencia desde fichero");
        System.out.println("9) Eliminar un texto de un lenguaje");
        System.out.println("0) Salir");
    }

    //crea un lenguaje a partir de un texto estandar
    public static void crearLenguajeTN() {
        try {
            lenguaje = crearLenguaje(true, false);
            mostrarInformacionLenguaje(lenguaje);
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: El alfabeto no tiene simbolos");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        }  catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        } catch (NoTxt e) {
            System.out.println("Error: El fichero no es un txt");
        } catch (FileNotFound e) {
            System.out.println("Error: El fichero no existe");
        }

    }

    //modifica un lenguaje a partir de un texto estandar
    public static void modificarLenguajeTE() {
        if (lenguaje == null) {
            System.out.println("Error: Primero debes crear un lenguaje.");
            return;
        }
        try {
            String nombre = leerTexto();
            String contenido = leerContenido();
            lenguaje.addNewTextoEstandar(nombre, contenido);
            mostrarInformacionLenguaje(lenguaje);
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        }
    }

    //crea un lenguaje a partir de un texto de frecuencia
    public static void crearLenguajeTF() {
        try {
            lenguaje = crearLenguaje(false, false);
            mostrarInformacionLenguaje(lenguaje);
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: El alfabeto no tiene simbolos");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        } catch (NoTxt e) {
            System.out.println("Error: El fichero no es un txt");
        } catch (FileNotFound e) {
            System.out.println("Error: El fichero no existe");
        }


    }

    //modifica un lenguaje a partir de un texto de frecuencia
    public static void modificarLenguajeTF() {
        if (lenguaje == null) {
            System.out.println("Error: Primero debes crear un lenguaje.");
            return;
        }
        try {
            String nombre = leerTexto();
            String contenido = leerContenido();
            lenguaje.addNewTextoFrequencia(nombre, contenido);
            mostrarInformacionLenguaje(lenguaje);
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        }
    }

    //crea un lenguaje a partir de un fichero
    public static void crearLenguajeDesdeFichero(boolean isTextEstandar) {
        try {
            lenguaje = crearLenguaje(isTextEstandar, true);
            mostrarInformacionLenguaje(lenguaje);
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: El alfabeto no tiene simbolos");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        } catch (NoTxt e) {
            System.out.println("Error: El fichero no es un txt");
        } catch (FileNotFound e) {
            System.out.println("Error: El fichero no existe");
        }


    }

    //modifica un lenguaje a partir de un fichero
    public static void modificarLenguajeDesdeFichero(boolean isTextEstandar) {
        if (lenguaje == null) {
            System.out.println("Error: Primero debes crear un lenguaje.");
            return;
        }
        try {
            if (isTextEstandar) lenguaje.addNewTextoFicheroTE(leerRutaFichero());
            else lenguaje.addNewTextoFicheroTF(leerRutaFichero());
            mostrarInformacionLenguaje(lenguaje);
        } catch (NoTxt e) {
            System.out.println("Error: El fichero no es un txt");
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto no es un buen texto de frecuencias");
        } catch (FileNotFound e) {
            System.out.println("Error: El fichero no existe");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: El texto no tiene palabras");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: El texto tiene simbolos que no pertenecen al alfabeto");
        }
    }

    //crea un lenguaje a partir de un texto estandar
    public static Lenguaje crearLenguaje(boolean isTextEstandar, boolean fichero) throws WrongTextoFrequencias, NoPalabrasEnTexto, SimboloNoPerteneceAlAlfabeto, NoSimbolosOnAlfabeto,  NoTxt, FileNotFound {
        Lenguaje lenguaje = null;

        System.out.println("Introduce el nombre del lenguaje:");
        String nombreLenguaje = in.nextLine();

        Alfabeto alfabeto = crearAlfabeto();

        String nombre = "";
        String contenido = "";
        String path = "";
        if (!fichero){
            System.out.println("Tambien se debe crear un texto para el lenguaje...:");
            nombre = leerTexto();
            contenido = leerContenido();
        } else {
            System.out.println("Tambien se debe crear un texto para el lenguaje...:");
            path = leerRutaFichero();
        }

        if (fichero) {
            lenguaje = new Lenguaje(nombreLenguaje, path, alfabeto, isTextEstandar);
        } else {
            lenguaje = new Lenguaje(nombreLenguaje, nombre, contenido, alfabeto, isTextEstandar);
        }
        return lenguaje;
    }


    //crea un alfabeto a partir de un nombre y una lista de simbolos
    public static Alfabeto crearAlfabeto() throws NoTxt, FileNotFound {
        System.out.println("Antes,se debe crear un alfabeto...");
        System.out.println("Introduce el nombre del alfabeto que pertenecera al lenguaje:");
        String nombreAlfabeto = in.nextLine();
        System.out.println("Introduce los simbolos del alfabeto:");
        String newSimbolos = in.nextLine();
        return new Alfabeto(nombreAlfabeto, newSimbolos, false);

    }

    //lee el nombre de un texto
    public static String leerTexto() {
        System.out.println("Introduce el nombre del texto:");
        return in.nextLine();
    }

    //lee el contenido de un texto
    public static String leerContenido() {
        System.out.println("Introduce el contenido del texto:");
        return in.nextLine();
    }

    //lee la ruta de un fichero
    public static String leerRutaFichero() {
        System.out.println("Introduce la ruta del archivo de texto:");
        return in.nextLine();
    }

    //muestra la informacion de un lenguaje
    public static void mostrarInformacionLenguaje(Lenguaje lenguaje) {
        if (lenguaje == null) {
            System.out.println("Lenguaje no disponible");
            return;
        }
        System.out.println(" ");
        System.out.println("Nombre del lenguaje: " + lenguaje.getNombre());
        System.out.println("Alfabeto: " + lenguaje.getAlfabeto().getNombre());
        System.out.println("Textos en el lenguaje (" + lenguaje.getTextos().size() + "):");
        for (Texto texto : lenguaje.getTextos()) {
            System.out.println("- " + texto.getNombre());
        }
        System.out.println("Palabras en el lenguaje (" + lenguaje.getPalabras().size() + "):");

        for (Palabra palabra : lenguaje.getPalabras()) {
            System.out.println("- " + palabra.getNombre() + " (Aparece " + obtenerNumeroApariciones(palabra, lenguaje) + " veces)");
        }
        mostrarMatrizFrecuencias(lenguaje);
    }

    //obtiene el numero de apariciones de una palabra en un lenguaje
    public static int obtenerNumeroApariciones(Palabra palabra, Lenguaje lenguaje) {
        int apariciones = 0;
        for (Texto texto : lenguaje.getTextos()) {
            for (PalabraEnTexto palabraEnTexto : texto.getPalabrasEnTexto()) {
                if (palabraEnTexto.getPalabra().getNombre().equalsIgnoreCase(palabra.getNombre())) {
                    apariciones += palabraEnTexto.getNumApariciones();
                }
            }
        }
        return apariciones;
    }

    //muestra la matriz de distancias entre simbolos del alfabeto de un lenguaje
    public static void mostrarMatrizFrecuencias(Lenguaje lenguaje) {
        System.out.println("Matriz de frecuencia entre 2 simbolos en todas las palabras del lenguaje:");
        int[][] matrizFrecuencia = lenguaje.calcularFrecuenciaParDeSimbolosPalabra();
        List<Character> simbolosAlfabeto = lenguaje.getAlfabeto().getSimbolos();

        // Imprime los símbolos en la parte superior de la matriz
        System.out.print("\t");
        for (int i = 0; i < simbolosAlfabeto.size(); i++) {
            System.out.print(simbolosAlfabeto.get(i) + "\t");
        }
        System.out.println();

        for (int i = 0; i < matrizFrecuencia.length; i++) {
            // Imprime los símbolos en la parte izquierda de la matriz
            System.out.print(simbolosAlfabeto.get(i) + "\t");
            for (int j = 0; j < matrizFrecuencia[i].length; j++) {
                System.out.print(matrizFrecuencia[i][j] + "\t");
            }
            System.out.println();
        }
    }

    //elimina un texto de un lenguaje
    public static void eliminarTexto() {
        if (lenguaje == null) {
            System.out.println("Error: Primero debes crear un lenguaje.");
            return;
        } else if (lenguaje.getTextos().size() <= 1) {
            System.out.println("Error: El lenguaje no tiene ningun texto.");
            return;
        }
        try {
            //primero se muestran los textos que tiene el lenguaje
            System.out.println("Textos en el lenguaje (" + lenguaje.getTextos().size() + "):");
            for (Texto texto : lenguaje.getTextos()) {
                System.out.println("- " + texto.getNombre());
            }
            System.out.println("Introduce el nombre del texto que quieres eliminar:");
            String nombreTexto = in.nextLine();
            lenguaje.eliminateTexto(nombreTexto);
            mostrarInformacionLenguaje(lenguaje);
        } catch (NoTextoFound e) {
            System.out.println("Error: el texto que has introducido no existe en el lenguaje.");
        } catch (CantRemoveTexto e) {
            System.out.println("Error: El lenguaje debe tener minimo un texto.");
        }
    }

    public static Lenguaje getLenguaje() {
        return lenguaje;
    }
}
