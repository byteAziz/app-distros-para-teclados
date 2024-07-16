package prop.teclado.domain.classes;

import prop.teclado.domain.classes.exceptions.*;
import prop.teclado.domain.classes.functions.ReadFile;

import java.util.List;
import java.util.ArrayList;

/*
 * Clase que representa un alfabeto con sus simbolos
 * Author: Joan Martinez Soria & Tahir Muhammad Aziz
 */

public class Alfabeto {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private String nombre;              // Nombre del alfabeto
    private List<Character> simbolos;   // Simbolos sobre los que se contruye alfabeto

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    // Crea un alfabeto a partir de un nombre y una lista de simbolos
    public Alfabeto(String nombre, List<Character> simbolos) {
        // inicializa el alfabeto con el nombre y el simbolo vacio
        initAlfabeto(nombre);

        // Se añaden los simbolos pasados como parametro a la lista de simbolos del alfabeto,
            // sin repeticion, sin distinguir entre mayusculas y minusculas y en el mismo orden
        for (Character simbolo : simbolos) {
            char c = Character.toLowerCase(simbolo);        // Se convierte a minusculas
            if (!this.simbolos.contains(c))                 // Añade el simbolo si no esta ya en el alfabeto
                this.simbolos.add(c);
        }
    }

    // Crea un alfabeto a partir de un nombre y un string simbolos_o_path que depende del boleano esPath:
        // esPath = true: indica que simbolos_o_path es un string que contiene el path a un archivo de texto que contiene los simbolos
        // esPath = false: indica que simbolos_o_path es un string que contiene los simbolos
    public Alfabeto(String nombre, String simbolos_o_path, boolean esPath) throws FileNotFound, NoTxt {
        // inicializa el alfabeto con el nombre y el simbolo vacio
        initAlfabeto(nombre);

        if (esPath)
            insertarSimbolosDesdePath(simbolos_o_path);
        else
            insertarSimbolosDesdeString(simbolos_o_path);
    }

    // ----------------------------------------- FUNCIONES -----------------------------------------

                            // ------------ FUNCIONES PRIVDAS ------------

    // Se inicializan los atributos del alfabeto
    private void initAlfabeto(String nombre) {
        if (nombre.isBlank()) 
            this.nombre = "AlfabetoDefault";        // Si el nombre esta vacio o en blanco, se asigna un nombre por defecto
        else 
            this.nombre = nombre;

        this.simbolos = new ArrayList<>();
        
        // Se añade el simbolo vacio al alfabeto al inicio de la lista: todos los alfabatos lo tienen
        this.simbolos.add(' ');
    }

                            // ------------ FUNCIONES PUBLICAS ------------

    // Se añaden los simbolos del string a la lista de simbolos del alfabeto, 
        // sin repeticion, sin distinguir entre mayusculas y minusculas y en el mismo orden
    public void insertarSimbolosDesdeString(String simbolos) {
        simbolos = simbolos.toLowerCase();                  // Se convierte a minusculas

        for (int i = 0; i < simbolos.length(); i++) {
            char c = simbolos.charAt(i);
            if (!this.simbolos.contains(c))                 // Añade el simbolo si no esta ya en el alfabeto
                this.simbolos.add(c);
        }
    }

    // Se añaden los simbolos del archivo que se encuentra en  la ruta al alfabeto sin repetir y sin distinguir entre mayusculas y minusculas
    public void insertarSimbolosDesdePath(String path) throws FileNotFound, NoTxt {
        // Se lee el contenido del archivo en path y se almacena en un string
        String simbolos = ReadFile.readFile(path);
        
        // Se usa el string para añadir los simbolos al alfabeto
        insertarSimbolosDesdeString(simbolos);
    }

    public boolean existeSimbolo(char simbolo) {
        return simbolos.contains(simbolo);
    }

    // ------------------------------------------ GETTERS ------------------------------------------

    public String getNombre() {
        return nombre;
    }
    
    public List<Character> getSimbolos() {
        return simbolos;
    }

    public int getSize() {
        return simbolos.size();
    }

    // ------------------------------------------ SETTERS ------------------------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
