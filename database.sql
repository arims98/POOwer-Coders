-- =====================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - TIENDA ONLINE
-- =====================================================
-- Este script crea toda la estructura de la base de datos
-- Incluye: tablas, claves primarias, claves foráneas y relaciones

-- 1. CREAR LA BASE DE DATOS (si no existe)
CREATE DATABASE IF NOT EXISTS tienda;
USE tienda;

-- 2. ELIMINAR TABLAS SI EXISTEN (para poder recrearlas)
-- El orden importa por las FOREIGN KEYS
DROP TABLE IF EXISTS Pedido;
DROP TABLE IF EXISTS ClientePremium;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Articulo;

-- =====================================================
-- 3. TABLA ARTICULO
-- =====================================================
-- Almacena los productos que se pueden comprar
CREATE TABLE Articulo (
    codigo_articulo INT AUTO_INCREMENT,      -- ID único del artículo
    descripcion VARCHAR(200) NOT NULL,       -- Nombre/descripción del producto
    precio_venta DECIMAL(10,2) NOT NULL,     -- Precio de venta (ej: 29.99)
    gastos_envio DECIMAL(10,2) NOT NULL,     -- Costo de envío (ej: 5.50)
    tiempoPrep INT NOT NULL,                 -- Tiempo de preparación en minutos
    
    PRIMARY KEY (codigo_articulo),           -- Clave primaria
    
    -- Restricciones de validación
    CHECK (precio_venta >= 0),
    CHECK (gastos_envio >= 0),
    CHECK (tiempoPrep >= 0)
);

-- =====================================================
-- 4. TABLA CLIENTE
-- =====================================================
-- Almacena información básica de todos los clientes
CREATE TABLE Cliente (
    email VARCHAR(100),                      -- Email único del cliente (PK)
    nombre VARCHAR(100) NOT NULL,            -- Nombre completo
    domicilio VARCHAR(200) NOT NULL,         -- Dirección de envío
    nif VARCHAR(20) NOT NULL UNIQUE,         -- NIF/DNI único
    tipo_cliente ENUM('Estándar', 'Premium') NOT NULL,  -- Tipo de cliente
    
    PRIMARY KEY (email)                      -- Email es la clave primaria
);

-- =====================================================
-- 5. TABLA CLIENTEPREMIUM
-- =====================================================
-- Almacena información adicional solo para clientes Premium
-- Relación 1:1 con Cliente (herencia)
CREATE TABLE ClientePremium (
    cliente_email VARCHAR(100),              -- Email del cliente (FK)
    cuota_anual DECIMAL(10,2) NOT NULL,      -- Cuota anual (ej: 30.00)
    descuento_envio INT NOT NULL,            -- Descuento en % (ej: 20 significa 20%)
    
    PRIMARY KEY (cliente_email),             -- Email es la clave primaria
    
    -- CLAVE FORÁNEA: relaciona con la tabla Cliente
    FOREIGN KEY (cliente_email) REFERENCES Cliente(email)
        ON DELETE CASCADE                     -- Si se borra el cliente, se borra el premium
        ON UPDATE CASCADE,                    -- Si se actualiza el email, se actualiza aquí
    
    -- Restricciones de validación
    CHECK (cuota_anual >= 0),
    CHECK (descuento_envio >= 0 AND descuento_envio <= 100)
);

-- =====================================================
-- 6. TABLA PEDIDO
-- =====================================================
-- Almacena los pedidos realizados por los clientes
CREATE TABLE Pedido (
    num_pedido INT AUTO_INCREMENT,           -- Número de pedido único
    cliente_email VARCHAR(100) NOT NULL,     -- Email del cliente (FK)
    articulo_codigo INT NOT NULL,            -- Código del artículo (FK)
    cantidad INT NOT NULL,                   -- Cantidad de artículos pedidos
    fecha_hora DATETIME NOT NULL,            -- Fecha y hora del pedido
    estado ENUM('PENDIENTE', 'ENVIADO') DEFAULT 'PENDIENTE',  -- Estado del pedido
    
    PRIMARY KEY (num_pedido),                -- Número de pedido es la PK
    
    -- CLAVES FORÁNEAS
    FOREIGN KEY (cliente_email) REFERENCES Cliente(email)
        ON DELETE RESTRICT                    -- No se puede borrar un cliente con pedidos
        ON UPDATE CASCADE,
        
    FOREIGN KEY (articulo_codigo) REFERENCES Articulo(codigo_articulo)
        ON DELETE RESTRICT                    -- No se puede borrar un artículo con pedidos
        ON UPDATE CASCADE,
    
    -- Restricciones de validación
    CHECK (cantidad > 0)
);

-- =====================================================
-- 7. DATOS DE PRUEBA (opcional)
-- =====================================================
-- Insertar algunos datos de ejemplo para probar

-- Artículos de ejemplo
INSERT INTO Articulo (descripcion, precio_venta, gastos_envio, tiempoPrep) VALUES
('Laptop Dell XPS 15', 1299.99, 15.00, 120),
('Mouse Logitech MX Master', 89.99, 5.00, 30),
('Teclado Mecánico Corsair', 149.99, 8.00, 45),
('Monitor Samsung 27"', 349.99, 20.00, 90);

-- Clientes de ejemplo
INSERT INTO Cliente (email, nombre, domicilio, nif, tipo_cliente) VALUES
('juan.perez@email.com', 'Juan Pérez', 'Calle Mayor 123, Madrid', '12345678A', 'Estándar'),
('maria.garcia@email.com', 'María García', 'Av. Diagonal 456, Barcelona', '87654321B', 'Premium'),
('pedro.lopez@email.com', 'Pedro López', 'Gran Vía 789, Valencia', '11223344C', 'Estándar');

-- Cliente Premium (solo María García)
INSERT INTO ClientePremium (cliente_email, cuota_anual, descuento_envio) VALUES
('maria.garcia@email.com', 30.00, 20);

-- Pedidos de ejemplo
INSERT INTO Pedido (cliente_email, articulo_codigo, cantidad, fecha_hora, estado) VALUES
('juan.perez@email.com', 1, 1, '2025-11-10 10:30:00', 'ENVIADO'),
('maria.garcia@email.com', 2, 2, '2025-11-11 14:15:00', 'PENDIENTE'),
('pedro.lopez@email.com', 3, 1, '2025-11-12 09:00:00', 'PENDIENTE');

-- =====================================================
-- RESUMEN DE LA ESTRUCTURA
-- =====================================================
-- TABLAS CREADAS:
-- 1. Articulo: Productos disponibles para la venta
-- 2. Cliente: Información básica de clientes (Estándar y Premium)
-- 3. ClientePremium: Información adicional solo para Premium (herencia)
-- 4. Pedido: Pedidos realizados por los clientes

-- RELACIONES:
-- - Cliente (1) ← (0..1) ClientePremium [Herencia/Especialización]
-- - Cliente (1) → (N) Pedido [Un cliente puede hacer muchos pedidos]
-- - Articulo (1) → (N) Pedido [Un artículo puede estar en muchos pedidos]
