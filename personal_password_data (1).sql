-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Час створення: Чрв 01 2022 р., 16:02
-- Версія сервера: 5.7.31
-- Версія PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `personal_password_data`
--

-- --------------------------------------------------------

--
-- Структура таблиці `applications`
--

DROP TABLE IF EXISTS `applications`;
CREATE TABLE IF NOT EXISTS `applications` (
  `name_application` varchar(32) NOT NULL,
  PRIMARY KEY (`name_application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп даних таблиці `applications`
--

INSERT INTO `applications` (`name_application`) VALUES
('Ігри '),
('Додатки '),
('Пошта '),
('Робота'),
('Сайти '),
('Фінанси ');

-- --------------------------------------------------------

--
-- Структура таблиці `data_password`
--

DROP TABLE IF EXISTS `data_password`;
CREATE TABLE IF NOT EXISTS `data_password` (
  `id_password` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `website_or_app` varchar(128) NOT NULL,
  `login` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name_type` varchar(16) NOT NULL,
  `name_applications` varchar(32) NOT NULL,
  PRIMARY KEY (`id_password`),
  KEY `id_user` (`id_user`),
  KEY `name_type` (`name_type`),
  KEY `name_applications` (`name_applications`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Структура таблиці `info_user`
--

DROP TABLE IF EXISTS `info_user`;
CREATE TABLE IF NOT EXISTS `info_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `PIB` varchar(32) NOT NULL,
  `address` varchar(64) NOT NULL,
  `number_phone` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_user_2` (`id_user`),
  UNIQUE KEY `id_user_3` (`id_user`),
  KEY `id_user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--
-- Дамп даних таблиці `info_user`
--

INSERT INTO `info_user` (`id`, `id_user`, `PIB`, `address`, `number_phone`) VALUES
(9, 8, 'Бабич Євген Васильович', 'м.Хуст', 975067896);

-- --------------------------------------------------------

--
-- Структура таблиці `type_pass`
--

DROP TABLE IF EXISTS `type_pass`;
CREATE TABLE IF NOT EXISTS `type_pass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_pass` varchar(16) NOT NULL,
  PRIMARY KEY (`type_pass`) USING BTREE,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Дамп даних таблиці `type_pass`
--

INSERT INTO `type_pass` (`id`, `type_pass`) VALUES
(1, 'Важливі'),
(2, 'не важливі');

-- --------------------------------------------------------

--
-- Структура таблиці `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `login` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

--
-- Дамп даних таблиці `users`
--

INSERT INTO `users` (`id_user`, `username`, `login`, `password`) VALUES
(3, '1231', '311321', '111'),
(6, '3333', '3333', '43814346e21444aaf4f70841bf7ed5ae93f55a9d'),
(7, 'Євген', 'evgen', '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
(8, '3', '3', '77de68daecd823babbb58edb1c8e14d7106e83bb'),
(13, '333333', '333333333', 'f56d6351aa71cff0debea014d13525e42036187a'),
(15, '1111', '1', '356a192b7913b04c54574d18c28d46e6395428ab'),
(16, '111', '1111111', '88ea39439e74fa27c09a4fc0bc8ebe6d00978392'),
(17, '3123', '3123', 'a642a77abd7d4f51bf9226ceaf891fcbb5b299b8'),
(19, 'Євген', 'kold223', '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
(20, '1111', '111', '88ea39439e74fa27c09a4fc0bc8ebe6d00978392');

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `data_password`
--
ALTER TABLE `data_password`
  ADD CONSTRAINT `data_password_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `data_password_ibfk_2` FOREIGN KEY (`name_type`) REFERENCES `type_pass` (`type_pass`),
  ADD CONSTRAINT `data_password_ibfk_3` FOREIGN KEY (`name_applications`) REFERENCES `applications` (`name_application`);

--
-- Обмеження зовнішнього ключа таблиці `info_user`
--
ALTER TABLE `info_user`
  ADD CONSTRAINT `info_user_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
