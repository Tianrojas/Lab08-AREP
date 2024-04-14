# Taller 8 - Laboratorio experimental

## Descripción

Este proyecto corresponde al Taller 7 del curso de Desarrollo de Aplicaciones Web Seguras. Se trata de un prototipo de aplicación web que incluye funcionalidades de autenticación de usuarios, creación de publicaciones y gestión de flujos de contenido.

## Arquitectura del Prototipo

El prototipo está desarrollado utilizando el framework Quarkus para la creación de una API RESTful. Se emplea MongoDB como base de datos para almacenar la información de usuarios, publicaciones y flujos de contenido. Además, se utiliza JSON Web Tokens (JWT) para la autenticación de usuarios.

## Estructura del Repositorio

El repositorio está organizado de la siguiente manera:

- **`src/main/java/`**: Contiene el código fuente de la aplicación.
  - **`users/`**: Paquete que incluye las clases relacionadas con la gestión de usuarios.
  - **`posts/`**: Paquete que contiene las clases relacionadas con las publicaciones.
  - **`streams/`**: Paquete que incluye las clases relacionadas con la gestión de flujos de contenido.
- **`src/main/resources/`**: Contiene archivos de recursos, como el archivo de configuración de MongoDB.
- **`pom.xml`**: Archivo de configuración de Maven que define las dependencias y configuraciones del proyecto.

## Componentes Principales

Los componentes principales del prototipo son:

- **`UserControler`**: Controlador que maneja las solicitudes relacionadas con la autenticación y creación de usuarios.
- **`PostController`**: Controlador encargado de las operaciones relacionadas con las publicaciones.
- **`StreamController`**: Controlador que gestiona las operaciones relacionadas con los flujos de contenido.
- **`UserService`**: Servicio que proporciona métodos para la gestión de usuarios.
- **`PostService`**: Servicio que maneja las operaciones relacionadas con las publicaciones.
- **`StreamService`**: Servicio encargado de las operaciones relacionadas con los flujos de contenido.
- **`UserRepository`**: Repositorio que realiza operaciones CRUD en la base de datos para la entidad de usuario.

## Flujo de Trabajo

El flujo de trabajo del prototipo es el siguiente:

1. El cliente realiza una solicitud HTTP al servidor, como la creación de un usuario o la autenticación.
2. El controlador correspondiente maneja la solicitud y llama al servicio adecuado para realizar la operación.
3. El servicio interactúa con el repositorio para acceder a los datos en la base de datos.
4. El repositorio realiza las operaciones de lectura o escritura en la base de datos y devuelve los resultados al servicio.
5. El servicio procesa los datos y devuelve una respuesta al controlador.
6. El controlador devuelve la respuesta al cliente.

## Ejemplo de Uso

A continuación, se presenta un ejemplo básico de cómo utilizar la API:

1. Crear un usuario:

   ```http
   POST /users/user HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json

   {
       "username": "ejemplo",
       "email": "ejemplo@example.com",
       "password": "contraseña",
       "role": "User"
   }
   ```

2. Autenticar un usuario:

   ```http
   POST /users/auth HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json

   {
       "email": "ejemplo@example.com",
       "password": "contraseña"
   }
   ```

3. Crear una publicación:

   ```http
   POST /users/post HTTP/1.1
   Host: localhost:8080
   Authorization: Bearer <token_de_autenticación>
   Content-Type: application/json

   {
       "content": "Contenido de la publicación",
       "authorID": "<ID_del_autor>"
   }
   ```

4. Obtener publicaciones por autor:

   ```http
   GET /users/postsByAuthor?authorID=<ID_del_autor>&page=1&pageSize=10 HTTP/1.1
   Host: localhost:8080
   Authorization: Bearer <token_de_autenticación>
   ```

Este es solo un ejemplo básico de uso de la API. Se pueden realizar otras operaciones y solicitudes según las necesidades del cliente.
