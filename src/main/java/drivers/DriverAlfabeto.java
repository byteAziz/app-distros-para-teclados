package drivers;

import prop.teclado.domain.classes.Alfabeto;
import prop.teclado.domain.classes.exceptions.*;

import java.util.*;
/**
 * Driver interactivo para testear las funcionalidades de alfabeto.
 * Author: Joan Martínez Soria
 */
public class DriverAlfabeto{
    private static Scanner in = null;

    public static void main(String[] args) throws NoSimbolosOnAlfabeto, NoTxt, FileNotFound {
        System.out.println("Driver de la clase Alfabeto");
        System.out.println("-----------------------------");

        init();
        String input;

        while (true) {
            mostrarOpciones();
            input = in.nextLine();

            if (input.equals("0")) {
                break; // Salir del bucle
            } else if (input.equals("1")) {
                dCrearAlfabeto();
            } else if (input.equals("2")){
                dCrearAlfabetoFichero();
            } else {
                System.out.println("Opcion no valida");
            }
        }
        System.out.println("Cerrando el driver ...");
        in.close();
    }

    //muestra por pantalla el alfabeto creado
    public static void mostrarAlfabeto(Alfabeto a) {
        System.out.println("Nombre: " + a.getNombre());
        System.out.println("Size: " + a.getSize());
        System.out.println("Simbolos: ");
        for (Character s : a.getSimbolos()) {
            System.out.println(s + " ");
        }
        System.out.println("Alfabeto creado correctamente");
    }

    //muestra por pantalla las opciones del driver
    public static void mostrarOpciones() {
        System.out.println("Introduce una opcion:");
        System.out.println("1) Crear Alfabeto");
        System.out.println("2) Crear Alfabeto a partir de un fichero .txt");
        System.out.println("0) Salir");
    }

    //inicializa el scanner
    public static void init(){
        in = new Scanner(System.in);
    }

    //Funcion que sigue los pasos para crear un alfabeto, a partir de un nombre y una lista de simbolos
    public static void dCrearAlfabeto() {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduce el nombre del alfabeto:");
        String nombre = in.nextLine();
        System.out.println("Introduce los simbolos del alfabeto:");
        String newSimbolos = in.nextLine();
        try {
            //se intenta crear el alfabeto
            Alfabeto a = new Alfabeto(nombre, newSimbolos, false);
            //escribe por pantalla el alfabeto creado
            mostrarAlfabeto(a);
            //se capturan las excepciones y se muestran por pantalla
        } catch (NoTxt e) {
            System.out.println("Error: El archivo proporcionado no es .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el fichero");
        }
        System.out.println(" ");
        in.close();
    }

    //Funcion que sigue los pasos para crear un alfabeto, a partir de un fichero
    public static void dCrearAlfabetoFichero ()  {
        Scanner in = new Scanner(System.in);

        //se crea un alfabeto a partir de un fichero introducido por el usuario
        try {
            System.out.println("Introduce el path donde se encuentra tu alfabeto:");
            String path = in.nextLine();
            Alfabeto a = new Alfabeto("",path, true);
            mostrarAlfabeto(a);
        } catch (NoTxt e) {
            System.out.println("Error: El archivo proporcionado no es .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el fichero");
        }

        System.out.println(" ");
        in.close();
    }
}