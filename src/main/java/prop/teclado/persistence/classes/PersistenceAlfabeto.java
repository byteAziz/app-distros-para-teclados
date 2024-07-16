package prop.teclado.persistence.classes;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de la persistencia de los alfabetos
 * Author: Joan Martínez Soria
 */
public class PersistenceAlfabeto {

    private final String FILE; //direccion de los datos de la persistencia
    //inizialización de la instancia "aeger" de la clase para hacer el Singleton "thread safe"
    private static PersistenceAlfabeto instance = new PersistenceAlfabeto();

    //esta no necesita guardar la info, ya que no modificamos los alfabetos, solo los obtenemos, y podemos hacerlo
    //directamente desde el fichero de recursos, que en un .jar no se puede modificar
    public PersistenceAlfabeto() {
        //ruata donde se guardan los datos de la persistencia
        String filePath = System.getProperty("user.dir") + "/resources/alfabetos.prop";

        //mira si el fichero existe, si no, lo crea
        if (doesFileExist(filePath)) {
            FILE = filePath;
        } else {
            FILE = copyResourceToCustomLocation("alfabetos.prop", filePath);
        }
    }

    //funcion que mira si el fichero existe
    private static boolean doesFileExist(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (InvalidPathException e) {
            e.printStackTrace();
            return false;
        }
    }

    //funcion que copia los datos de los recursos del programa y crea un fichero donde guardar la info de la aplicacion
    private String copyResourceToCustomLocation(String resourcePath, String customLocation) {
        try {
            // Crear directorios si no existen
            System.out.println("Creando directorios: " + Paths.get(customLocation).getParent());
            Files.createDirectories(Paths.get(customLocation).getParent());

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                throw new FileNotFoundException("Recurso no encontrado: " + resourcePath);
            }

            Files.copy(inputStream, Paths.get(customLocation), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo creado/copiado: " + customLocation);

            return customLocation;
        } catch (IOException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente en tu aplicación
            throw new RuntimeException("Error al crear/copiar el archivo.", e);
        }
    }

    //funcion que obtiene los alfabetos de la persistencia
    public List<List<String>> obtenerAlfabetos() {
        List<List<String>> alfabetos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Suponiendo que cada fila tiene el formato: id,nombre,descripcion
                String[] datosAlfabeto = line.split("%/%/%");
                List<String> alfabeto = new ArrayList<>();
                Collections.addAll(alfabeto, datosAlfabeto);
                alfabetos.add(alfabeto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alfabetos;
    }
    // ------------------------------------------ GETTERS ------------------------------------------
    public static PersistenceAlfabeto getInstance() {
        return instance;
    }
}
