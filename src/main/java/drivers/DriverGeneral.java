package drivers;

import prop.teclado.domain.classes.Lenguaje;
import prop.teclado.domain.classes.Teclado;
import prop.teclado.domain.classes.exceptions.*;

import java.util.Scanner;

/**
 * Driver interactivo para testear la funcionalidad principal.
 * Author: Guillem Angulo Hidalgo
 */

public class DriverGeneral {
        private static Scanner in = null;

        public static void main(String[] args) {
            System.out.println("Driver Funcionalidad Principal");
            System.out.println("-----------------------------");

            init();
            String input;

            while (true) {
                mostrarOpciones();
                input = in.nextLine();

                if (input.equals("0")) {
                    break; // Salir del bucle
                } else if (input.equals("1")) {
                    dCrearTeclado();
                } else if (input.equals("2")){
                    dModificarTeclado();

                } else if (input.equals("3")) {
                    dEliminarTeclado();
                }
                else {
                    System.out.println("Opcion no valida");
                }
            }
            System.out.println("Cerrando el driver ...");
            in.close();
        }

        //muestra por pantalla las opciones del driver
        public static void mostrarOpciones() {
            System.out.println("Introduce una opcion:");
            System.out.println("1) Crear Teclado");
            System.out.println("2) Modificar Teclado");
            System.out.println("3) Eliminar Teclado");
            System.out.println("0) Salir");
        }

        //inicializa el scanner
        public static void init(){
            in = new Scanner(System.in);
        }

        //Funcion que sigue los pasos para crear un alfabeto, a partir de un nombre y una lista de simbolos
        public static void dCrearTeclado()  {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce el nombre del Teclado:");
            String nombre = in.nextLine();
            System.out.println("Para crear un teclado debemos primero crear un lenguaje");

            //DriverGestionDeLenguajes.driverLenguaje();

            System.out.println("-----------------------------");
            mostrarOpciones2();
            String input = in.nextLine();
            switch (input) {
                case "1":
                    DriverGestionDeLenguajes.crearLenguajeTN();
                    break;
                case "2":
                    DriverGestionDeLenguajes.crearLenguajeTF();
                    break;
                case "3":
                    DriverGestionDeLenguajes.crearLenguajeDesdeFichero(true);
                    break;
                case "4":
                    DriverGestionDeLenguajes.crearLenguajeDesdeFichero(false);
                    break;
                case "0":
                    System.out.println("Cerrando el driver ...");
                    in.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

            Lenguaje lenguaje = DriverGestionDeLenguajes.getLenguaje(); // getter del llenguatge

            //se crea el teclado
            Teclado t = null;
            try {
                t = new Teclado(nombre, lenguaje);
            } catch (NoLenguaje n) {
                System.out.println("Error: El lenguaje está vacío");
            } catch (NoSimbolosOnAlfabeto n) {
                System.out.println("Error: El alfabeto no tiene simbolos");
            }
            //se imprime el teclado
            if (t != null) t.imprimirTeclado();
        }

        public static void dModificarTeclado() {
            Scanner in = new Scanner(System.in);
            System.out.println("Funcion en desarrollo");
            in.close();
        }

        public static void mostrarOpciones2() {
            System.out.println("Introduce una opcion:");
            System.out.println("1) Crear un nuevo lenguaje desde texto normal");
            System.out.println("2) Crear un nuevo lenguaje desde texto de frecuencia");
            System.out.println("3) Crear un nuevo lenguaje con un texto normal desde fichero");
            System.out.println("4) Crear un nuevo lenguaje con un texto de frecuencia desde fichero");
            System.out.println("0) Salir");
    }

    public static void dEliminarTeclado() {
        Scanner in = new Scanner(System.in);
        System.out.println("Funcion en desarrollo");
        in.close();
    }
}
