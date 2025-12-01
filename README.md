📦 Online Store — Proyecto 4 (Hibernate + JPA + MySQL)

Este proyecto es la evolución del sistema de gestión de pedidos en Java.
Tras la versión basada en JDBC, el sistema ha sido migrado a JPA + Hibernate para usar un ORM profesional que simplifica la persistencia y reduce el código repetitivo.

Tecnologías Clave:

Lenguaje: Java (JDK 21+)

ORM: JPA 3.1 + Hibernate ORM 6.4

Base de Datos: MySQL

Build Tool: Maven

Arquitectura: MVC + DAO (repositorio JPA)

Patrones: DAO, Repository, MVC


Estructura del Proyecto:

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
```

