-- =====================================================
-- PROCEDIMIENTOS ALMACENADOS - TIENDA ONLINE
-- =====================================================
-- Los procedimientos almacenados son "funciones" guardadas en la BD
-- Permiten ejecutar lógica compleja en el servidor de BD
-- Ventajas: rendimiento, seguridad, reutilización

USE tienda;

-- Eliminar procedimientos si existen (para poder recrearlos)
DROP PROCEDURE IF EXISTS sp_agregar_pedido_completo;
DROP PROCEDURE IF EXISTS sp_calcular_total_pedido;
DROP PROCEDURE IF EXISTS sp_obtener_estadisticas_cliente;

-- =====================================================
-- PROCEDIMIENTO 1: AGREGAR PEDIDO COMPLETO
-- =====================================================
-- ¿Qué hace?: Crea un pedido completo con validaciones
-- ¿Por qué es útil?: Valida que el cliente y artículo existan
--                    antes de crear el pedido

DELIMITER $$

CREATE PROCEDURE sp_agregar_pedido_completo(
    IN p_cliente_email VARCHAR(100),     -- Email del cliente
    IN p_articulo_codigo INT,            -- Código del artículo
    IN p_cantidad INT,                   -- Cantidad a pedir
    OUT p_num_pedido INT,                -- Devuelve el número de pedido creado
    OUT p_mensaje VARCHAR(200)           -- Devuelve mensaje de éxito/error
)
BEGIN
    -- Variables locales
    DECLARE v_cliente_existe INT;
    DECLARE v_articulo_existe INT;
    DECLARE v_tipo_cliente VARCHAR(20);
    
    -- Inicializamos los valores de salida
    SET p_num_pedido = 0;
    SET p_mensaje = '';
    
    -- PASO 1: Verificar que el cliente existe
    SELECT COUNT(*) INTO v_cliente_existe 
    FROM Cliente 
    WHERE email = p_cliente_email;
    
    IF v_cliente_existe = 0 THEN
        SET p_mensaje = 'ERROR: El cliente no existe';
        -- ROLLBACK; -- Si estuviera en una transacción
    ELSE
        -- PASO 2: Verificar que el artículo existe
        SELECT COUNT(*) INTO v_articulo_existe 
        FROM Articulo 
        WHERE codigo_articulo = p_articulo_codigo;
        
        IF v_articulo_existe = 0 THEN
            SET p_mensaje = 'ERROR: El artículo no existe';
        ELSE
            -- PASO 3: Verificar que la cantidad es válida
            IF p_cantidad <= 0 THEN
                SET p_mensaje = 'ERROR: La cantidad debe ser mayor a 0';
            ELSE
                -- PASO 4: Todo OK, crear el pedido
                INSERT INTO Pedido (cliente_email, articulo_codigo, cantidad, fecha_hora, estado)
                VALUES (p_cliente_email, p_articulo_codigo, p_cantidad, NOW(), 'PENDIENTE');
                
                -- Obtener el ID del pedido recién creado
                SET p_num_pedido = LAST_INSERT_ID();
                
                -- Obtener el tipo de cliente
                SELECT tipo_cliente INTO v_tipo_cliente
                FROM Cliente
                WHERE email = p_cliente_email;
                
                SET p_mensaje = CONCAT('Pedido creado exitosamente. Número: ', p_num_pedido, 
                                     '. Cliente tipo: ', v_tipo_cliente);
            END IF;
        END IF;
    END IF;
    
END$$

DELIMITER ;

-- =====================================================
-- PROCEDIMIENTO 2: CALCULAR TOTAL DEL PEDIDO
-- =====================================================
-- ¿Qué hace?: Calcula el precio total de un pedido
--             considerando: precio + gastos envío - descuento premium
-- ¿Por qué es útil?: Centraliza la lógica de cálculo de precios

DELIMITER $$

CREATE PROCEDURE sp_calcular_total_pedido(
    IN p_num_pedido INT,                 -- Número del pedido
    OUT p_subtotal DECIMAL(10,2),        -- Precio sin envío
    OUT p_gastos_envio DECIMAL(10,2),    -- Gastos de envío
    OUT p_descuento DECIMAL(10,2),       -- Descuento aplicado
    OUT p_total DECIMAL(10,2),           -- Total final
    OUT p_mensaje VARCHAR(200)           -- Mensaje informativo
)
BEGIN
    -- Variables locales
    DECLARE v_pedido_existe INT;
    DECLARE v_precio_articulo DECIMAL(10,2);
    DECLARE v_gastos_articulo DECIMAL(10,2);
    DECLARE v_cantidad INT;
    DECLARE v_cliente_email VARCHAR(100);
    DECLARE v_es_premium INT;
    DECLARE v_descuento_premium INT DEFAULT 0;
    
    -- Inicializar valores de salida
    SET p_subtotal = 0;
    SET p_gastos_envio = 0;
    SET p_descuento = 0;
    SET p_total = 0;
    SET p_mensaje = '';
    
    -- PASO 1: Verificar que el pedido existe
    SELECT COUNT(*) INTO v_pedido_existe
    FROM Pedido
    WHERE num_pedido = p_num_pedido;
    
    IF v_pedido_existe = 0 THEN
        SET p_mensaje = 'ERROR: El pedido no existe';
    ELSE
        -- PASO 2: Obtener datos del pedido
        SELECT 
            p.cantidad,
            p.cliente_email,
            a.precio_venta,
            a.gastos_envio
        INTO 
            v_cantidad,
            v_cliente_email,
            v_precio_articulo,
            v_gastos_articulo
        FROM Pedido p
        INNER JOIN Articulo a ON p.articulo_codigo = a.codigo_articulo
        WHERE p.num_pedido = p_num_pedido;
        
        -- PASO 3: Calcular subtotal (precio * cantidad)
        SET p_subtotal = v_precio_articulo * v_cantidad;
        
        -- PASO 4: Verificar si es cliente Premium
        SELECT COUNT(*) INTO v_es_premium
        FROM ClientePremium
        WHERE cliente_email = v_cliente_email;
        
        -- PASO 5: Calcular gastos de envío con descuento si es Premium
        IF v_es_premium > 0 THEN
            -- Obtener el porcentaje de descuento
            SELECT descuento_envio INTO v_descuento_premium
            FROM ClientePremium
            WHERE cliente_email = v_cliente_email;
            
            -- Calcular descuento (ej: 20% de 10€ = 2€)
            SET p_descuento = (v_gastos_articulo * v_descuento_premium) / 100;
            SET p_gastos_envio = v_gastos_articulo - p_descuento;
            
            SET p_mensaje = CONCAT('Cliente Premium con ', v_descuento_premium, '% de descuento en envío');
        ELSE
            -- Cliente estándar: sin descuento
            SET p_gastos_envio = v_gastos_articulo;
            SET p_mensaje = 'Cliente Estándar: sin descuento en envío';
        END IF;
        
        -- PASO 6: Calcular total
        SET p_total = p_subtotal + p_gastos_envio;
    END IF;
    
