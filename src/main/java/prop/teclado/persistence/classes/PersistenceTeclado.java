package prop.teclado.persistence.classes;


import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de la persistencia de los teclados
 * Author: Joan Martínez Soria
 */
public class PersistenceTeclado {
    private final String FILE; //direccion de los datos de la persistencia
    //inizialización de la instancia "aeger" de la clase para hacer el Singleton "thread safe"
    private static PersistenceTeclado instance = new PersistenceTeclado();
    public PersistenceTeclado() {
        String filePath = System.getProperty("user.dir") + "/resources/teclados.prop";

        //mira si el fichero existe, si no, lo crea
        if (doesFileExist(filePath)) {
            FILE = filePath;
        } else {
            FILE = copyResourceToCustomLocation("teclados.prop", filePath);
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

    //se guardan los datos en un fichero que nosotros tenemos que definir (Ej: lenguajes.prop) (OPERACION DE ESCRITURA)
    public void guardarTeclado(List<String> datosTeclado, List<String> datosLenguaje) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true));
            boolean first = true;
            for (String dato : datosTeclado) {
                if (first) {
                    first = false;
                    bw.write(dato);
                } else {
                    bw.write("%/%/%");
                    bw.write(dato);
                }
            }
            bw.write("/&/&/&/");
            first = true;
            for (String dato : datosLenguaje) {
                if (first) {
                    first = false;
                    bw.write(dato);
                } else {
                    bw.write("%/%/%");
                    bw.write(dato);
                }
            }
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //se obtienen los datos de un fichero que nosotros tenemos que definir (Ej: lenguajes.prop) y se devuelven en una lista
    //Los datos del lenguaje vienen de la sigiente manera (NombreTeclado, fechaCreacion, FechaModificacion, ...)
    public List<List<String>> obtenerTeclados() {
        List<List<String>> teclados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] divisionLenguajeTeclado = line.split("/&/&/&/");
                for (String division : divisionLenguajeTeclado) {
                    String[] datosTeclado = division.split("%/%/%");
                    List<String> teclado = new ArrayList<>();
                    Collections.addAll(teclado, datosTeclado);
                    teclados.add(teclado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teclados;
    }

    //se elimina un teclado de la persistencia
    public void eliminarTeclado(String tecladoeRemove) {
        try {
            BufferedReader rd = new BufferedReader(new FileReader(FILE));
            StringBuilder newContent = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                // Verifica si la línea actual es la que se debe eliminar
                if (!line.equals(tecladoeRemove)) {
                    newContent.append(line).append(System.lineSeparator());
                }
            }
            rd.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, false));
            // Sobreescribe el contenido del fichero con el nuevo contenido
            bw.write(newContent.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //funcion que modifica el texto de un lenguaje en la persistencia
    //puede ser tanto eliminar como añadir
    public void modificarTextoLenguaje(String antiguoTexto, String nuevoTexto) {
        try {
            BufferedReader rd = new BufferedReader(new FileReader(FILE));
            StringBuilder newContent = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                // Verifica si la línea actual es la que se debe eliminar
                if (line.equals(antiguoTexto)) {
                    newContent.append(nuevoTexto).append(System.lineSeparator());
                } else
                    newContent.append(line).append(System.lineSeparator());
            }
            rd.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, false));
            // Sobreescribe el contenido del fichero con el nuevo contenido
            bw.write(newContent.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ------------------------------------------ GETTERS ------------------------------------------
    public static PersistenceTeclado getInstance() {
        return instance;
    }
}
