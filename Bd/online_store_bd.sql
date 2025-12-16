CREATE DATABASE IF NOT EXISTS online_store_bd;
USE online_store_bd;

-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: online_store_bd
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `codigo` varchar(10) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio_venta` double DEFAULT NULL,
  `gastos_envio` double DEFAULT NULL,
  `tiempo_preparacion` int NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` VALUES ('A01','Auriculares Bluetooth',60,5,5),('A02','Teclado',80,5,5),('A03','Ratón inalámbrico',20,5,5),('A04','Monitor 24 pulgadas',120,10,10),('A10','MacBook Air',1000,25,30),('A11','Lampara de estudio',19.99,5,10),('A12','M�vil',50,5,7),('A13','Tablet Smasung',200,5,7),('A14','Ordeandor Sobremesa',500,40,50),('A15','Nevera LG',400,40,30);
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `nif` varchar(10) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `domicilio` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tipo` varchar(31) NOT NULL,
  `cuota_anual` decimal(10,2) DEFAULT NULL,
  `descuento_envio` decimal(5,2) DEFAULT NULL,
  `cuotaAnual` int DEFAULT NULL,
  `descuentoEnvio` double DEFAULT NULL,
  PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('12345678A','Sergio Gomez Gutierrez','Calle Luna 3, Barcelona','sgomezg@uoc.edu','Estandar',NULL,NULL,NULL,NULL),('23456789C','Meritxell Moreno Moya','Calle Estrella 5, Barcelona','mmorenom@uoc.edu','Estandar',NULL,NULL,NULL,NULL),('3941234C','Ivan ','Avenida America 19 Granada','ivan@gmail.com','Premium',30.00,0.20,NULL,NULL),('55555555V','Venonat','Avenida Veneno','venonat@uoc.edu','Estandar',NULL,NULL,NULL,NULL),('77777777P','Juan Palomo','Calle Guiso','juan@uoc.edu','Estandar',NULL,NULL,NULL,NULL),('87654321B','Ariadna Martínez Serra','Av. Sol 10, Barcelona','amartinezs@uoc.edu','Premium',30.00,0.20,NULL,NULL),('88542996P','Hana','Calle Plata N.0','hana@uoc.edu','Premium',NULL,NULL,NULL,NULL),('98765432D','Cèlia Trullà Estruch','Plaza Mayor 12, Barcelona','ctrullae@uoc.edu','Premium',30.00,0.20,NULL,NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `numero_pedido` varchar(255) NOT NULL,
  `fecha_hora` datetime(6) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `cantidad` int NOT NULL,
  `cliente_nif` varchar(10) NOT NULL,
  `articulo_codigo` varchar(10) NOT NULL,
  PRIMARY KEY (`numero_pedido`),
  KEY `cliente_nif` (`cliente_nif`),
  KEY `articulo_codigo` (`articulo_codigo`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`cliente_nif`) REFERENCES `cliente` (`nif`) ON DELETE CASCADE,
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`articulo_codigo`) REFERENCES `articulo` (`codigo`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES ('P01','2025-11-24 13:14:01.000000','Enviado',2,'23456789C','A01'),('P02','2025-11-29 19:52:20.811947','Pendiente de envío',7,'88542996P','A10'),('P03','2025-12-01 11:05:56.768006','Pendiente de envío',2,'98765432D','A10');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-01 19:02:28
