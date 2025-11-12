# 📚 GUÍA SIMPLE - Tu Proyecto Adaptado a online_store_db

## ✅ ¿QUÉ SE HA HECHO?

Tu código ahora funciona con la base de datos `online_store_db` que tenéis en el grupo.

---

## 🔄 CAMBIOS PRINCIPALES

### 1. **Base de Datos**
- ✅ **Antes**: `tienda`
- ✅ **Ahora**: `online_store_db`
- **Archivo**: `src/util/ConexionBD.java`

### 2. **Tabla Pedido** - Cambios importantes
- ✅ **Añadido**: Campo `precio_total` (DECIMAL)
- ✅ **Eliminado**: Campo `estado` (ya no existe en vuestra BD)
- ✅ **Cálculo automático**: El precio se calcula al crear/actualizar pedido

### 3. **Modelo Pedido.java**
**Antes**:
```java
private Estado estado;  // ELIMINADO
```

**Ahora**:
```java
private double precioTotal;  // AÑADIDO
```

---

## 💰 CÁLCULO AUTOMÁTICO DE PRECIOS

### Cuando creas un pedido, el sistema calcula:

```
subtotal = precio_venta × cantidad
gastos_envio = gastos_envio del artículo

Si el cliente es Premium:
  descuento = gastos_envio × (descuento_envio / 100)
  gastos_envio = gastos_envio - descuento

precio_total = subtotal + gastos_envio
```

### Ejemplo real:
```
Artículo: Ratón (precio: 15.50€, envío: 2.00€)
Cantidad: 5
Cliente: Estándar

subtotal = 15.50 × 5 = 77.50€
gastos_envio = 2.00€
TOTAL = 79.50€
```

### Ejemplo Premium:
```
Artículo: Ratón (precio: 15.50€, envío: 2.00€)
Cantidad: 5
Cliente: Premium (20% descuento en envío)

subtotal = 15.50 × 5 = 77.50€
descuento = 2.00 × 0.20 = 0.40€
gastos_envio = 2.00 - 0.40 = 1.60€
TOTAL = 79.10€
```

---

## 📝 CÓMO USAR EL CÓDIGO

### Crear un Pedido (Método Normal)
```java
// 1. Obtener cliente y artículo
Cliente cliente = DAOFactory.getClienteDAO().buscarPorId("sergio@tienda.es");
Articulo articulo = DAOFactory.getArticuloDAO().buscarPorId(1);

// 2. Crear pedido (el precio se calcula automáticamente)
Pedido pedido = new Pedido(cliente, articulo, 5, LocalDateTime.now());

// 3. Guardar
DAOFactory.getPedidoDAO().agregar(pedido);

// 4. El precio_total ya está calculado y guardado en la BD
System.out.println("Total: " + pedido.getPrecioTotal() + "€");
```

### Crear Pedido con Procedimiento Almacenado
```java
MySqlPedidoDAO pedidoDAO = (MySqlPedidoDAO) DAOFactory.getPedidoDAO();

int numPedido = pedidoDAO.agregarPedidoConProcedimiento(
    "sergio@tienda.es",  // email del cliente
    1,                    // código del artículo
    5                     // cantidad
);

System.out.println("Pedido creado: " + numPedido);
```

### Actualizar un Pedido
```java
// 1. Buscar el pedido
Pedido pedido = DAOFactory.getPedidoDAO().buscarPorId(3);

// 2. Cambiar la cantidad (el precio se recalcula automáticamente)
pedido.setCantidad(10);

// 3. Actualizar en la BD
DAOFactory.getPedidoDAO().actualizar(pedido);

System.out.println("Nuevo total: " + pedido.getPrecioTotal() + "€");
```

---

## 🗄️ ESTRUCTURA DE LA BD (online_store_db)

### Tabla: Articulo
```sql
codigo_articulo INT (PK, AUTO_INCREMENT)
descripcion VARCHAR(255)
precio_venta DECIMAL(10,2)
gastos_envio DECIMAL(10,2)
tiempoPrep SMALLINT
```

### Tabla: Cliente
```sql
num_cliente INT (AUTO_INCREMENT)
email VARCHAR(100) (PK)
nombre VARCHAR(100)
domicilio VARCHAR(225)
nif VARCHAR(20) (UNIQUE)
tipo_cliente ENUM('Estándar','Premium')
```

### Tabla: ClientePremium
```sql
cliente_email VARCHAR(100) (PK, FK → Cliente.email)
cuota_anual DECIMAL(10,2) DEFAULT 30.00
descuento_envio SMALLINT DEFAULT 20
```

