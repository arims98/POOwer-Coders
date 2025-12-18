# 🛒 Online Store — Proyecto 5 (JavaFX + MVC + JPA/Hibernate + MySQL)

Aplicación de escritorio para la gestión de **Artículos**, **Clientes** y **Pedidos**, implementada en **JavaFX** y manteniendo el patrón **MVC**.  
La persistencia se realiza mediante **ORM (JPA + Hibernate)** sobre una base de datos **MySQL**.

---

## ✅ Objetivo del Producto 5

- Sustituir la interacción por consola por una **interfaz gráfica (JavaFX)**.
- Mantener el patrón **Modelo–Vista–Controlador (MVC)**:
    - **Modelo**: entidades del dominio.
    - **Controladores**: lógica de negocio.
    - **Vista**: JavaFX (Tabs + TableView + formularios).
- Mantener la persistencia con **mapeo ORM** (JPA/Hibernate).

---

## 🧰 Tecnologías

- **Java**: JDK 21+
- **GUI**: JavaFX
- **ORM**: Jakarta Persistence (JPA) + Hibernate
- **Base de datos**: MySQL
- **Build Tool**: Maven
- **Arquitectura / Patrones**: MVC + DAO/Repository + Factory

---

## 📁 Estructura del proyecto (resumen)

> Nota: el proyecto usa un layout no estándar con `<sourceDirectory>src</sourceDirectory>`.

src/
├─ app/ → Entry points: Main (consola) y MainFX (JavaFX)
├─ controller/ → Controladores (lógica de negocio)
├─ model/ → Entidades (JPA/Hibernate)
├─ dao/ → Repositorios + DAOFactory (EntityManager)
├─ view/ → Vista por consola (versiones anteriores)
└─ viewfx/ → Vista JavaFX (Producto 5)
resources/
└─ META-INF/persistence.xml → Configuración JPA/Hibernate (MySQL)

yaml
Copiar código

---

## 🖥️ Funcionalidades (JavaFX)

La interfaz gráfica incluye pestañas y tablas (**TableView**) para:

### Artículos
- Alta de artículo
- Listado en tabla con formato (precio/envío a 2 decimales)

### Clientes
- Alta de cliente (Estándar / Premium)
- Listado en tabla
- Filtros: Todos / Estándar / Premium
- Eliminación con confirmación

### Pedidos
- Crear pedido (cliente + artículo + cantidad)
- Listado en tabla
- Buscar pedido (popup)
- Eliminar pedido (con confirmación y regla: solo si está pendiente)
- Filtros: Todos / Pendientes / Enviados + filtro por NIF
- Fecha/hora formateada

---

## ⚙️ Requisitos previos

- **JDK 21** instalado
- **MySQL** en ejecución
- Base de datos configurada según `resources/META-INF/persistence.xml`
    - URL: `jdbc:mysql://localhost:3306/online_store_bd`
    - Usuario/Password: según tu configuración

---

## ▶️ Ejecución (JavaFX) — recomendado

Desde IntelliJ:
- Abrir panel **Maven**
- `Plugins → javafx → javafx:run`

O desde terminal (si tienes Maven en el PATH):

```bash
mvn clean javafx:run