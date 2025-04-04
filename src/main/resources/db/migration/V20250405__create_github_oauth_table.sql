CREATE TABLE IF NOT EXISTS github_oauth (
                                           id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           login VARCHAR(255) NOT NULL,
                                           login_type VARCHAR(255) NOT NULL,
                                           github_id BIGINT NOT NULL UNIQUE,
                                           access_token VARCHAR(255) NOT NULL,
                                           scope VARCHAR(255),
                                           token_type VARCHAR(50),
                                           create_time DATETIME NOT NULL,
                                           update_time DATETIME NOT NULL,
                                           deleted TINYINT(1) DEFAULT 0
);
