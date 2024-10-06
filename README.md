# UI Noticracia

## Tabla de Contenidos
- [Introducción](#introducción)
- [Tecnologías necesarias](#tecnologías-necesarias)
- [Dependencias](#dependencias)
- [Instalación](#instalación)

## Introducción

**UI Noticracia** es un sistema diseñado para generar nubes de palabras basadas en los nombres de candidatos políticos, permitiendo el análisis y la visualización de la presencia de dichos candidatos en medios periodísticos. La aplicación busca proporcionar una herramienta visual intuitiva para comparar la relevancia y el impacto de diferentes candidatos en un entorno político específico.

## Tecnologías

- **Java 22**  
- **Java Swing**

## Dependencias

- **Noticracia-Core**: contiene el núcleo del proyecto. Es necesario importarlo para el correcto funcionamiento del sistema.  
  - [Repositorio Noticracia-Core](https://github.com/juanmanuellosada/noticracia-core.git)

- **Noticracia-Ext**: contiene la extensión del proyecto (Clarinete). Incluye módulos adicionales para el procesamiento de datos específicos de ciertos medios de comunicación.  
  - [Repositorio Noticracia-Ext](https://github.com/donatofranco/noticracia-extension-1)

- **Noticracia-UI**: contiene todo lo relacionado con la interfaz gráfica de usuario (GUI) para interactuar con el sistema de análisis y visualización.

## Instalación

Para instalar el proyecto en un entorno local, seguí los siguientes pasos:

1. **Clonar los repositorios**:  
   Clona los repositorios de **Noticracia-Core**, **Noticracia-Ext** y **Noticracia-UI**, de la rama It-0, en tu máquina local:
   ```bash
   git clone https://github.com/juanmanuellosada/noticracia-core.git
   git clone https://github.com/donatofranco/noticracia-extension-1
   git clone https://github.com/donatofranco/noticracia-ui
   ```

2. **Importar los proyectos**:  
   Utiliza un IDE compatible con Java, como IntelliJ IDEA o Eclipse, para importar los tres repositorios como proyectos individuales.


3. **Configurar las dependencias**:  
   Aseguráte de que Noticracia-Core y Noticracia-Ext estén correctamente vinculados como dependencias dentro de Noticracia-UI. Esto permitirá que la interfaz gráfica pueda interactuar con los módulos correspondientes.

4. **Añadir noticias sobre candidatos políticos**:  
   Las noticias deben estar ubicadas en el directorio **home** del usuario, dependiendo del sistema operativo. Los archivos que se encuentran en `src/main/resources/` del proyecto [noticracia-extension-1](https://github.com/donatofranco/noticracia-extension-1/tree/main/src/main/resources) se dejan a modo de ejemplo y deben moverse al directorio principal del usuario. Por ejemplo, en sistemas Windows, se deben ubicar en `C:\Users\TuUsuario`, y en sistemas Unix (Linux/Mac), en `/home/TuUsuario`. Un archivo de ejemplo sería `Javier Milei.txt` dentro del directorio del usuario.

5. **Compilar y ejecutar**:  
   Dentro de Noticracia-UI, busca el archivo principal MainUI.java o App.java y ejecutálo. Este archivo contiene el punto de entrada para la aplicación y cargará la interfaz gráfica.  
   ![IMG](https://i.ibb.co/YdwspJS/4904525474764533105.jpg)
   
