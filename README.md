# 🛒 Tienda Online — Proyecto MVC en Java

Este proyecto implementa una **aplicación de consola** en Java que simula el funcionamiento de una tienda online.  
El programa permite **gestionar clientes, artículos y pedidos**, siguiendo una arquitectura **MVC (Modelo–Vista–Controlador)** y aplicando principios de **modularidad, encapsulación y reutilización de código**.

---

## 📦 Características principales

- Gestión completa de **clientes** (altas, búsquedas, listados, eliminaciones).  
- Gestión de **artículos** con precios, gastos de envío y tiempos de preparación.  
- Creación y control de **pedidos**, con estados y fechas.  
- Estructura **orientada a objetos** y separada en capas:
  - `model` → clases de dominio (`Cliente`, `Articulo`, `Pedido`, etc.)
  - `dao` → repositorios (almacenamiento y acceso a datos)
  - `controller` → lógica de negocio
  - `view` → interfaz de usuario por consola  
- Uso de **repositorios en memoria** (`ArrayList`) en la versión actual.
- Preparado para migrar a **persistencia con JDBC y MySQL** mediante el patrón **DAO + Factory**.

---


---

## ⚙️ Tecnologías utilizadas

- **Java 17+**  
- **JDK estándar (sin frameworks externos)**  
- (Opcional en la versión extendida) **MySQL** y **JDBC** para persistencia real  
- **Patrón DAO + Factory** para independencia del motor de base de datos

---

## 🚀 Ejecución del programa

### ▶️ Versión actual (repositorios en memoria)

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/arims98/POOwer-Coders.git
   cd Poower Coders