END$$

DELIMITER ;

-- =====================================================
-- PROCEDIMIENTO 3: OBTENER ESTADÍSTICAS DE CLIENTE
-- =====================================================
-- ¿Qué hace?: Obtiene estadísticas de un cliente
--             (total de pedidos, gasto total, etc.)
-- ¿Por qué es útil?: Para reportes y análisis

DELIMITER $$

CREATE PROCEDURE sp_obtener_estadisticas_cliente(
    IN p_cliente_email VARCHAR(100),
    OUT p_total_pedidos INT,
    OUT p_gasto_total DECIMAL(10,2),
    OUT p_tipo_cliente VARCHAR(20),
    OUT p_mensaje VARCHAR(200)
)
BEGIN
    DECLARE v_cliente_existe INT;
    
    -- Inicializar
    SET p_total_pedidos = 0;
    SET p_gasto_total = 0;
    SET p_tipo_cliente = '';
    SET p_mensaje = '';
    
    -- Verificar que el cliente existe
    SELECT COUNT(*) INTO v_cliente_existe
    FROM Cliente
    WHERE email = p_cliente_email;
    
    IF v_cliente_existe = 0 THEN
        SET p_mensaje = 'ERROR: El cliente no existe';
    ELSE
        -- Obtener tipo de cliente
        SELECT tipo_cliente INTO p_tipo_cliente
        FROM Cliente
        WHERE email = p_cliente_email;
        
        -- Contar pedidos
        SELECT COUNT(*) INTO p_total_pedidos
        FROM Pedido
        WHERE cliente_email = p_cliente_email;
        
        -- Calcular gasto total (precio * cantidad)
        SELECT COALESCE(SUM(a.precio_venta * p.cantidad), 0) INTO p_gasto_total
        FROM Pedido p
        INNER JOIN Articulo a ON p.articulo_codigo = a.codigo_articulo
        WHERE p.cliente_email = p_cliente_email;
        
        SET p_mensaje = CONCAT('Estadísticas del cliente: ', p_total_pedidos, ' pedidos, ',
                              'gasto total: ', p_gasto_total, '€');
    END IF;
    
END$$

DELIMITER ;

-- =====================================================
-- EJEMPLOS DE USO
-- =====================================================

-- EJEMPLO 1: Crear un pedido completo
/*
CALL sp_agregar_pedido_completo(
    'juan.perez@email.com',  -- Email del cliente
    1,                        -- Código del artículo
    2,                        -- Cantidad
    @num_pedido,             -- Variable para recibir el número de pedido
    @mensaje                 -- Variable para recibir el mensaje
);
SELECT @num_pedido AS 'Número de Pedido', @mensaje AS 'Mensaje';
*/

-- EJEMPLO 2: Calcular total de un pedido
/*
CALL sp_calcular_total_pedido(
    1,                        -- Número de pedido
    @subtotal,
    @gastos_envio,
    @descuento,
    @total,
    @mensaje
);
SELECT @subtotal AS 'Subtotal', @gastos_envio AS 'Gastos Envío', 
       @descuento AS 'Descuento', @total AS 'Total', @mensaje AS 'Mensaje';
*/

-- EJEMPLO 3: Estadísticas de cliente
/*
CALL sp_obtener_estadisticas_cliente(
    'maria.garcia@email.com',
    @total_pedidos,
    @gasto_total,
    @tipo_cliente,
    @mensaje
);
SELECT @total_pedidos AS 'Total Pedidos', @gasto_total AS 'Gasto Total', 
       @tipo_cliente AS 'Tipo Cliente', @mensaje AS 'Mensaje';
*/

-- =====================================================
-- RESUMEN DE LOS PROCEDIMIENTOS
-- =====================================================
-- 1. sp_agregar_pedido_completo: Crea pedidos con validaciones
-- 2. sp_calcular_total_pedido: Calcula el total de un pedido
-- 3. sp_obtener_estadisticas_cliente: Obtiene estadísticas de compra
--
-- BENEFICIOS:
-- - Lógica centralizada en la BD
-- - Mejor rendimiento (menos viajes red)
-- - Más seguridad (menos código SQL en Java)
-- - Reutilización entre diferentes aplicaciones
