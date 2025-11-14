📦 Online Store — Proyecto 3 (JDBC & MySQL)

Este proyecto representa la evolución de un sistema de gestión de pedidos en Java, transformado para utilizar una base de datos relacional (MySQL) como su capa de persistencia, manteniendo los principios de diseño Modelo-Vista-Controlador (MVC) y Data Access Object (DAO).

Tecnologías Clave:

Lenguaje: Java (JDK 17+)

Base de Datos: MySQL

Conexión: JDBC (Java Database Connectivity)

Driver: MySQL Connector/J

Patrones de Diseño: MVC, DAO, Factory


Estructura del Proyecto:

El proyecto sigue una arquitectura limpia basada en patrones de diseño:

src/app: Contiene la clase Main y el punto de entrada.

src/controller: Lógica de negocio y coordinación.

src/view: Interfaz de usuario en consola.

src/model: Objetos de negocio.

src/dao: Implementaciones del patrón DAO y la Factory para el acceso a la base de datos.

src/util: Clases de utilidad, incluyendo la gestión de la conexión a la DB (Conexion.java).

lib/: Contiene el driver JDBC (mysql-connector-j-9.5.0.jar).


Versión actual (repositorios en memoria)
Clonar el repositorio:
git clone https://github.com/arims98/POOwer-Coders.git
cd Poower Coders

