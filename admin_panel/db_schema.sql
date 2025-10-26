--
-- Database: `vpn_panel`
--

-- --------------------------------------------------------

--
-- Table structure for table `servers`
--

CREATE TABLE `servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `FLAG` varchar(255) NOT NULL,
  `ServerIP` varchar(255) NOT NULL,
  `ServerPort` varchar(255) NOT NULL,
  `SSLPort` varchar(255) NOT NULL,
  `ProxyPort` varchar(255) NOT NULL,
  `ServerUser` varchar(255) NOT NULL,
  `ServerPass` varchar(255) NOT NULL,
  `Sfrep` varchar(255) NOT NULL,
  `sInfo` varchar(255) NOT NULL,
  `Slowchave` varchar(255) NOT NULL,
  `Nameserver` varchar(255) NOT NULL,
  `servermessage` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payloads`
--

CREATE TABLE `payloads` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Payload` text NOT NULL,
  `SNI` varchar(255) NOT NULL,
  `pInfo` varchar(255) NOT NULL,
  `Slowdns` varchar(255) NOT NULL,
  `WebProxy` varchar(255) NOT NULL,
  `WebPort` varchar(255) NOT NULL,
  `isSSL` tinyint(1) NOT NULL,
  `isPayloadSSL` tinyint(1) NOT NULL,
  `isSlow` tinyint(1) NOT NULL,
  `isHatok` tinyint(1) NOT NULL,
  `isInject` tinyint(1) NOT NULL,
  `isDirect` tinyint(1) NOT NULL,
  `isWeb` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Version` varchar(255) NOT NULL,
  `ReleaseNotes` text NOT NULL,
  `ReleaseNotes1` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `Version`, `ReleaseNotes`, `ReleaseNotes1`) VALUES
(1, '1.0', 'Initial release', 'Welcome!');