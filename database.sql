-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.26 - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour supplychainy
CREATE DATABASE IF NOT EXISTS `supplychainy` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `supplychainy`;

-- Listage de la structure de la table supplychainy. address
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.address : ~3 rows (environ)
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`id`, `city`, `country`, `phone_number`, `street`) VALUES
	(1, '2815408162', 'USA', '0666776529', '3317 BIRD SPRING LANE HUMBLE, TX 77338'),
	(3, 'city', 'UK', '99888777', 'street'),
	(4, 'london', 'uk', '2815408162', '3317 BIRD ');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.customers : ~3 rows (environ)
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id`, `name`) VALUES
	(2, 'Adidas'),
	(3, 'Nike'),
	(4, 'Puma');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `price_total` double DEFAULT NULL,
  `products_total` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `address_id` bigint(20) NOT NULL,
  `manufacturer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5464gxwc32ongdvka2rtvw96` (`address_id`),
  KEY `FKdxaeiqthrjqoq3kd11aamfmdj` (`manufacturer_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKdxaeiqthrjqoq3kd11aamfmdj` FOREIGN KEY (`manufacturer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.orders : ~2 rows (environ)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`id`, `created_at`, `price_total`, `products_total`, `status`, `address_id`, `manufacturer_id`, `user_id`) VALUES
	(1, '2022-04-15 00:13:57.315000', NULL, NULL, 'IN_PROGRESS', 1, 6, 7),
	(2, '2022-04-15 00:14:07.870000', NULL, NULL, 'IN_PROGRESS', 1, 6, 7);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. order_items
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.order_items : ~3 rows (environ)
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` (`id`, `quantity`, `order_id`, `product_id`) VALUES
	(1, 1, 1, 3),
	(2, 1, 2, 1),
	(3, 1, 2, 2);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. products
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK29w1glmsx19fyn0ts34ak8pc5` (`customer_id`),
  CONSTRAINT `FK29w1glmsx19fyn0ts34ak8pc5` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.products : ~5 rows (environ)
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`id`, `image`, `price`, `quantity`, `title`, `customer_id`) VALUES
	(1, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/430729c77a5d4a9eaefcad6b007a4b62_9366/Chaussure_ZX_5K_BOOST_Beige_GX6348_01_standard.jpg', 40, 199, 'Shoes', 2),
	(2, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/430729c77a5d4a9eaefcad6b007a4b62_9366/Chaussure_ZX_5K_BOOST_Beige_GX6348_01_standard.jpg', 30, 10, 'Shoes', 2),
	(3, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/4546acbdbcdf4cd0a3d9add2010babdd_9366/T-shirt_adidas_SPRT_Outline_Logo_Rose_HE4681_21_model.jpg', 6, 9, 'Tshirt', 2),
	(4, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/4546acbdbcdf4cd0a3d9add2010babdd_9366/T-shirt_adidas_SPRT_Outline_Logo_Rose_HE4681_21_model.jpg', 9, 10, 'Tshirt', 2),
	(5, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/4546acbdbcdf4cd0a3d9add2010babdd_9366/T-shirt_adidas_SPRT_Outline_Logo_Rose_HE4681_21_model.jpg', 2, 9, 'Tshirt', 2);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.role : ~3 rows (environ)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `description`, `role_name`) VALUES
	(1, 'role admin', 'ROLE_ADMIN'),
	(2, 'role manufacturer', 'ROLE_MANUFACTURER'),
	(3, 'role client', 'ROLE_CLIENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.user : ~5 rows (environ)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `enabled`, `first_name`, `last_name`, `password`, `username`, `email`) VALUES
	(1, b'1', 'admin', 'admin', '$2y$10$tvt3JlsewFQJ6.drB2wzIewRey81MxkSEGN2iqLzeaZp0tV/aH.ia', NULL, 'admin@gmail.com'),
	(4, b'0', 'test', 'test', '123', NULL, 'test@gmail.com'),
	(5, b'0', 'STEPHANIE', 'HALL', '123', NULL, 'STEPHANIEVHALL@DAYREP.COM'),
	(6, b'1', 'biya', 'bik', '$2a$10$SjBS/NQ3zY7emYwktV/Vx.PfT.ghPvuyNtBJLfSxkm287Nctf2h66', NULL, 'bik@gmail.com'),
	(7, b'1', 'Adidas', 'Adidas', '$2a$10$SjBS/NQ3zY7emYwktV/Vx.PfT.ghPvuyNtBJLfSxkm287Nctf2h66', NULL, 'adidas@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Listage de la structure de la table supplychainy. user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Listage des données de la table supplychainy.user_role : ~5 rows (environ)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(1, 2),
	(6, 2),
	(7, 3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
