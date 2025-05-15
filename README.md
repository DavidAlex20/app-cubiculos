# APPCUBICULOS

AppCubiculos es una aplicación para registrar pases de lista de los docentes a sus respectivos cubículos de estudio, también cuenta con la función de registrar pases de lista sobre eventos asignados por la facultad, revisión de pases de lista, etc. Es una aplicación en proceso de desarrollo, hasta ahora cuenta con un sistema de administrador con CRUDS de las tablas de la base de datos y las opciones de pase de lista del día para los usuarios.

## Tabla de contenido
- [Instalacion](#instalacion)
- [Tecnologias](#tecnologias)
- [Composicion del proyecto](#composicion-del-proyecto)
- [Ideas](#ideas)

## Instalacion

Para correr el proyecto de forma local se requiere:

- Java 17
- Maven v3.6.3+
- PostgreSql

El proceso de instalación se puede encontrar en la documentación oficial de [Spring](https://docs.spring.io/spring-boot/installing.html), dependiendo de tu sistema operativo. Además, también cuentan con un *IDE* llamado **SpringToolSuite** enfocado en trabajar con proyectos SpringBoot. A su vez, la instalación de [**PostgreSql**](https://www.postgresql.org/download/) puede ser vista en la documentación oficial.

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

Dentro de la carpeta ***main*** se encuentran las clases del *backend*:

- *Config* :: Estrictamente para archivos de configuración del proyecto, de momento solo está el archivo ***SecurityConfig.java*** que su funcionalidad es la configuración del **Spring Security**, utilizando una autentificación simple que requiere solo del usuario y contraseña. Como futuros cambios, quizá sería solo implementar WebTokens para mantener las sesiones abiertas hasta cierto tiempo.
- *Controllers* :: Aquí se encuentran todos los controladores de la aplicación, sirven tanto para la autentificación, vistas y respuestas API del *backend*. Dentro de esta hay otra carpeta llamada Admin, esta solo es para los controladores que funcionen solo si un administrador ha iniciado sesión. Además, cabe recalcar que las funciones API de los controladores solo se enfocan en devolver respuestas utilizando **ResponseEntity**, no se hace mucho manejo de datos o verificaciones de estos desde los controladores, sino de los *Servicios*.
- *Models* :: Tanto los modelos de las bases de datos como clases DTO para la accesibilidad y manejo de los datos en la aplicación. Los DTOs cuentan con clases tanto para tomar datos del *frontend* y mandarlos al *backend* como también clases para mostrar los datos en el *frontend* sin recibir errores.
- *Repositories* :: Se encuentran las interfaces de cada tabla de la base de datos, además de contar con las funciones básicas que ofrece **R2DBC**, también cuentan con funciones personalizables utilizando la anotación *@Query* para diseñar consultas directas a la base de datos. Cabe aclarar que estas consultas deben ser simples y no manejan estructuras complejas a diferencia de utilizar **DatabaseClient** (si se ve la necesidad de utilizarlo es posible agregar interfaces adicionales para extender las interfaces principales de los repositorios).
- *Services* :: Para finalizar, los servicios son los que hacen todo el manejo de datos, tanto analizarlos y verificarlos, además de que son las clases que hacen las llamadas a los repositorios y son los que devuelven los datos obtenidos a los controladores, de esta manera se mantiene un orden entre clases y paquetes.

Dentro de la carpeta ***resources*** se encuentran los archivos para el *frontend*, al ser mayormente un proyecto de *backend* se utilizó una base con **Bootstrap 5** llamada [NiceAdmin](https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/), la cual ofrece bastantes herramientas para la muestra de datos, íconos, gráficos, entre otras herramientas sencillas. El *frontend* se encuentra dentro de la carpeta ***resources/templates***, se utilizó **Thymeleaf** para poder hacer las vistas utilizando plantillas, similar a como funcionan otros *frameworks* como **Angular**; este *frontend* es más un diseño simple para tener una vista de la aplicación, a futuras actualizaciones podría ser más óptimo utilizar algún *framework* enfocado en este.

> [!NOTE]
> Para ver la funcionalidad del proyecto en el apartado del *frontend*, vease [el video en este enlace](https://www.youtube.com/watch?v=Yd4y3VdGZ1g).

### Base de datos

La siguiente imagen muestra cómo están divididas las tablas de la base de datos. Como se puede ver solo hay una llave foránea que es en la tabla de cubículos, la cual puede o no tener un usuario asignado.

<p align="left">
    <img src="https://github.com/user-attachments/assets/b166c2a2-35ce-4d5a-879c-bc02d52b882e" width="512"/>
</p>

Dentro del proyecto, en la carpeta *database* se encuentra el archivo ***tables.sql***, el cual cuenta con todas las consultas para agregar las tablas a una base de datos prehecha.

> [!NOTE]
> Los datos de conexión con PostgreSql se ubican en el archivo ***application.properties***.

## Ideas

De momento, el proyecto ya cuenta con un *CRUD* funcional para el administrador y el usuario puede comenzar, pausar y finalizar sus pases de lista en la página principal, pero aún faltan cosas por finalizar y por realizar.

1) Reportes semanales automáticos:

    Aunque ya existe un formulario para que el administrador realice los reportes tanto de la semana actual como de una semana en específico, se desea que estos reportes semanales se realicen de forma automática. Inicialmente se tenía la idea de utilizar una clase *@Schedule* para poder llamar a las funciones del servicio de reportes y así realizar de forma automática estos, el problema es que *@Schedule* es de tecnología bloqueante, la cual no se suele llevar bien con lo reactivo como **Spring Webflux**, por lo que sería necesario buscar otros medios de realizarlos.

2) Tablas adicionales:

    La base de datos cuenta con los datos necesario para llevar a cabo la función principal de la aplicación, sin embargo hay cosas adicionales que podrían servir en algún futuro, como una tabla de variables globales, ya sea para el mismo sistema o aplicación. Por ejemplo, una variable que el administrador podría asignar es la cantidad de horas que el maestro debe asistir por semana. También otras tablas por hacer pueden ser las del siguiente punto.

3) Sistema de notificaciones:

    Existen varias cosas que se podrían registrar para mejorar la aplicación, y una importante para mantener al tanto al maestro es poder informarle cualquier cosa, desde eventos o asambleas por venir, sus horas de entrada, las faltas que tiene, etc.

    - Cada día se podría hacer un registro del día anterior sobre las horas realizadas del maestro o si hubo algún pase de lista que no finalizó manualmente, estos registros se podrían guardar en una tabla de faltas, de esta manera al día siguiente se le notificaría al maestro que debe detallar alguna justificación de la falta.
    - Los reportes además de semanales podrían ser mensuales e incluso semestrales, por lo que tener una opción de notificarle al maestro sobre estos reportes y enviarle algún resumen por correo sería lo ideal. De esta manera no solo resumimos la información de la tabla para un usuario, sino que también lo mantenemos al tanto de su dedicación.

> [!TIP]
> Durante el proceso de actualización del proyecto puede llegar a salir una que otra idea, siempre será conveniente anotarlas para poder investigar con anticipación sobre la manera más eficiente de realizarla.


