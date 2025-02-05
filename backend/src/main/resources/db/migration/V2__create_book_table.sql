CREATE TABLE books (
    isbn VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    added_by INT NOT NULL,
    CONSTRAINT fk_added_by FOREIGN KEY (added_by) REFERENCES users(id) ON DELETE CASCADE
);