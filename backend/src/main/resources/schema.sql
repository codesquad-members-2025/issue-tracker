CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  login_id VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(255),
  password VARCHAR(255),
  created_at DATETIME,
  updated_at DATETIME,
  provider_id VARCHAR(100),
  auth_provider VARCHAR(50)
);