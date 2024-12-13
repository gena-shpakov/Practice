CREATE TABLE photo_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(255) NOT NULL,
    session_number INT NOT NULL,
    session_date DATE NOT NULL,
    photographer VARCHAR(255) NOT NULL,
    session_type VARCHAR(255) NOT NULL
);