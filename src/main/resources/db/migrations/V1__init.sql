CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `age` int DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `first_name` varchar(255) DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `advert` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `created_date` date DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          `end_date` date DEFAULT NULL,
                          `is_active` bit(1) DEFAULT NULL,
                          `title` varchar(255) DEFAULT NULL,
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKjds5rnsjbg4gr45pmn8bgd7dj` (`user_id`),
                          CONSTRAINT `FKjds5rnsjbg4gr45pmn8bgd7dj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci