# 📚 RESUMEN DE MEJORAS IMPLEMENTADAS

Este documento explica todas las mejoras añadidas al proyecto para cumplir con los requisitos del enunciado.

---

## ✅ 1. SCRIPT DE BASE DE DATOS (`database.sql`)

### ¿Qué hace?
Crea toda la estructura de la base de datos MySQL: tablas, claves primarias, claves foráneas y datos de prueba.

### Tablas creadas:
1. **Articulo** - Productos disponibles para venta
   - PK: `codigo_articulo` (INT, AUTO_INCREMENT)
   - Campos: descripcion, precio_venta, gastos_envio, tiempoPrep

2. **Cliente** - Información básica de todos los clientes
   - PK: `email` (VARCHAR)
   - Campos: nombre, domicilio, nif, tipo_cliente (Estándar/Premium)

3. **ClientePremium** - Información adicional solo para Premium
   - PK y FK: `cliente_email` (relaciona con Cliente)
   - Campos: cuota_anual, descuento_envio (%)
   - **Relación 1:1** con Cliente (herencia)

4. **Pedido** - Pedidos realizados por clientes
   - PK: `num_pedido` (INT, AUTO_INCREMENT)
   - FK: `cliente_email` → Cliente
   - FK: `articulo_codigo` → Articulo
   - Campos: cantidad, fecha_hora, estado (PENDIENTE/ENVIADO)

### Relaciones (FOREIGN KEYS):
- Cliente (1) ← (0..1) ClientePremium
- Cliente (1) → (N) Pedido
- Articulo (1) → (N) Pedido

### Cómo usar:
```sql
-- En MySQL Workbench o consola MySQL:
SOURCE database.sql;
-- o
mysql -u root -p < database.sql
```

---

## ✅ 2. PROCEDIMIENTOS ALMACENADOS (`procedimientos_almacenados.sql`)

### ¿Qué son?
Son "funciones" guardadas en el servidor MySQL que ejecutan lógica compleja.

### Ventajas:
- ✅ Mejor rendimiento (menos viajes red)
- ✅ Lógica centralizada en la BD
- ✅ Más seguridad
- ✅ Reutilización entre aplicaciones

### Procedimientos creados:

#### 🔹 `sp_agregar_pedido_completo`
**¿Qué hace?**: Crea un pedido validando que cliente y artículo existan.

**Parámetros**:
- IN: `cliente_email`, `articulo_codigo`, `cantidad`
- OUT: `num_pedido` (ID generado), `mensaje` (resultado)

**Ejemplo SQL**:
```sql
CALL sp_agregar_pedido_completo(
    'juan.perez@email.com', 
    1, 
    2, 
    @num_pedido, 
    @mensaje
);
SELECT @num_pedido, @mensaje;
```

#### 🔹 `sp_calcular_total_pedido`
**¿Qué hace?**: Calcula el precio total con descuentos Premium automáticos.

**Parámetros**:
- IN: `num_pedido`
- OUT: `subtotal`, `gastos_envio`, `descuento`, `total`, `mensaje`

**Ejemplo SQL**:
```sql
CALL sp_calcular_total_pedido(
    1, 
    @subtotal, 
    @gastos_envio, 
    @descuento, 
    @total, 
    @mensaje
);
SELECT @subtotal, @gastos_envio, @descuento, @total;
```

#### 🔹 `sp_obtener_estadisticas_cliente`
**¿Qué hace?**: Obtiene estadísticas de compra de un cliente.

**Parámetros**:
- IN: `cliente_email`
- OUT: `total_pedidos`, `gasto_total`, `tipo_cliente`, `mensaje`

**Ejemplo SQL**:
```sql
CALL sp_obtener_estadisticas_cliente(
    'maria.garcia@email.com',
    @total_pedidos,
    @gasto_total,
    @tipo_cliente,
    @mensaje
);
SELECT @total_pedidos, @gasto_total, @tipo_cliente;
```

### Cómo usar:
```sql
SOURCE procedimientos_almacenados.sql;
```

---

## ✅ 3. LLAMADAS A PROCEDIMIENTOS EN JAVA

### Nuevos métodos añadidos:

#### En `MySqlPedidoDAO.java`:

##### 🔹 `agregarPedidoConProcedimiento()`
**¿Qué hace?**: Crea un pedido usando el procedimiento almacenado.

**Uso en Java**:
```java
MySqlPedidoDAO pedidoDAO = new MySqlPedidoDAO();
int numPedido = pedidoDAO.agregarPedidoConProcedimiento(
    "juan.perez@email.com",  // email cliente
    1,                        // código artículo
    2                         // cantidad
);
System.out.println("Pedido creado: " + numPedido);
```

