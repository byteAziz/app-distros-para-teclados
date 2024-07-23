# Aplicación de distribuciones óptimas para teclados

## Descripción

Este es un proyecto de una aplicación que permite calcular la distribución óptima (o una muy parecida en muy poco tiempo de ejecución) dado unos textos de un lenguaje cualquiera. Se busca que, tras analizar el lenguaje completo, la distribución de salida minimice la distancia media recorrida por el dedo al escribir un texto lógico. **Para obtener los detalles sobre el diseño, los casos de uso, la implementación de las clases y la lógica detrás de los algoritmos, consulta el archivo [documentación.pdf](documentación.pdf)**.

## Funcionalidades

- Crear y gestionar teclados personalizados
- Crear y gestionar lenguajes personalizados
- Modificar lenguajes añadiendo o eliminando textos
- Calcular la distribución óptima de un teclado
- Definir el alfabeto completo en caso que alguna letra no aparezca en ningún texto puntualmente
- Intercambiar posiciones de teclas en un teclado
- Buscar y ordenar teclados
- Visualizar detalles de los teclados

## Requisitos del Sistema

- Java JDK 11 o superior

## Instalación

1. Clona el repositorio:
    ```bash
    git clone https://github.com/byteAziz/app-distros-para-teclados
    ```
2. Entra en el directorio del repositorio:
   cd directorio-del-repositorio
3. Ejecuta el make dependiendo de tu sistema operativo:
    - Windows:
        ```bash
        Make.bat
        ```
    - Linux/Mac:
        ```bash
        Make.sh
        ```
4. Ejecuta la aplicación:
    ```bash
    java -jar EXE/tecladosprop-1.0.jar
    ```
5. **(Opcional)** Genera la documentación de la implementación usando javadoc:
    - Windows:
        ```bash
        MakeJavadoc.bat
        ```
    - Linux/Mac:
        ```bash
        MakeJavadoc.sh
        ```
    Accede a la documentación abriendo el archivo index.html desde el siguiente directorio:
    
   ```bash
   cd DOCS/javadoc
   ```

## Uso
Véase el [doc-manual-usuario.pdf](doc-manual-usuario.pdf).

## Tabla de miembros

| Nombre miembro | Usuario GitLab |
| -------------- | -------------- |
| Tahir Muhammad Aziz | - |
| Guillem Angulo Hidalgo | - |
| Joan Martínez | - |

Dado que se ha migrado de *Gitlab FIB*, no se pueden ver los commits originales ni tiene sentido poner los usuarios privados en la tabla.
