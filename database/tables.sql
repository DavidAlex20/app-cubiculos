--  TABLA DE USUARIOS
--      Esta tabla cuenta con toda la info de los usuarios. 
--      Se podría quizá separar en algún futuro los datos de usuario (username, password, role, activo) de la información del maestro (nombres, apellidos, numempleado, status, email).
--      Los datos de la columna 'status' son los siguientes tres: FITEC, FORANEO, VISITANTE.
CREATE TABLE usuarios (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    role character varying(30) DEFAULT 'USER'::character varying NOT NULL,
    nombres character varying(100) NOT NULL,
    apellidos character varying(100) NOT NULL,
    numempleado character varying(100) NOT NULL,
    status character varying(100) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    email character varying(100) NOT NULL
);
ALTER TABLE ONLY usuarios ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);
-- Usuario administrador
--  La contraseña es: '..admin..'
--  Está encriptada con BCrypt de Java, la unica manera de cambiar la contraseña (y no desde la base de datos) es actualizando el usuario con una nueva contraseña.
INSERT INTO usuarios(username, password, role, nombres, apellidos, numempleado, status, activo, email)
VALUES ('admin', '$2a$10$/lTuPPPIB8KqDbEM5AsTXeJqdFxzZ1vw4AnI4RSIOEaX1JeHkzS5K', 'ADMIN', 'Admin', 'Super', 'E-000', 'FITEC', true, 'adminsuper@mail.com');

--  TABLA DE CUBICULOS
--      Esta es la unica tabla que cuenta con llave foranea, ya que a un cubiculo solo se le puede asignar un maestro o puede no tener alguno asignado.
CREATE TABLE cubiculos (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    numero integer NOT NULL,
    edificio character varying(100) NOT NULL,
    disponible boolean DEFAULT true NOT NULL,
    asignacion uuid
);
ALTER TABLE ONLY cubiculos ADD CONSTRAINT cubiculos_pkey PRIMARY KEY (id);
ALTER TABLE ONLY cubiculos ADD CONSTRAINT fk_parent FOREIGN KEY (asignacion) REFERENCES usuarios(id) ON DELETE SET NULL;

--  TABLA DE EVENTOS
--      Registra eventos a los que los maestros deben asistir.
CREATE TABLE eventos (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    titulo character varying(100) NOT NULL,
    lugar character varying(100) NOT NULL,
    inicio time without time zone,
    fin time without time zone,
    fecha date
);
ALTER TABLE ONLY eventos ADD CONSTRAINT eventos_pkey PRIMARY KEY (id);

--  TABLA DE PASES DE LISTA
--      Esta tabla registra los pase de lista, tanto la asistencia al cubiculo como a un evento.
--      Las columnas de evento y cubiculo no pueden estar llenas ni vacías a la vez.
--      Solo cuando es un pase de lista de cubiculo, las columnas de pausa pueden ser llenadas.
CREATE TABLE paselista (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    usuario uuid NOT NULL,
    evento uuid,
    cubiculo uuid,
    fecha date DEFAULT CURRENT_DATE NOT NULL,
    inicio time without time zone DEFAULT CURRENT_TIME,
    fin time without time zone,
    pausainicio time without time zone,
    pausafin time without time zone
);
ALTER TABLE ONLY paselista ADD CONSTRAINT paselista_pkey PRIMARY KEY (id);

--  TABLA DE REPORTES
--      Esta tabla suma la cantidad de horas cumplidas por el maestro, tanto las de eventos y cubiculos.
--      Se registran tomando los días de domingo a sabado.
CREATE TABLE reportes (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    usuario uuid NOT NULL,
    semanainicio date DEFAULT (date_trunc('week'::text, (CURRENT_DATE)::timestamp with time zone) - '1 day'::interval) NOT NULL,
    semanafin date DEFAULT (date_trunc('week'::text, (CURRENT_DATE)::timestamp with time zone) + '5 days'::interval) NOT NULL,
    puntuales interval DEFAULT '00:00:00'::interval NOT NULL
);
ALTER TABLE ONLY reportes ADD CONSTRAINT reportes_pkey PRIMARY KEY (id);