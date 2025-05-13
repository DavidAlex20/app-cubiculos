# APPCUBICULOS

AppCubiculos es una aplicación para registrar pases de lista de los docentes a sus respectivos cubículos de estudio, también cuenta con la función de registrar pases de lista sobre eventos asignados por la facultad, revisión de pases de lista, etc. Es una aplicación en proceso de desarrollo, hasta ahora cuenta con un sistema de administrador con CRUDS de las tablas de la base de datos y las opciones de pase de lista del día para los usuarios.

## Tabla de contenido
- [Tecnologias](#tecnologias)
- [Composicion del proyecto](#composicion-del-proyecto)

## Tecnologias

La tecnología principal utilizada es **Springboot Webflux**, la mayor parte de la aplicación está enfocada en el *backend*, incluidas las llamadas a las vistas utilizando **Thymeleaf**, es posible en un futuro hacer de este proyecto solo *backend* con *Rest Api*. Además, al ser tecnología reactiva, se decidió utilizar **PostgreSql** como base de datos, ya que utilizando la librería de **R2DBC** para Spring es posible poder hacer uso de las clases *Mono* y *Flux*, las cuales tienen propiedades asíncronas que van de la mano con **Spring Webflux**.

> [!NOTE]
> El resto de librerías incluídas pueden ser vistas en el archivo ***pom.xml***.

## Composicion del proyecto

### Recursos

Las carpetas del proyecto están divididas de la siguiente manera:

<p align="left">
    <img src="https://github.com/user-attachments/assets/728b995c-861c-41c2-88c5-15f86db07d6d" width="256"/>
</p>

- *Config* :: Estrictamente para archivos de configuración del proyecto, de momento solo está el archivo ***SecurityConfig.java*** que su funcionalidad es la configuración del **Spring Security**, utilizando una autentificación simple que requiere solo del usuario y contraseña. Como futuros cambios, quizá sería solo implementar WebTokens para mantener las sesiones abiertas hasta cierto tiempo.
- *Controllers* :: Aquí se encuentran todos los controladores de la aplicación, sirven tanto para la autentificación, vistas y respuestas API del *backend*. Dentro de esta hay otra carpeta llamada Admin, esta solo es para los controladores que funcionen solo si un administrador ha iniciado sesión. Además, cabe recalcar que las funciones API de los controladores solo se enfocan en devolver respuestas utilizando **ResponseEntity**, no se hace mucho manejo de datos o verificaciones de estos desde los controladores, sino de los *Servicios*.
- *Models* :: Tanto los modelos de las bases de datos como clases DTO para la accesibilidad y manejo de los datos en la aplicación. Los DTOs cuentan con clases tanto para tomar datos del *frontend* y mandarlos al *backend* como también clases para mostrar los datos en el *frontend* sin recibir errores.
- *Repositories* :: Se encuentran las interfaces de cada tabla de la base de datos, además de contar con las funciones básicas que ofrece **R2DBC**, también cuentan con funciones personalizables utilizando la anotación *@Query* para diseñar consultas directas a la base de datos. Cabe aclarar que estas consultas deben ser simples y no manejan estructuras complejas a diferencia de utilizar **DatabaseClient** (si se ve la necesidad de utilizarlo es posible agregar interfaces adicionales para extender las interfaces principales de los repositorios).
- *Services* :: Para finalizar, los servicios son los que hacen todo el manejo de datos, tanto analizarlos y verificarlos, además de que son las clases que hacen las llamadas a los repositorios y son los que devuelven los datos obtenidos a los controladores, de esta manera se mantiene un orden entre clases y paquetes.

### Base de datos



<p align="left">
    <img src="https://github.com/user-attachments/assets/b166c2a2-35ce-4d5a-879c-bc02d52b882e" width="512"/>
</p>
