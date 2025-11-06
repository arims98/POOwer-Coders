-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: online_store_db
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
-- Table structure for table `Articulo`
--

DROP TABLE IF EXISTS `Articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Articulo` (
  `codigo_articulo` int unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `gastos_envio` decimal(10,2) NOT NULL,
  `tiempoPrep` smallint unsigned NOT NULL,
  PRIMARY KEY (`codigo_articulo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Articulo`
--

LOCK TABLES `Articulo` WRITE;
/*!40000 ALTER TABLE `Articulo` DISABLE KEYS */;
INSERT INTO `Articulo` VALUES (1,'Ratón Óptico Ergonómico',15.50,2.00,5),(2,'Monitor Ultra-HD 27\"',280.00,15.00,60),(3,'Teclado Mecánico RGB',89.99,4.50,120),(4,'Ratón inalambrico',19.90,2.00,5),(5,'Monitor Oled',340.00,15.00,60),(6,'Teclado Inalambrico',99.99,4.50,120),(7,'Cascos inalambricos',15.50,2.00,5),(8,'Monitor 4K 27\"',280.00,15.00,60),(9,'Teclado Mediano',89.99,4.50,120);
/*!40000 ALTER TABLE `Articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cliente`
--

DROP TABLE IF EXISTS `Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cliente` (
  `num_cliente` int unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `domicilio` varchar(225) DEFAULT NULL,
  `nif` varchar(20) NOT NULL,
  `tipo_cliente` enum('Estándar','Premium') NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `num_cliente` (`num_cliente`),
  UNIQUE KEY `nif` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cliente`
--

LOCK TABLES `Cliente` WRITE;
/*!40000 ALTER TABLE `Cliente` DISABLE KEYS */;
INSERT INTO `Cliente` VALUES (4,'ari@tienda.es','Ari','Paseo Olivos 8','40777888A','Premium'),(2,'celia@tienda.es','Celia','Avenida Principal 45','40333444C','Premium'),(3,'meri@tienda.es','Meri','Ronda Mar 2','40555666M','Estándar'),(1,'sergio@tienda.es','Sergio','Calle del Sol 10','40111222S','Estándar');
/*!40000 ALTER TABLE `Cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClientePremium`
--

DROP TABLE IF EXISTS `ClientePremium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ClientePremium` (
  `cliente_email` varchar(100) NOT NULL,
  `cuota_anual` decimal(10,2) DEFAULT '30.00',
  `descuento_envio` smallint DEFAULT '20',
  PRIMARY KEY (`cliente_email`),
  CONSTRAINT `fk_premium_cliente` FOREIGN KEY (`cliente_email`) REFERENCES `Cliente` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClientePremium`
--

LOCK TABLES `ClientePremium` WRITE;
/*!40000 ALTER TABLE `ClientePremium` DISABLE KEYS */;
INSERT INTO `ClientePremium` VALUES ('ari@tienda.es',30.00,20),('celia@tienda.es',30.00,20);
/*!40000 ALTER TABLE `ClientePremium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pedido`
--

DROP TABLE IF EXISTS `Pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pedido` (
  `num_pedido` int unsigned NOT NULL AUTO_INCREMENT,
  `cliente_email` varchar(50) NOT NULL,
  `articulo_codigo` int unsigned NOT NULL,
  `cantidad` smallint unsigned NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `precio_total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`num_pedido`),
  KEY `fk_pedido_cliente` (`cliente_email`),
  KEY `fk_pedido_articulo` (`articulo_codigo`),
  CONSTRAINT `fk_pedido_articulo` FOREIGN KEY (`articulo_codigo`) REFERENCES `Articulo` (`codigo_articulo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_pedido_cliente` FOREIGN KEY (`cliente_email`) REFERENCES `Cliente` (`email`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pedido`
--

LOCK TABLES `Pedido` WRITE;
/*!40000 ALTER TABLE `Pedido` DISABLE KEYS */;
INSERT INTO `Pedido` VALUES (3,'sergio@tienda.es',1,5,'2025-10-29 15:30:00',79.50),(4,'celia@tienda.es',2,1,'2025-10-29 16:00:00',292.00),(5,'ari@tienda.es',5,5,'2025-11-29 17:00:00',1000.00),(6,'sergio@tienda.es',3,1,'2025-10-20 09:00:00',89.99),(7,'meri@tienda.es',1,2,'2025-10-06 12:30:00',31.00);
/*!40000 ALTER TABLE `Pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-29 17:32:30