##### 🔹 `calcularTotalPedido()`
**¿Qué hace?**: Calcula el total de un pedido con descuentos.

**Uso en Java**:
```java
double[] totales = pedidoDAO.calcularTotalPedido(1);
// totales[0] = subtotal
// totales[1] = gastos_envio
// totales[2] = descuento
// totales[3] = total
```

#### En `MySqlClienteDAO.java`:

##### 🔹 `obtenerEstadisticasCliente()`
**¿Qué hace?**: Obtiene estadísticas de compra.

**Uso en Java**:
```java
MySqlClienteDAO clienteDAO = new MySqlClienteDAO();
String stats = clienteDAO.obtenerEstadisticasCliente("maria.garcia@email.com");
System.out.println(stats);
```

### Tecnología usada:
- **`CallableStatement`**: Clase de JDBC para ejecutar procedimientos almacenados
- **`registerOutParameter()`**: Registra parámetros de salida (OUT)
- **`Types.INTEGER`, `Types.DECIMAL`, etc.**: Define tipos de datos SQL

---

## ✅ 4. TRANSACCIONES EN MySqlPedidoDAO

### ¿Qué son las transacciones?
Son bloques de operaciones que se ejecutan "todo o nada":
- ✅ Si todo va bien → `COMMIT` (guardar cambios)
- ❌ Si algo falla → `ROLLBACK` (deshacer todo)

### Método actualizado: `agregar()`

**Antes** (sin transacciones):
```java
conn.getConnection();
ps.executeUpdate();  // Si falla aquí, ¡puede quedar basura!
```

**Ahora** (con transacciones):
```java
conn.setAutoCommit(false);  // ⭐ INICIAR TRANSACCIÓN
try {
    ps.executeUpdate();
    conn.commit();          // ⭐ TODO OK, GUARDAR
} catch (Exception e) {
    conn.rollback();        // ⭐ ERROR, DESHACER TODO
}
```

### ¿Por qué en Pedido?
Porque un pedido relaciona Cliente + Artículo. Si el artículo no existe, el pedido no debe crearse.

---

## ✅ 5. MÉTODO ACTUALIZAR() IMPLEMENTADO

### Añadido a la interfaz `Repositorio`:
```java
void actualizar(T objeto) throws Exception;
```

### Implementado en todos los DAOs:

#### `MySqlArticuloDAO.actualizar()`
Actualiza: descripción, precio, gastos_envio, tiempoPrep
```java
articuloDAO.actualizar(articulo);
```

#### `MySqlClienteDAO.actualizar()`
Actualiza: nombre, domicilio, nif
```java
clienteDAO.actualizar(cliente);
```

#### `MySqlPedidoDAO.actualizar()`
Actualiza: cantidad, estado
```java
pedido.setEstado(Pedido.Estado.ENVIADO);
pedidoDAO.actualizar(pedido);
```

---

## 📊 RESUMEN DE CUMPLIMIENTO DE REQUISITOS

| Requisito | Estado | Archivo |
|-----------|--------|---------|
| ✅ Patrón MVC | COMPLETO | src/model, view, controller |
| ✅ Patrón DAO | COMPLETO | src/dao/Repositorio.java + DAOs |
| ✅ Patrón Factory | COMPLETO | src/dao/DAOFactory.java |
| ✅ Clase utilidad conexiones | COMPLETO | src/util/ConexionBD.java |
| ✅ JDBC con PreparedStatement | COMPLETO | Todos los DAOs |
| ✅ Prevención SQL Injection | COMPLETO | Uso de ? en queries |
| ✅ MySQL como SGBD | COMPLETO | database.sql |
| ✅ Script creación BD | COMPLETO | database.sql |
| ✅ PRIMARY KEYS | COMPLETO | database.sql |
| ✅ FOREIGN KEYS | COMPLETO | database.sql |
| ✅ Transacciones | COMPLETO | MySqlClienteDAO, MySqlPedidoDAO |
| ✅ Procedimientos almacenados | COMPLETO | procedimientos_almacenados.sql |
| ✅ CallableStatement (usar PAs) | COMPLETO | MySqlPedidoDAO, MySqlClienteDAO |
| ✅ Método actualizar() | COMPLETO | Todos los DAOs |

---

## 🚀 CÓMO EJECUTAR EL PROYECTO

### 1. Configurar la Base de Datos:
```bash
# En MySQL:
mysql -u root -p
source database.sql
source procedimientos_almacenados.sql
```

