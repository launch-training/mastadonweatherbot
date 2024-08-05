 CREATE TABLE `location` (
   `id` int NOT NULL AUTO_INCREMENT,
   `guid` varchar(45) NOT NULL,
   `name` varchar(100) NOT NULL,
   `latitude` decimal(5,2) NOT NULL,
   `longitude` decimal(5,2) NOT NULL,
   `date_saved` datetime NOT NULL,
   `active` tinyint NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `guid_UNIQUE` (`guid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `post_history` (
   `id` int NOT NULL AUTO_INCREMENT,
   `guid` varchar(45) NOT NULL,
   `timestamp_weather_request` datetime NOT NULL,
   `weather_api_response` json DEFAULT NULL,
   `timestamp_mastodon_posted` datetime DEFAULT NULL,
   `error_logging` varchar(100) DEFAULT NULL,
   `post_link` varchar(100) DEFAULT NULL,
   `location_id` int NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `guid_UNIQUE` (`guid`),
   KEY `fk_location_id_idx` (`location_id`),
   CONSTRAINT `fk_location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;