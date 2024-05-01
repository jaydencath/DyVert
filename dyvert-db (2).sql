-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 30, 2024 at 06:20 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dyvert-db`
--

-- --------------------------------------------------------

--
-- Table structure for table `bucket`
--

CREATE TABLE `bucket` (
  `accountID` varchar(255) DEFAULT NULL,
  `contentID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bucket`
--

INSERT INTO `bucket` (`accountID`, `contentID`) VALUES
('101', 1),
('testUser', 8),
('testUser', 3),
('testUser', 10),
('testing', 5),
('testing', 7),
('testing', 2),
('testUser', 2),
('102', 9),
('102', 5),
('testing', 6),
('newtesting', 5),
('newtesting', 7),
('newtesting', 1),
('newtesting', 9),
('newtesting', 2),
('newtesting', 8),
('adminUser', 8),
('adminUser', 10);

-- --------------------------------------------------------

--
-- Table structure for table `bucket_api`
--

CREATE TABLE `bucket_api` (
  `accountID` varchar(255) DEFAULT NULL,
  `apiKey` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bucket_api`
--

INSERT INTO `bucket_api` (`accountID`, `apiKey`, `type`) VALUES
('testing', 661791, 'Movie'),
('testing', 1687, 'Movie'),
('testing', 4982, 'Movie'),
('testing', 42994, 'Movie'),
('testing', 785084, 'Movie'),
('testing', 506528, 'Movie'),
('testing', 388, 'Movie'),
('newtesting', 9398, 'Movie'),
('newtesting', 91962, 'TV Show'),
('newtesting', 93005, 'TV Show'),
('newtesting', 90795, 'TV Show'),
('newtesting', 15196, 'Movie'),
('newtesting', 9315, 'Movie'),
('newtesting', 399566, 'Movie'),
('newtesting', 345934, 'Movie'),
('adminUser', 1085218, 'Movie'),
('adminUser', 129, 'Movie'),
('adminUser', 587807, 'Movie'),
('adminUser', 232672, 'Movie'),
('adminUser', 65854, 'TV Show'),
('adminUser', 21552, 'TV Show');

-- --------------------------------------------------------

--
-- Table structure for table `card_data`
--