### 2. Verificar conexión en `ConexionBD.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/tienda";
private static final String USUARIO = "root";
private static final String PASSWORD = "tu_password";
```

### 3. Ejecutar el proyecto:
```bash
# Compilar
javac -d bin -cp "lib/*" src/**/*.java

# Ejecutar
java -cp "bin:lib/*" controller.Controlador
```

---

## 📝 EJEMPLO DE USO COMPLETO

```java
// 1. Crear un cliente usando DAO normal
Cliente cliente = new ClienteEstandar("test@email.com", "Test", "Calle 123", "12345678X");
DAOFactory.getClienteDAO().agregar(cliente);

// 2. Crear un pedido usando PROCEDIMIENTO ALMACENADO
MySqlPedidoDAO pedidoDAO = (MySqlPedidoDAO) DAOFactory.getPedidoDAO();
int numPedido = pedidoDAO.agregarPedidoConProcedimiento(
    "test@email.com",
    1,  // código artículo
    2   // cantidad
);

// 3. Calcular total usando PROCEDIMIENTO
double[] totales = pedidoDAO.calcularTotalPedido(numPedido);
System.out.println("Total del pedido: " + totales[3] + "€");

// 4. Ver estadísticas usando PROCEDIMIENTO
MySqlClienteDAO clienteDAO = (MySqlClienteDAO) DAOFactory.getClienteDAO();
clienteDAO.obtenerEstadisticasCliente("test@email.com");

// 5. Actualizar el pedido
Pedido pedido = pedidoDAO.buscarPorId(numPedido);
pedido.setEstado(Pedido.Estado.ENVIADO);
pedidoDAO.actualizar(pedido);
```

---

## 🎓 CONCEPTOS CLAVE PARA ENTENDER

### 1. **PreparedStatement** vs **Statement**
- ✅ PreparedStatement: Usa `?` para parámetros → **Seguro contra SQL Injection**
- ❌ Statement: Concatena strings → **Vulnerable**

### 2. **CallableStatement**
- Para ejecutar **procedimientos almacenados**
- Sintaxis: `{CALL nombre_procedimiento(?, ?, ?)}`

### 3. **Transacciones**
- `setAutoCommit(false)`: Iniciar transacción
- `commit()`: Guardar cambios
- `rollback()`: Deshacer cambios

### 4. **Patrón DAO**
- Separa la lógica de datos del resto
- Fácil cambiar de MySQL a Oracle

### 5. **Patrón Factory**
- Crea objetos sin especificar la clase exacta
- `DAOFactory.getArticuloDAO()` → devuelve MySqlArticuloDAO

---

## 📚 ARCHIVOS CREADOS/MODIFICADOS

### Nuevos archivos:
- ✅ `database.sql` - Script de creación de BD
- ✅ `procedimientos_almacenados.sql` - Procedimientos almacenados
- ✅ `RESUMEN_MEJORAS.md` - Este documento

### Archivos modificados:
- ✅ `src/dao/Repositorio.java` - Añadido método actualizar()
- ✅ `src/dao/MySqlArticuloDAO.java` - Añadido actualizar()
- ✅ `src/dao/MySqlClienteDAO.java` - Añadido actualizar() y método para estadísticas
- ✅ `src/dao/MySqlPedidoDAO.java` - Añadido transacciones, actualizar() y métodos para procedimientos

---

## 🎯 PRÓXIMOS PASOS (Opcional)

1. **Probar los procedimientos almacenados** directamente en MySQL
2. **Integrar las llamadas** a procedimientos en el Controlador
3. **Añadir menú** en Consola para usar las nuevas funcionalidades
4. **Crear más procedimientos** según necesidades (ej: actualizar stock)
5. **Documentar** el código con más JavaDoc

---

## ❓ PREGUNTAS FRECUENTES

### ¿Debo usar siempre los procedimientos almacenados?
No, son opcionales. Los métodos normales (agregar, buscar, etc.) siguen funcionando. Los procedimientos son para lógica compleja.

### ¿Las transacciones son obligatorias?
Sí, para operaciones que modifiquen múltiples tablas o sean críticas. Ya están implementadas en ClienteDAO (agregar) y PedidoDAO (agregar).

### ¿Puedo cambiar de MySQL a PostgreSQL?
Sí, solo necesitas:
1. Crear nuevos DAOs (ej: PostgresArticuloDAO)
2. Modificar DAOFactory para devolver los nuevos DAOs
3. El resto del código NO cambia (esa es la ventaja del patrón DAO)

---

**¡Todo listo para cumplir con los requisitos del proyecto!** 🎉
