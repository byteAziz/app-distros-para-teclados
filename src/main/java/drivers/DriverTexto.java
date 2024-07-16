package drivers;

import prop.teclado.domain.classes.Alfabeto;

import prop.teclado.domain.classes.Texto;
import prop.teclado.domain.classes.TextoFrecuencias;
import prop.teclado.domain.classes.TextoEstandar;
import prop.teclado.domain.classes.exceptions.*;

import java.util.Scanner;
/**
 * Driver interactivo para testear las funcionalidades de texto.
 * Author: Joan Martínez Soria
 */
public class DriverTexto {
    private static Scanner in = null;

    public static void main(String[] args) {
        System.out.println("Driver de la clase Texto");
        System.out.println("-----------------------------");

        init();
        String input;

        while (true) {
            mostrarOpciones();
            input = in.nextLine();

            if (input.equals("0")) {
                break; // Salir del bucle
            } else if (input.equals("1")) {
                dCrearTextoEstandar();
            } else if (input.equals("2")) {
                dCrearTextoEstandarDesdeFichero();
            } else if (input.equals("3")) {
                dCrearTextoFrequencia();
            } else if (input.equals("4")) {
                dCrearTextoFrequenciaDesdeFichero();
            } else {
                System.out.println("Opcion no valida");
            }
        }
        System.out.println("Cerrando el driver ...");
        in.close();
    }

    //muestra por pantalla las opciones del driver
    public static void mostrarOpciones() {
        System.out.println("Introduce una opcion:");
        System.out.println("1) Crear Texto estandar");
        System.out.println("2) Crear Texto estandar desde un archivo .txt");
        System.out.println("3) Crear Texto con Frencuencias");
        System.out.println("4) Crear Texto con Frecuencias desde un archivo .txt");
        System.out.println("0) Salir");
    }

    //inicializa el scanner
    public static void init() {
        in = new Scanner(System.in);
    }

    //crea un texto a partir de un alfabeto
    public static void dCrearTextoEstandar() {
        try {
            Alfabeto alfabeto = crearAlfabeto();

            Scanner in = new Scanner(System.in);
            System.out.println("Introduce el nombre del texto:");
            String nombre = in.nextLine();
            System.out.println("Introduce el texto:");
            String texto = in.nextLine();
            TextoEstandar t = new TextoEstandar(nombre, texto);
            t.crearTexto(nombre, alfabeto, texto);

            System.out.println("Texto creado correctamente:");
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Tamano del texto: " + t.getSize());
            System.out.println("Nombre de palabras diferentes que aparecen en el texto: " + t.getPalabrasEnTexto().size());
            in.close();

        }catch (NoPalabrasEnTexto e) {
            System.out.println("Error: No hay palabras en el texto");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: Aparece un simbolo que no pertenece al alfabeto");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: Es necesario asignar sombolos al alfabeto");
        } catch (NoTxt e) {
            System.out.println("Error: El archivo no es un archivo .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el archivo");
        }
        System.out.println(" ");
    }

    //crea un texto a partir de un alfabeto y un archivo
    public static void dCrearTextoEstandarDesdeFichero() {
        try {

            Alfabeto alfabeto = crearAlfabeto();

            Scanner in = new Scanner(System.in);
            System.out.println("Introduce la ruta del archivo de texto:");
            String filePath = in.nextLine();
            Texto t = new TextoEstandar("", "");
            t=t.crearTextoFichero(filePath, alfabeto, Texto.TipoTexto.ESTANDAR);

            System.out.println("Texto creado correctamente desde el archivo:");
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Tamano del texto: " + t.getSize());
            System.out.println("Nombre de palabras diferentes que aparecen en el texto: " + t.getPalabrasEnTexto().size());
            in.close();

        } catch (NoTxt e) {
            System.out.println("Error: El archivo no es un archivo .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el archivo");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: No hay palabras en el texto");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: Aparece un simbolo que no pertenece al alfabeto");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: Es necesario asignar símbolos al alfabeto");
        }  catch (WrongTextoFrequencias e) {
            System.out.println("Error: El archivo que contiene el texto de frequencias no tiene el formato correcto");
        }
        System.out.println(" ");
    }


    public static void dCrearTextoFrequencia() {
        try {
            Alfabeto alfabeto = crearAlfabeto();

            Scanner in = new Scanner(System.in);
            System.out.println("Introduce el nombre del texto:");
            String nombre = in.nextLine();
            System.out.println("Introduce el texto (primero siempre va la palabra y luego su frequencia):");
            String texto = in.nextLine();
            TextoFrecuencias t = new TextoFrecuencias(nombre, texto);
            t.crearTexto(nombre, alfabeto, texto);

            System.out.println("Texto de frequencias creado correctamente:");
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Tamano del texto: " + t.getSize());
            System.out.println("Nombre de palabras diferentes que aparecen en el texto: " + t.getPalabrasEnTexto().size());
            in.close();

        }catch (NoPalabrasEnTexto e) {
            System.out.println("Error: No hay palabras en el texto");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: Aparece un simbolo que no pertenece al alfabeto");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: Es necesario asignar sombolos al alfabeto");
        } catch (WrongTextoFrequencias e) {
            System.out.println("Error: El texto de frequencias no tiene el formato correcto");
        } catch (NoTxt e) {
            System.out.println("Error: El archivo no es un archivo .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el archivo");
        }
        System.out.println(" ");
    }

    //crea un texto a partir de un alfabeto y un archivo
    public static void dCrearTextoFrequenciaDesdeFichero() {
        try {
            Alfabeto alfabeto = crearAlfabeto();

            Scanner in = new Scanner(System.in);
            System.out.println("Introduce la ruta del archivo de texto:");
            String filePath = in.nextLine();
            Texto t = new TextoFrecuencias("", "");
            t=t.crearTextoFichero(filePath, alfabeto, Texto.TipoTexto.FRECUENCIA);

            System.out.println("Texto con frequencias creado correctamente desde el archivo:");
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Tamano del texto: " + t.getSize());
            System.out.println("Nombre de palabras diferentes que aparecen en el texto: " + t.getPalabrasEnTexto().size());
            in.close();

        } catch (NoTxt e) {
            System.out.println("Error: El archivo no es un archivo .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el archivo");
        } catch (NoPalabrasEnTexto e) {
            System.out.println("Error: No hay palabras en el texto");
        } catch (SimboloNoPerteneceAlAlfabeto e) {
            System.out.println("Error: Aparece un simbolo que no pertenece al alfabeto");
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: Es necesario asignar símbolos al alfabeto");
        }  catch (WrongTextoFrequencias e) {
            System.out.println("Error: El archivo que contiene el texto de frequencias no tiene el formato correcto");
        }
        System.out.println(" ");
    }

    //crea un alfabeto a partir de un nombre y una lista de simbolos
    public static Alfabeto crearAlfabeto() throws NoSimbolosOnAlfabeto, NoTxt, FileNotFound {
        Scanner in = new Scanner(System.in);
        System.out.println("Creando alfabeto para el texto...");
        System.out.println("Introduce el nombre del alfabeto:");
        String nombreAlfabeto = in.nextLine();
        System.out.println("Introduce los simbolos del alfabeto:");
        String newSimbolos = in.nextLine();
        in.close();
        return new Alfabeto(nombreAlfabeto, newSimbolos, false);
    }
}
