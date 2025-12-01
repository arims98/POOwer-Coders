# Online Store - Implementación JPA/Hibernate

## 📋 Descripción
Aplicación de escritorio para gestión de tienda online implementando persistencia mediante JPA (Java Persistence API) con Hibernate como proveedor ORM, manteniendo el patrón de diseño MVC.

## 🛠️ Tecnologías Utilizadas
- **Java 11+**
- **JPA 3.1** (Jakarta Persistence API)
- **Hibernate 6.4.4** (Implementación ORM)
- **MySQL 8.0+**
- **Patrón de diseño MVC** (Modelo-Vista-Controlador)
- **Patrón Factory** (para creación de DAOs)

## 📦 Estructura del Proyecto

```
src/
├── app/
│   └── Main.java                    # Punto de entrada de la aplicación
├── model/                           # Entidades JPA
│   ├── Articulo.java               # Entidad con @Entity y @Table
│   ├── Cliente.java                # Entidad abstracta con herencia SINGLE_TABLE
│   ├── ClienteEstandar.java        # Subclase con @DiscriminatorValue
│   ├── ClientePremium.java         # Subclase con @DiscriminatorValue
│   └── Pedido.java                 # Entidad con relaciones @ManyToOne
├── dao/                            # Capa de acceso a datos
│   ├── Repositorio.java            # Interfaz genérica
│   ├── DAOFactory.java             # Interfaz Factory
│   ├── JPADAOFactory.java          # Factory para repositorios JPA
│   ├── ArticuloRepositorioJPA.java # Repositorio JPA para Artículos
│   ├── ClienteRepositorioJPA.java  # Repositorio JPA para Clientes
│   └── PedidoRepositorioJPA.java   # Repositorio JPA para Pedidos
├── controller/                      # Controladores MVC
│   ├── ArticuloControlador.java
│   ├── ClienteControlador.java
│   └── PedidoControlador.java
├── view/                           # Vistas de usuario
│   ├── ArticuloVista.java
│   ├── ClienteVista.java
│   └── PedidoVista.java
├── util/
│   └── EntityManagerUtil.java      # Utilidad para gestionar EntityManager
└── META-INF/
    └── persistence.xml             # Configuración de JPA
```

## 🔧 Configuración de la Base de Datos

### 1. Crear la base de datos
Ejecuta el script SQL ubicado en `Bd/online-store_bd.sql` en tu servidor MySQL.

### 2. Configurar credenciales
Edita el archivo `src/META-INF/persistence.xml` y ajusta las siguientes propiedades:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/online_store_bd?useSSL=false&amp;serverTimezone=UTC"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="TU_CONTRASEÑA"/>
```

## 📚 Conceptos JPA/Hibernate Implementados

### 1. Anotaciones de Entidad
- `@Entity`: Marca una clase como entidad JPA
- `@Table`: Mapea la entidad a una tabla específica
- `@Id`: Define la clave primaria
- `@Column`: Mapea un atributo a una columna

### 2. Relaciones
- `@ManyToOne`: Relación muchos-a-uno (Pedido → Cliente, Pedido → Artículo)
- `@JoinColumn`: Define la columna de la clave foránea
- `FetchType.EAGER`: Carga inmediata de las relaciones

### 3. Herencia
- `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`: Estrategia de herencia en una sola tabla
- `@DiscriminatorColumn`: Columna que diferencia los tipos de entidad
- `@DiscriminatorValue`: Valor del discriminador para cada subclase

### 4. Operaciones CRUD con EntityManager
- `persist()`: Crear (INSERT)
- `find()`: Buscar por ID (SELECT)
- `createQuery()`: Consultas JPQL
- `merge()`: Actualizar (UPDATE)
- `remove()`: Eliminar (DELETE)

### 5. Gestión de Transacciones
```java
EntityTransaction tx = em.getTransaction();
tx.begin();
// operaciones
tx.commit();
// o tx.rollback() en caso de error
```

## 🚀 Ejecución del Proyecto

### Desde VS Code
1. Asegúrate de tener todos los JARs en la carpeta `lib/`
2. Abre el proyecto en VS Code
3. Ejecuta `Main.java`

### Desde la terminal
```bash
# Compilar
javac -cp "lib/*" -d bin src/**/*.java

# Ejecutar
java -cp "bin;lib/*" app.Main
```

## 📝 Funcionalidades

### Gestión de Artículos
- ✅ Agregar nuevo artículo
- ✅ Listar todos los artículos
- ✅ Buscar artículo por código
- ✅ Eliminar artículo

### Gestión de Clientes
- ✅ Agregar cliente (Estándar o Premium)
- ✅ Listar todos los clientes
- ✅ Buscar cliente por NIF
- ✅ Eliminar cliente

### Gestión de Pedidos
- ✅ Crear nuevo pedido
- ✅ Listar todos los pedidos
- ✅ Buscar pedido por número
- ✅ Eliminar pedido

## 🔄 Comparación: JDBC vs JPA

### Antes (JDBC)
```java
String sql = "INSERT INTO ARTICULO VALUES (?, ?, ?, ?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, articulo.getCodigo());
ps.setString(2, articulo.getDescripcion());
// ... más código SQL manual
ps.executeUpdate();
```

### Ahora (JPA)
```java
EntityManager em = EntityManagerUtil.getEntityManager();
em.getTransaction().begin();
em.persist(articulo);  // ¡Así de simple!
em.getTransaction().commit();
```

## ✨ Ventajas de JPA/Hibernate

1. **Abstracción del SQL**: No escribes SQL manualmente
2. **Portabilidad**: Cambiar de MySQL a PostgreSQL es trivial
3. **Gestión automática de relaciones**: Hibernate carga objetos relacionados
4. **Cache de primer nivel**: Mejor rendimiento
5. **Lazy/Eager Loading**: Control fino sobre cuándo cargar relaciones
6. **JPQL**: Consultas orientadas a objetos en lugar de tablas

## 🎯 Patrón MVC Mantenido

- **Modelo** (`model/`): Entidades JPA con anotaciones
- **Vista** (`view/`): Interfaces de usuario (sin cambios)
- **Controlador** (`controller/`): Lógica de negocio (sin cambios)
- **DAO** (`dao/`): Ahora usa JPA en lugar de JDBC

## 📌 Notas Importantes

1. **Constructor vacío**: Todas las entidades JPA requieren un constructor sin argumentos
2. **Transacciones**: Toda operación de escritura debe estar en una transacción
3. **Cierre de recursos**: Siempre cerrar el EntityManager después de usarlo
4. **EntityManagerFactory**: Se cierra al finalizar la aplicación

## 🐛 Troubleshooting

### Error: "No Persistence provider for EntityManager"
- Verifica que todos los JARs de Hibernate estén en `lib/`
- Revisa que `persistence.xml` esté en `src/META-INF/`

### Error de conexión a MySQL
- Verifica que MySQL esté ejecutándose
- Comprueba usuario y contraseña en `persistence.xml`
- Asegúrate de que la base de datos existe

### Error: "Table doesn't exist"
- Ejecuta el script SQL de la carpeta `Bd/`
- O cambia en `persistence.xml`: `hibernate.hbm2ddl.auto` a `create`

## 📖 Referencias

- [JPA Specification](https://jakarta.ee/specifications/persistence/)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/3.1/)

---

**Autor**: Sergio Gómez Gutiérrez  
**Asignatura**: POO con BBDD - UOC DAW  
**Fecha**: Diciembre 2025
