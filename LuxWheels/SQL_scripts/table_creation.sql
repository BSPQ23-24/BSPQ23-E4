USE luxwheels;
CREATE TABLE IF NOT EXISTS User (
	id INT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    birthdate DATE,
    licenseNumber INT UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Car (
    LicensePlate INT PRIMARY KEY,
    CarCondition ENUM('bad', 'standard', 'good'),
    Brand VARCHAR(255),
    Model VARCHAR(255),
    Year DATE,
    Location VARCHAR(255),
    UserID INT,
    CONSTRAINT OwnerId FOREIGN KEY (UserID) REFERENCES User(id)
);