CREATE TABLE `card_data` (
  `cardid` bigint(20) NOT NULL,
  `userID` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `synopsis` varchar(255) DEFAULT NULL,
  `genres` varchar(255) DEFAULT NULL,
  `imagepath` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `reviews` int(11) DEFAULT NULL,
  `reviewed` bit(1) NOT NULL,
  `flagged` bit(1) NOT NULL,
  `finished` bit(1) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `card_data`
--

INSERT INTO `card_data` (`cardid`, `userID`, `title`, `synopsis`, `genres`, `imagepath`, `type`, `reviews`, `reviewed`, `flagged`, `finished`, `image_path`) VALUES
(1, '101', 'Pokemon', 'Joined by his partner Pokémon Pikachu and a rotating cast of human characters, Ash goes on a journey to become a \"Pokémon Master\", traveling through the various regions of the Pokémon world and competing in various Pokémon-battling tournaments known as th', 'Adventure Fantasy', 'pokemon.jpg', 'TV Show', 0, b'1', b'0', b'1', NULL),
(2, '102', 'Avatar: The Last Airbender', 'The world is divided into four nations -- the Water Tribe, the Earth Kingdom, the Fire Nation and and the Air Nomads -- each represented by a natural element for which the nation is named.', 'Adventure', 'avatar.jpeg', 'TV Show', 0, b'1', b'1', b'1', NULL),
(3, '101', 'The Giver', 'The Giver is a 1993 American young adult dystopian novel written by Lois Lowry, set in a society which at first appears to be utopian but is revealed to be dystopian as the story progresses.', 'Science Fiction', 'the-giver.jpg', 'Book', 0, b'1', b'0', b'1', NULL),
(4, '102', 'Adventure Time', 'Twelve- year-old Finn battles evil in the Land of Ooo. Assisted by his magical dog, Jake, Finn roams the Land of Ooo righting wrongs and battling evil.', 'Cartoon, Action', 'adventure-time.jpg', 'TV Show', 0, b'1', b'0', b'1', NULL),
(5, '101', 'Dune', 'Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people.', 'Science Fiction', 'Dune.jpg', 'Movie', 0, b'1', b'0', b'1', NULL),
(6, '102', 'Parks and Recreation', 'Leslie Knope, a midlevel bureaucrat in an Indiana Parks and Recreation Department, hopes to beautify her town (and boost her own career) by helping local nurse Ann Perkins turn an abandoned construction site into a community park', 'Comedy', 'parks-and-rec.jpeg', 'TV Show', 0, b'1', b'0', b'1', NULL),
(7, '102', 'Ted Lasso', 'An American football coach is hired to manage a British soccer team; what he lacks in knowledge, he makes up for in optimism, determination and biscuits.', 'Comedy', 'ted-lasso.jpg', 'TV Show', 0, b'1', b'0', b'1', NULL),
(8, '102', 'Late Night with the Devil', 'In 1977 a live television broadcast goes horribly wrong, unleashing evil into the nation\'s living rooms.', 'Horror', 'late-night-with-the-devil.jpeg', 'Movie', 0, b'1', b'0', b'1', NULL),
(9, '101', 'Terminator', 'Disguised as a human, a cyborg assassin known as a Terminator travels from 2029 to 1984 to kill Sarah Connor.', 'Science Fiction', 'terminator.jpg', 'Movie', 0, b'1', b'0', b'1', NULL),
(10, '101', 'The Martian', 'The Martian by Andy Weir is a book that centers around the journey of Mark Watney. The book is set in the future where humans can travel to Mars via spaceship.', 'Science Fiction', 'the-martian.jpg', 'Book', 0, b'1', b'0', b'1', NULL),
(20, '103', 'Test Card', 'This is a test card', 'Comedy,Adventure', NULL, 'Movie', 0, b'1', b'0', b'1', NULL),
(21, 'adminUser', 'Testing the Card feature', 'This is a synopsis where I am testing the card.', 'Indie', NULL, 'TV Show', 0, b'0', b'0', b'1', NULL),
(22, 'adminUser', 'Trying out the picture in local storage', 'This is a description.', 'Adventure', NULL, 'Movie', 0, b'0', b'0', b'1', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `seen`
--

CREATE TABLE `seen` (
  `accountID` varchar(255) DEFAULT NULL,
  `contentID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seen`
--

INSERT INTO `seen` (`accountID`, `contentID`) VALUES
('101', 1),
('101', 2),
('testUser', 9),
('testUser', 7),
('testUser', 8),
('testUser', 5),
('testUser', 6),
('testUser', 3),
('testUser', 10),
('testing', 9),
('testing', 1),
('testing', 5),
('testing', 7),
('testing', 2),
('testUser', 2),
('testUser', 1),
('102', 9),
('102', 2),
('102', 5),
('testing', 6),
('testing', 10),
('newtesting', 5),
('newtesting', 7),
('newtesting', 1),
('newtesting', 9),
('newtesting', 2),
('newtesting', 8),
('adminUser', 8),
('adminUser', 10);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `accountID` varchar(255) NOT NULL,
  `accountType` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `last_card_viewed` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`accountID`, `accountType`, `account_type`, `last_card_viewed`, `password`) VALUES
('101', 'Creator', NULL, 0, 'password'),
('102', 'Creator', NULL, 0, 'password'),
('103', 'Creator', NULL, 0, 'password'),
('adminUser', 'Admin', NULL, 0, 'password'),
('creatorUser', 'Creator', NULL, 0, 'password'),
('newtesting', 'User', NULL, 0, 'password'),
('sergio_test', 'Creator', NULL, 0, 'password'),
('testing', 'User', NULL, 0, 'password'),
('testUser', 'User', NULL, 0, 'password'),
('user', 'User', NULL, 0, 'password');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bucket`
--
ALTER TABLE `bucket`
  ADD KEY `accountID` (`accountID`),
  ADD KEY `contentID` (`contentID`);

--
-- Indexes for table `bucket_api`
--
ALTER TABLE `bucket_api`
  ADD KEY `accountID` (`accountID`);

--
-- Indexes for table `card_data`
--
ALTER TABLE `card_data`
  ADD PRIMARY KEY (`cardid`),
  ADD UNIQUE KEY `cardID` (`cardid`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `seen`
--
ALTER TABLE `seen`
  ADD KEY `accountID` (`accountID`),
  ADD KEY `contentID` (`contentID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`accountID`),
  ADD UNIQUE KEY `accountID` (`accountID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `card_data`
--
ALTER TABLE `card_data`
  MODIFY `cardid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bucket`
--
ALTER TABLE `bucket`
  ADD CONSTRAINT `bucket_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `users` (`accountID`),
  ADD CONSTRAINT `bucket_ibfk_2` FOREIGN KEY (`contentID`) REFERENCES `card_data` (`cardid`);

--
-- Constraints for table `bucket_api`
--
ALTER TABLE `bucket_api`
  ADD CONSTRAINT `bucket_api_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `users` (`accountID`);

--
-- Constraints for table `card_data`
--
ALTER TABLE `card_data`
  ADD CONSTRAINT `card_data_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`accountID`);

--
-- Constraints for table `seen`
--
ALTER TABLE `seen`
  ADD CONSTRAINT `seen_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `users` (`accountID`),
  ADD CONSTRAINT `seen_ibfk_2` FOREIGN KEY (`contentID`) REFERENCES `card_data` (`cardid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
