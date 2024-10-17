# UI Noticracia

## Tabla de Contenidos
- [Introducción](#introducción)
- [Tecnologías necesarias](#tecnologías-necesarias)
- [Dependencias](#dependencias)
- [Instalación](#instalación)

## Introducción

**UI Noticracia** es un sistema diseñado para generar nubes de palabras basadas en los nombres de candidatos políticos, permitiendo el análisis y la visualización de la presencia de dichos candidatos en medios periodísticos. La aplicación busca proporcionar una herramienta visual intuitiva para comparar la relevancia y el impacto de diferentes candidatos en un entorno político específico.

**Nuevo**: se incorpora la posibilidad de elegir la extensión de dónde generar la nube de palabras.

## Tecnologías necesarias

- **Java 22**  
- **Java Swing**

## Dependencias

- **Noticracia-Core**: contiene el núcleo del proyecto. Es necesario importarlo para el correcto funcionamiento del sistema.  
  - [Repositorio Noticracia-Core](https://github.com/juanmanuellosada/noticracia-core/tree/it-1)

- **Noticracia-Clarinete**: contiene la extensión del proyecto (Clarinete). Incluye módulos adicionales para el procesamiento de datos específicos de ciertos medios de comunicación.
  - [Repositorio Noticracia-Clarinete](https://github.com/donatofranco/noticracia-extension-1)
 
- **Nuevo**: se añaden dos extensiones al proyecto.
  - **Noticracia-Croniquita**: retorna información de la web en real time.
    - [Repositorio Croniquita RSS](https://github.com/donatofranco/noticracia-extension-2)
  - **Noticracia-Pajarito**: retorna información de un servidor local.
    - [Repositorio Pajarito](https://github.com/donatofranco/noticracia-extension-3)

- **Noticracia-UI**: contiene todo lo relacionado con la interfaz gráfica de usuario (GUI) para interactuar con el sistema de análisis y visualización.

## Instalación

Para instalar el proyecto en un entorno local, seguí los siguientes pasos:

1. **Clonar los repositorios**:  
   Clona los repositorios de **Noticracia-Core**, **Noticracia-Clarinete** y **Noticracia-UI**, de la rama It-1, en tu máquina local:
   ```bash
   git clone https://github.com/juanmanuellosada/noticracia-core.git
   git clone https://github.com/donatofranco/noticracia-extension-1
   git clone https://github.com/donatofranco/noticracia-ui
   ```

   **Nuevo** Clona los repositorios de **Noticracia-Croniquita** y **Noticracia-Pajarito**
   ```bash
   git clone https://github.com/donatofranco/noticracia-extension-2.git
   git clone https://github.com/donatofranco/noticracia-extension-3.git
   ```

3. **Importar los proyectos**:  
   Utiliza un IDE compatible con Java, como IntelliJ IDEA o Eclipse, para importar los cinco repositorios como proyectos individuales.


4. **Configurar las dependencias**:  
   Aseguráte de que Noticracia-Core y sus extensiones estén correctamente vinculados como dependencias dentro de Noticracia-UI. Esto permitirá que la interfaz gráfica pueda interactuar con los módulos correspondientes.

5. **Añadir noticias sobre candidatos políticos en Clarinete**:  
   Las noticias deben estar ubicadas en el directorio **home** del usuario, dependiendo del sistema operativo. Los archivos que se encuentran en `src/main/resources/` del proyecto [noticracia-extension-1](https://github.com/donatofranco/noticracia-extension-1/tree/main/src/main/resources) se dejan a modo de ejemplo y deben moverse al directorio principal del usuario. Por ejemplo, en sistemas Windows, se deben ubicar en `C:\Users\TuUsuario`, y en sistemas Unix (Linux/Mac), en `/home/TuUsuario`. Un archivo de ejemplo sería `Javier Milei.txt` dentro del directorio del usuario.
6. **Añadir noticias sobre candidatos políticos en Croniquita**:
   En este caso, las noticias se generarán con la información del RSS de Cronica en el momento que se solicita la información. No se requiere de ningun paso previo.  
7. **Añadir noticias sobre candidatos políticos en Pajarito**:
   Las noticias se deben enviar a la URL indicada por consola una vez solicitada la nube de palabras. Se deberá enviar por método POST e incluír un body con un JSON que incluya un array de elementos con un "link" y una "information".
   Ejemplo:
   ```json
     [
      {
          "link": "https://example.com",
          "information": "Esta es una noticia para Javier Milei"
      },
      {
          "link": "https://example2.com",
          "information": "Esta es otra noticia que se puede utilizar para testear la nube de palabras para el candidato Javier Milei"
      }
    ]
   ```
9. **Compilar y ejecutar**:  
   Dentro de Noticracia-UI, busca el archivo principal MainUI.java o App.java y ejecutálo. Este archivo contiene el punto de entrada para la aplicación y cargará la interfaz gráfica.  
   ![image](https://github.com/user-attachments/assets/95089c2b-3456-4086-932d-eda7478ef92f)

   
