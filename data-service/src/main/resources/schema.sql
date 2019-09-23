CREATE TABLE VEHICLE (
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  VIN VARCHAR(17) NOT NULL,
  REGNR VARCHAR(6) NOT NULL UNIQUE,
  CUSTOMER VARCHAR(50) NOT NULL,
  ADDRESS VARCHAR(50) NOT NULL,
  STATUS VARCHAR(15) NOT NULL
);