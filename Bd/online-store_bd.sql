CREATE DATABASE  IF NOT EXISTS `online_store_bd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `online_store_bd`;
-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: online_store_bd
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ARTICULO`
--

DROP TABLE IF EXISTS `ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ARTICULO` (
  `codigo` varchar(10) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `gastos_envio` decimal(10,2) NOT NULL,
  `tiempo_preparacion` int NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICULO`
--

LOCK TABLES `ARTICULO` WRITE;
/*!40000 ALTER TABLE `ARTICULO` DISABLE KEYS */;
INSERT INTO `ARTICULO` VALUES ('A01','Auriculares Bluetooth',60.00,5.00,5),('A02','Teclado',80.00,5.00,5),('A03','Ratón inalámbrico',20.00,5.00,5),('A04','Monitor 24 pulgadas',120.00,10.00,10),('A10','MacBook Air',1000.00,25.00,30),('A11','Lampara de estudio',19.99,5.00,10);
/*!40000 ALTER TABLE `ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENTE`
--

DROP TABLE IF EXISTS `CLIENTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CLIENTE` (
  `nif` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `domicilio` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tipo` enum('Estandar','Premium') NOT NULL,
  `cuota_anual` decimal(10,2) DEFAULT NULL,
  `descuento_envio` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENTE`
--

LOCK TABLES `CLIENTE` WRITE;
/*!40000 ALTER TABLE `CLIENTE` DISABLE KEYS */;
INSERT INTO `CLIENTE` VALUES ('12345678A','Sergio Gomez Gutierrez','Calle Luna 3, Barcelona','sgomezg@uoc.edu','Estandar',NULL,NULL),('23456789C','Meritxell Moreno Moya','Calle Estrella 5, Barcelona','mmorenom@uoc.edu','Estandar',NULL,NULL),('3941234C','Ivan ','Avenida America 19 Granada','ivan@gmail.com','Premium',30.00,0.20),('87654321B','Ariadna Martínez Serra','Av. Sol 10, Barcelona','amartinezs@uoc.edu','Premium',30.00,0.20),('98765432D','Cèlia Trullà Estruch','Plaza Mayor 12, Barcelona','ctrullae@uoc.edu','Premium',30.00,0.20);
/*!40000 ALTER TABLE `CLIENTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PEDIDO`
--

DROP TABLE IF EXISTS `PEDIDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PEDIDO` (
  `numero_pedido` varchar(20) NOT NULL,
  `fecha_hora` timestamp NOT NULL,
  `estado` varchar(50) NOT NULL,
  `cantidad` int NOT NULL,
  `cliente_nif` varchar(10) NOT NULL,
  `articulo_codigo` varchar(10) NOT NULL,
  PRIMARY KEY (`numero_pedido`),
  KEY `cliente_nif` (`cliente_nif`),
  KEY `articulo_codigo` (`articulo_codigo`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`cliente_nif`) REFERENCES `CLIENTE` (`nif`) ON DELETE CASCADE,
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`articulo_codigo`) REFERENCES `ARTICULO` (`codigo`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PEDIDO`
--

LOCK TABLES `PEDIDO` WRITE;
/*!40000 ALTER TABLE `PEDIDO` DISABLE KEYS */;
/*!40000 ALTER TABLE `PEDIDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'online_store_bd'
--

--
-- Dumping routines for database 'online_store_bd'
--
/*!50003 DROP PROCEDURE IF EXISTS `SP_CARGAR_DATOS_PRUEBA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_CARGAR_DATOS_PRUEBA`()
BEGIN
    -- Inserción de Artículos
    INSERT INTO ARTICULO (codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion) VALUES
    ('A01', 'Auriculares Bluetooth', 60.00, 5.00, 5),
    ('A02', 'Teclado', 80.00, 5.00, 5),
    ('A03', 'Ratón inalámbrico', 20.00, 5.00, 5),
    ('A04', 'Monitor 24 pulgadas', 120.00, 10.00, 10);

    -- Inserción de Clientes Estándar y Premium
    INSERT INTO CLIENTE (nif, nombre, domicilio, email, tipo, cuota_anual, descuento_envio) VALUES
    ('12345678A', 'Sergio Gomez Gutierrez', 'Calle Luna 3, Barcelona', 'sgomezg@uoc.edu', 'Estandar', NULL, NULL),
    ('87654321B', 'Ariadna Martínez Serra', 'Av. Sol 10, Barcelona', 'amartinezs@uoc.edu', 'Premium', 30.00, 0.20),
    ('23456789C', 'Meritxell Moreno Moya', 'Calle Estrella 5, Barcelona', 'mmorenom@uoc.edu', 'Estandar', NULL, NULL),
    ('98765432D', 'Cèlia Trullà Estruch', 'Plaza Mayor 12, Barcelona', 'ctrullae@uoc.edu', 'Premium', 30.00, 0.20);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_ELIMINAR_PEDIDO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_ELIMINAR_PEDIDO`(
    IN p_num_pedido VARCHAR(20)
)
BEGIN
    START TRANSACTION;
    
    -- Se podría añadir aquí la lógica de validación de estado si fuera necesario,
    -- pero para simplificar, el controlador Java ya valida el tiempo de preparación.
    DELETE FROM PEDIDO WHERE numero_pedido = p_num_pedido;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_PEDIDO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_INSERTAR_PEDIDO`(
    IN p_num_pedido VARCHAR(20),
    IN p_cliente_nif VARCHAR(10),
    IN p_articulo_codigo VARCHAR(10),
    IN p_cantidad INT
)
BEGIN
    -- Iniciamos la transacción para asegurar atomicidad
    START TRANSACTION;
    
    INSERT INTO PEDIDO (numero_pedido, fecha_hora, estado, cantidad, cliente_nif, articulo_codigo)
    VALUES (p_num_pedido, NOW(), 'Pendiente de envío', p_cantidad, p_cliente_nif, p_articulo_codigo);
    
    -- Si todo fue bien, confirmamos (COMMIT) la operación
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-13 11:54:51
