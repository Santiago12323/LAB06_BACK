# LAB4_BACK
# Backend - API REST para Gestor de Tareas

Este proyecto es el backend de una aplicación web para gestionar tareas. Proporciona una API RESTful que permite a los clientes (frontend) realizar operaciones de agregar, visualizar, actualizar y eliminar tareas, así como manejar la persistencia de los datos.

## Características

- **Agregar Tareas**: Permitee crear nuevas tareas con nombre y descripción.
- **Visualizar Tareas**: Permite obtener una lista de todas las tareas existentes.
- **Actualizar Tareas**: Permite modificar los detalles de una tarea específica.
- **Eliminar Tareas**: Permite para eliminar una tarea existente.
- **Cambio de Estado**: Permite para cambiar el estado de una tarea (completada o no completada).

## Tecnologías Utilizadas

- **Spring Boot**: Para crear la API REST y gestionar las operaciones del servidor.
- **Java**: Lenguaje de programación utilizado para desarrollar la lógica de negocio.
- **MongoDB**: Base de datos no relacional.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.

## Requisitos Previos

Para ejecutar este proyecto localmente, asegúrate de tener instalado:

- **JDK 11** o superior: Para compilar y ejecutar la aplicación.
- **Maven**: Para gestionar dependencias y construir el proyecto.

## Estructura del Proyecto

- **src/main/java**: Contiene el código fuente de la aplicación.
- **src/main/resources**: Contiene los archivos de configuración y el archivo de propiedades de la aplicación.
- **src/test/java**: Contiene las pruebas unitarias para la lógica del backend.
- **pom.xml**: Archivo de configuración de Maven que contiene las dependencias del proyecto.

## Uso

1. **Configuración**: Configura la base de datos y otros parámetros en el archivo `application.properties`.
2. **Ejecutar la Aplicación**: Utiliza el comando `mvn spring-boot:run` en la terminal para iniciar la aplicación.
3. **Endpoints**:
   - **POST /tareas**: Agregar una nueva tarea.
   - **GET /tareas**: Obtener la lista de tareas.
   - **PUT /tareas/{id}**: Actualizar una tarea existente.
   - **DELETE /tareas/{id}**: Eliminar una tarea.
   - **PATCH /tareas/{id}/estado**: Cambiar el estado de una tarea.

## Conexión con el Frontend

El backend está diseñado para interactuar con el frontend desarrollado en HTML, CSS y JavaScript. Asegúrate de que el frontend esté configurado para realizar peticiones a la API en `http://localhost:8080` para que la conexión funcione correctamente.
