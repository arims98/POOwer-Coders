# 📦 Online Store — Proyecto 4 (Hibernate + JPA + MySQL)

Este proyecto es la evolución del sistema de gestión de pedidos en Java.
Tras la versión basada en JDBC, el sistema ha sido migrado a **JPA + Hibernate**, usando un ORM profesional que simplifica la persistencia y reduce el código repetitivo.

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
