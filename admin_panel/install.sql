CREATE TABLE `servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `FLAG` varchar(255) DEFAULT NULL,
  `ServerIP` varchar(255) NOT NULL,
  `ServerPort` varchar(255) NOT NULL,
  `SSLPort` varchar(255) DEFAULT NULL,
  `ProxyPort` varchar(255) DEFAULT NULL,
  `ServerUser` varchar(255) NOT NULL,
  `ServerPass` varchar(255) NOT NULL,
  `Sfrep` text,
  `sInfo` text,
  `Slowchave` text,
  `Nameserver` varchar(255) DEFAULT NULL,
  `servermessage` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `networks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Payload` text,
  `SNI` varchar(255) DEFAULT NULL,
  `pInfo` text,
  `Slowdns` varchar(255) DEFAULT NULL,
  `WebProxy` varchar(255) DEFAULT NULL,
  `WebPort` varchar(255) DEFAULT NULL,
  `isSSL` varchar(10) DEFAULT 'false',
  `isPayloadSSL` varchar(10) DEFAULT 'false',
  `isSlow` varchar(10) DEFAULT 'false',
  `isHatok` varchar(10) DEFAULT 'false',
  `isInject` varchar(10) DEFAULT 'false',
  `isDirect` varchar(10) DEFAULT 'false',
  `isWeb` varchar(10) DEFAULT 'false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `settings` (`key`, `value`) VALUES
('Version', '1.0'),
('ReleaseNotes', 'Initial release'),
('ReleaseNotes1', 'Welcome!');

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- It is strongly recommended to change this password after installation.
-- The default password is "password". It is strongly recommended to change this after installation.
INSERT INTO `users` (`username`, `password`) VALUES
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi');
