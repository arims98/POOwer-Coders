📦 Sistema de Gestión de Pedidos - Tienda Online (JDBC & MySQL)
Este proyecto representa la evolución de un sistema de gestión de pedidos en Java, transformado para utilizar una base de datos relacional (MySQL) como su capa de persistencia, manteniendo los principios de diseño Modelo-Vista-Controlador (MVC) y Data Access Object (DAO).

Requisitos y Tecnologías
El sistema está construido siguiendo prácticas de desarrollo robusto, seguridad y mantenibilidad.

Tecnologías Clave
Lenguaje: Java (JDK 17+)

Base de Datos: MySQL

Conexión: JDBC (Java Database Connectivity)

Driver: MySQL Connector/J

Patrones de Diseño: MVC, DAO, Factory

Estructura del Proyecto
El proyecto sigue una arquitectura limpia basada en patrones de diseño:

src/app: Contiene la clase Main y el punto de entrada.

src/controller: (C) Lógica de negocio y coordinación (e.g., ArticuloControlador).

src/view: (V) Interfaz de usuario en consola (e.g., ArticuloVista).

src/model: (M) Objetos de negocio (e.g., Pedido, Articulo).

src/dao: Implementaciones del patrón DAO y la Factory para el acceso a la base de datos (e.g., PedidoRepositorioJdbc).

src/util: Clases de utilidad, incluyendo la gestión de la conexión a la DB (Conexion.java).

lib/: Contiene el driver JDBC (mysql-connector-j-9.5.0.jar).
