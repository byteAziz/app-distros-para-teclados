package drivers;

import prop.teclado.domain.classes.*;
import prop.teclado.domain.classes.exceptions.*;

import java.util.Scanner;
/**
 * Driver interactivo para testear las funcionalidades de palabra.
 * Author: Joan Martínez Soria
 */
public class DriverPalabra {
    private static Scanner in = null;

    public static void main(String[] args) {
        System.out.println("Driver de la clase Palabra");
        System.out.println("-----------------------------");

        in = new Scanner(System.in);

        while (true) {
            mostrarOpciones();
            String input = in.nextLine();

            if (input.equals("0")) {
                break; // Salir del bucle
            } else if (input.equals("1")) {
                dCrearPalabra();
            } else {
                System.out.println("Opcion no válida.");
            }
        }

        System.out.println("Cerrando el driver ...");
        in.close();
    }

    //muestra por pantalla las opciones del driver
    public static void mostrarOpciones() {
        System.out.println("Introduce una opcion:");
        System.out.println("1) Crear Palabra");
        System.out.println("0) Salir");
    }

    //crea una palabra a partir de un alfabeto
    public static void dCrearPalabra()  {
        try {
            System.out.println("Creando alfabeto para la palabra...");
            Alfabeto alfabeto = dCrearAlfabeto();

            System.out.println("Introduce la palabra que deseas crear:");
            String nombre = in.nextLine();
            try {
                Palabra palabra = new Palabra(nombre, alfabeto);

                System.out.println("Palabra creada correctamente:");
                System.out.println("Nombre: " + palabra.getNombre());
                System.out.println("Tamano: " + palabra.getSize());

                // Mostrar símbolos en la palabra con sus apariciones
                System.out.println("Simbolos en la palabra:");
                for (int i = 1; i < alfabeto.getSize(); i++) {
                    char letra = alfabeto.getSimbolos().get(i);
                    int apariciones = 0;
                    //calcula el nombre de apariciones del simbolo en la palabra
                    for(int j = 0; j < palabra.getSize(); j++){
                        if(palabra.getNombre().charAt(j) == letra){
                            apariciones++;
                        }
                    }
                    System.out.println(letra + " - Apariciones: " + apariciones);
                }
            } catch (SimboloNoPerteneceAlAlfabeto e) {
                System.out.println("Error: Hay algun simbolo de la palabra que no pertenece al alfabeto.");
            }
        } catch (NoSimbolosOnAlfabeto e) {
            System.out.println("Error: El alfabeto no tiene simbolos.");
        } catch (NoTxt e) {
            System.out.println("Error: El archivo proporcionado no es .txt");
        } catch (FileNotFound e) {
            System.out.println("Error: No se ha encontrado el fichero");
        }
    }

    //crea un alfabeto a partir de un nombre y una lista de simbolos
    public static Alfabeto dCrearAlfabeto() throws NoSimbolosOnAlfabeto, NoTxt, FileNotFound {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduce el nombre del alfabeto:");
        String nombreAlfabeto = in.nextLine();
        System.out.println("Introduce los simbolos del alfabeto:");
        String newSimbolos = in.nextLine();
        in.close();
        return new Alfabeto(nombreAlfabeto, newSimbolos, false);
    }
}
