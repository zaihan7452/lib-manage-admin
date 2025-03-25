CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    deleted TINYINT(1) DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS role (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL UNIQUE,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    deleted TINYINT(1) DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS permission (
                                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL UNIQUE,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    deleted TINYINT(1) DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS user_role (
                                         user_id BIGINT NOT NULL,
                                         role_id BIGINT NOT NULL,
                                         PRIMARY KEY (user_id, role_id),
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    deleted TINYINT(1) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
    );

CREATE TABLE IF NOT EXISTS role_permission (
                                               role_id BIGINT NOT NULL,
                                               permission_id BIGINT NOT NULL,
                                               PRIMARY KEY (role_id, permission_id),
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    deleted TINYINT(1) DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
    );
