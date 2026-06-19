# Kata 6 - Servicio Web para Generación de Histogramas

## Descripción

Esta práctica amplía la kata anterior para convertir la aplicación en un servicio web. El nuevo servicio permite solicitar, mediante parámetros HTTP, la generación de histogramas específicos y devolverlos en formato JSON.

Se ha implementado el patrón de diseño Adapter para desacoplar la arquitectura interna de la aplicación del formato y protocolo de comunicación del servicio web, manteniendo una separación clara entre las distintas capas del sistema.

---

## Estructura del proyecto

```
src/main/java
├── app
│   └── WebService.java
├── io
│   ├── CSVSongParser.java
│   ├── CSVSongReader.java
│   ├── DatabaseRecorder.java
│   ├── DatabaseStore.java
│   ├── Recorder.java
│   ├── SongParser.java
│   └── Store.java
├── model
│   ├── HistogramPOJO.java
│   └── Song.java
├── tasks
│   ├── HistogramAdapter.java
│   └── HistogramBuilder.java
├── view
│   └── MainFrame.java
└── viewmodel
    └── Histogram.java
```

---

## Objetivos de la práctica

- Convertir la aplicación en un servicio web.
- Permitir la generación de histogramas mediante peticiones HTTP.
- Devolver los resultados en formato JSON.
- Implementar el patrón de diseño Adapter.
- Desacoplar la lógica interna del formato de comunicación externo.
- Mantener una arquitectura modular y organizada.

---

## Clases del proyecto

### WebService.java
Clase encargada de exponer el servicio web y atender las peticiones HTTP.

### Song.java
Clase inmutable que representa una canción y sus atributos.

### HistogramPOJO.java
Clase utilizada para representar el histograma en formato adecuado para su serialización en JSON.

### SongReader.java
Clase encargada de leer el dataset desde una fuente externa.

### CSVSongReader.java
Clase encargada de obtener y leer los datos en formato CSV.

### SongParser.java
Interfaz que define cómo convertir una línea del archivo en un objeto `Song`.

### CSVSongParser.java
Clase encargada de transformar cada línea del CSV en un objeto `Song`.

### Recorder.java
Interfaz que define el almacenamiento de los datos.

### DatabaseRecorder.java
Clase encargada de guardar los registros en la base de datos SQLite.

### Store.java
Interfaz encargada del acceso a los datos almacenados.

### DatabaseStore.java
Clase responsable de recuperar la información almacenada en SQLite.

### HistogramBuilder.java
Clase encargada de generar histogramas a partir de los datos almacenados.

### HistogramAdapter.java
Clase que implementa el patrón Adapter para adaptar la estructura interna del histograma al formato JSON utilizado por el servicio web.

### Histogram.java
Clase view model que almacena los valores y las frecuencias observadas en el histograma.

### MainFrame.java
Clase responsable de la interfaz gráfica y de la representación visual del histograma.

---

## Uso de Git

Se han realizado commits durante el desarrollo para registrar los cambios del proyecto y facilitar el seguimiento de la práctica.

---

## Nota

Esta práctica forma parte del aprendizaje sobre servicios web y patrones de diseño. Se ha implementado un servicio capaz de generar histogramas bajo demanda y devolverlos en formato JSON, utilizando el patrón Adapter para mantener desacopladas la lógica interna y la capa de comunicación externa.
