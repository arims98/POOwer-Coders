# 📦 Online Store — Proyecto 4 (Hibernate + JPA + MySQL)

Este proyecto es la evolución del sistema de gestión de pedidos en Java.
Tras la versión basada en JDBC, el sistema ha sido migrado a **JPA + Hibernate**, usando un ORM profesional que simplifica la persistencia y reduce el código repetitivo.
=======
📦 Online Store — Proyecto 3 (JDBC & MySQL)

Este proyecto representa la evolución de un sistema de gestión de pedidos en Java, transformado para utilizar una base de datos relacional (MySQL) como su capa de persistencia, manteniendo los principios de diseño Modelo-Vista-Controlador (MVC) y Data Access Object (DAO).
>>>>>>> 9aa8748 (Actualizar código, README y añadir carpeta lib)

---

## Tecnologías

* **Lenguaje:** Java (JDK 21+)
* **ORM:** JPA 3.1 + Hibernate 6.4
* **Base de Datos:** MySQL
* **Build Tool:** Maven
* **Arquitectura:** MVC + DAO
* **Patrones de diseño:** DAO, Repository, MVC

---

## Estructura del Proyecto

```
src/
├─ main/java
│   ├─ app/          → Punto de entrada (Main)
│   ├─ controller/   → Controladores (lógica de negocio)
│   ├─ view/         → Interfaz de usuario por consola
│   ├─ model/        → Entidades JPA (@Entity)
│   ├─ dao/          → Repositorios (JPA + EntityManager)
│   └─ util/         → Utilidades y configuración
├─ main/resources
│   └─ META-INF/persistence.xml  → Configuración JPA/Hibernate
└─ test/             → Tests (no usados)
=======
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
Construcción y ejecución (Maven + Java 21)

Requisitos:
- JDK 21 instalado
- Maven (opcional si prefieres usar mvn wrapper)

Ejemplo - compilar y ejecutar desde PowerShell:

```pwsh
# compilar
mvn clean package -DskipTests

# ejecutar (jar ejecutable generado)
java -jar target/POOwer-Coders-1.0.0-SNAPSHOT.jar
>>>>>>> 9aa8748 (Actualizar código, README y añadir carpeta lib)
```

---

## Contenido actual en la rama `celiaP4`

* Código fuente actualizado (`src/`)
* Archivos de proyecto importantes (`pom.xml`, `sources*.txt`)
* Documentación (`README.md`)
* Ignorados por `.gitignore`: archivos compilados (`bin/`), librerías locales (`lib/`), configuraciones de IDE (`.idea/`, `.vscode/`)

---

## Clonar el Repositorio

```bash
git clone https://github.com/arims98/POOwer-Coders.git
cd POOwer-Coders
```

---

## Compilación y Ejecución

### Requisitos

* JDK 21 instalado
* Maven (opcional si prefieres usar `mvn wrapper`)

### Instrucciones

```powershell
# compilar el proyecto
mvn clean package -DskipTests

# ejecutar el JAR generado
java -jar target/POOwer-Coders-1.0.0-SNAPSHOT.jar
```