### Tabla: Pedido
```sql
num_pedido INT (PK, AUTO_INCREMENT)
cliente_email VARCHAR(50) (FK → Cliente.email)
articulo_codigo INT (FK → Articulo.codigo_articulo)
cantidad SMALLINT
fecha_hora DATETIME
precio_total DECIMAL(10,2)  ← IMPORTANTE: Este campo se llena automáticamente
```

---

## 🔧 PROCEDIMIENTOS ALMACENADOS

### sp_agregar_pedido_completo
**Qué hace**: Crea un pedido validando cliente y artículo, y calcula el precio_total

**Uso desde Java**:
```java
MySqlPedidoDAO dao = (MySqlPedidoDAO) DAOFactory.getPedidoDAO();
int numPedido = dao.agregarPedidoConProcedimiento("sergio@tienda.es", 1, 5);
```

**Uso desde MySQL**:
```sql
CALL sp_agregar_pedido_completo(
    'sergio@tienda.es',
    1,
    5,
    @num_pedido,
    @precio_total,
    @mensaje
);
SELECT @num_pedido, @precio_total, @mensaje;
```

### sp_calcular_total_pedido
**Qué hace**: Calcula el total de un pedido existente

### sp_obtener_estadisticas_cliente
**Qué hace**: Muestra cuántos pedidos y cuánto ha gastado un cliente

---

## ⚙️ CONFIGURACIÓN

### Archivo: `ConexionBD.java`
```java
private static final String URL = "jdbc:mysql://localhost:3306/online_store_db";
private static final String USUARIO = "root";
private static final String PASSWORD = "";
```

**Si tu contraseña de MySQL es diferente**, cámbiala en `PASSWORD`.

---

## 🚀 PASOS PARA EJECUTAR

### 1. Importar la Base de Datos
```bash
# En MySQL Workbench o terminal:
mysql -u root -p

# Dentro de MySQL:
SOURCE database.sql
SOURCE procedimientos_almacenados.sql
```

### 2. Verificar que todo está bien
```sql
USE online_store_db;
SHOW TABLES;

-- Deberías ver:
-- Articulo
-- Cliente  
-- ClientePremium
-- Pedido
```

### 3. Ver los datos de ejemplo
```sql
SELECT * FROM Cliente;
SELECT * FROM Articulo;
SELECT * FROM Pedido;
```

### 4. Ejecutar tu programa Java
```bash
# Desde VS Code:
Run → Run Without Debugging
# O presiona Ctrl+F5
```

---

## 🎯 RESUMEN DE LO IMPORTANTE

1. **No hay campo `estado`** en la BD → Se eliminó del código
2. **Hay campo `precio_total`** → Se calcula automáticamente
3. **Descuentos Premium** → Se aplican automáticamente al calcular el total
4. **Base de datos**: `online_store_db` (no `tienda`)
5. **Todo está adaptado** a la estructura de vuestra BD del grupo

---

## ❓ PREGUNTAS FRECUENTES

### ¿Por qué ya no hay estado (PENDIENTE/ENVIADO)?
Porque vuestra base de datos no tiene esa columna. Si la necesitáis, tendríais que agregarla a la BD.

### ¿Dónde se calcula el precio_total?
Se calcula automáticamente en 2 lugares:
- En `MySqlPedidoDAO.agregar()` → Cuando creas un pedido
- En `MySqlPedidoDAO.actualizar()` → Cuando cambias la cantidad

### ¿Por qué calcular el precio en Java y no solo en la BD?
Para que el objeto Pedido en Java siempre tenga el precio correcto. Pero también está en el procedimiento almacenado para que funcione desde ambos lados.

### ¿Puedo seguir usando los métodos normales (sin procedimientos)?
¡Sí! Los métodos normales (`agregar()`, `buscarPorId()`, etc.) siguen funcionando perfectamente.

---

## 📌 ARCHIVOS MODIFICADOS

- ✅ `src/util/ConexionBD.java` → URL de BD cambiada
- ✅ `src/model/Pedido.java` → Sin estado, con precio_total
- ✅ `src/dao/MySqlPedidoDAO.java` → Cálculo de precios automático
- ✅ `src/model/ListaPedidos.java` → Sin filtros por estado
- ✅ `database.sql` → Reemplazado con online_store_db.sql
- ✅ `procedimientos_almacenados.sql` → Adaptado para online_store_db

---

**¡Todo listo para trabajar con la BD del grupo!** 🎉

Si tienes dudas, pregúntame lo que necesites.
