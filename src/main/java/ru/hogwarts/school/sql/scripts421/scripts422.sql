CREATE TABLE Person (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    car_license BOOLEAN
);

CREATE TABLE Car (
    car_id SERIAL PRIMARY KEY,
    car_brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE PersonCar (
    person_car_id SERIAL PRIMARY KEY ,
    person_id INT,
    car_id INT,
    FOREIGN KEY (person_id) REFERENCES Person(person_id),
    FOREIGN KEY (car_id) REFERENCES Car(car_id)
);