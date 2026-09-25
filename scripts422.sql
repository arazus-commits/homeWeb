CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    price INT
);
CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    has_license BOOLEAN,
    car_id BIGINT,
    FOREIGN KEY (car_id) REFERENCES car (id)
);
