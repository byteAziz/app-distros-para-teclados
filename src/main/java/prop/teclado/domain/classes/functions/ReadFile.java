package prop.teclado.domain.classes.functions;

import prop.teclado.domain.classes.exceptions.FileNotFound;
import prop.teclado.domain.classes.exceptions.NoTxt;

import java.io.*;

/**
 * Clase que lee un archivo (solamente .txt) y devuelve su contenido en un string
 * Author: Joan Martínez Soria
 */
public class ReadFile {
    public static String readFile(String path) throws FileNotFound, NoTxt {
        CheckTxt.checkTxt(path);
        try {
            // Intenta abrir el archivo
            File archivo = new File(path);
            // Utiliza try-with-resources para asegurarse de cerrar el recurso correctamente
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {
                // Lee el contenido del archivo usando StringBuilder
                StringBuilder fileContent = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isBlank()) { // Agrega solamente si la linea no esta en blanco
                        fileContent.append(line).append(" "); // Agrega un espacio después de cada línea
                    }
                }
                // Se añaden los simbolos del string obtenido al alfabeto sin repetir
                return fileContent.toString();
            }
        } catch (IOException e) {
            throw new FileNotFound();
        }
    }
}
