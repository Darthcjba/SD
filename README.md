Laboratorio de WebServices

**Elementos a utilizar**
* JavaEE, JDK 1.8
* IDE Eclipse, elegir la opción Eclipse for Java EE
* IDE Eclipse, instalar Jboss Tools (Menú: Help -> Eclipse MarketPlace. Buscar "Jboss Tools"). Este paso solo es necesario para crear un proyecto nuevo.
* Instalar Jboss tools 
* Instalar SOAP-UI
* Instalar PostMan para Google Chrome
* Instalar el motor de base de datos Postgresql

**Descargar el proyecto**

Via SSH:
    
    git clone git@gitlab.com:fmancia/sd.git
    
Via HTTPS:
    
    git clone https://gitlab.com/fmancia/sd.git
    
Via transferencia de archivos por parte del Profesor

    
**Importar proyecto**
 * Abrir el IDE Eclipse
 * Menú File -> Import -> Existing Maven Project
 * Dentro de los archivos descargados, especificar la carpeta "sd\lab-ws\servidor\personas"
 * Finalizar
 * El IDE comenzará a importar las librerías de Maven
 
**Base de datos**
 * Crear una base de datos.
 * Configurar los datos de configuración y acceso en la clase "Bd.java".

```sql
CREATE TABLE persona
(
    cedula integer NOT NULL,
    nombre character varying(1000),
    apellido character varying(1000),
    CONSTRAINT pk_cedula PRIMARY KEY (cedula)
)
WITH (
   OIDS=FALSE
 );
```

**Verificación del código fuente**
 * Verificación de la clase JaxRsActivator.java
 * Verificación de la clase Bd.java
 * Verificación de la clase Persona.java
 * Verificación de la clase PersonaDAO.java
 * Verificación de la clase PersonaService.java
 * Verificación de la clase PersonaRESTService.java

**Deployar en Servidor**
 * Desde el IDE Eclipse, configurar el servidor de aplicaciones Wildfly10 (Verificar la guía de clase anterior sobre el laboratorio de JavaRMI)
 * Deploy del proyecto "personas" en el servidor Wildfly


**Verificación con herramientas: POSTMAN / SOAP-UI / Browser**
 * GET http://localhost:8080/personas/rest/personas
 * GET http://localhost:8080/personas/rest/personas/3298639
 * GET http://localhost:8080/personas/rest/personas/cedula?cedula=160160
 * POST http://localhost:8080/personas/rest/personas/
 * DELETE http://localhost:8080/personas/rest/personas/150150

 

**Tarea**
 * Modificar el ejemplo del profesor para incluir más funcionalidades.
 * Se deben crear servicios RESTful necesarios para la administración de "asignaturas"
 * Las asignaturas están asociadas a una persona.
 * Pueden estar en memoria o en base de datos.
 * Los servicios deben poder:
    * crear/modificar/listar/borrar asignaturas.
    * asociar y desasociar una asignatura a una persona.
    * listar todas las asignaturas de una persona.
    * listar todos los alumnos de una asignatura.
 * Se debe entregar de la siguiente forma:
     * Enviar un email al profesor:  fernandomancia@gmail.com 
     * El email debe contener el link del repositorio personal del alumn@
     * El email debe contener imagenes en PNG o JPG de los prints de pantalla de las pruebas a los restful solicitados.
     * En el link del repositorio gitlab del alumn@ debe estar el código fuente de la tarea solicitada.
     * Se debe entregar a más tardar el Lunes 07/05/2018