CREATE TABLE IF NOT EXISTS comment (
    id INT NOT NULL AUTO_INCREMENT,
    post_id INT,
    name VARCHAR(255),
    email VARCHAR(255),
    body VARCHAR(1024),
    PRIMARY KEY (id)
   );