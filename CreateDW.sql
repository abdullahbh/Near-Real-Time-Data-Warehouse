drop schema if exists `dw` ;
-- DROP TABLE if exists `TRANSACTIONS`;
-- DROP TABLE if exists `CUSTOMERS`;
-- DROP TABLE if exists `PRODUCTS`;
commit;

CREATE SCHEMA `dw` ;
USE `dw` ;

CREATE TABLE Products(
  PRODUCT_ID VARCHAR(6) NOT NULL,
  PRODUCT_NAME VARCHAR(30) NOT NULL,
  PRICE DOUBLE(5,2) DEFAULT 0.0, 
  PRIMARY KEY (PRODUCT_ID)
);

CREATE TABLE supplier(
  SUPPLIER_ID VARCHAR(5) NOT NULL, 
  SUPPLIER_NAME VARCHAR(30) NOT NULL, 
  PRIMARY KEY (SUPPLIER_ID)
);


CREATE TABLE Store(
  STORE_ID VARCHAR(10) NOT NULL, 
  STORE_NAME VARCHAR(20) NOT NULL,  
  PRIMARY KEY (STORE_ID)
);

CREATE TABLE customers(
    CUSTOMER_ID VARCHAR(4) NOT NULL, 
    CUSTOMER_NAME VARCHAR(30) not null,
    PRIMARY KEY (CUSTOMER_ID)
) ;

CREATE TABLE Time_d(
  TIME_ID VARCHAR(10) NOT NULL, 
  T_DATE DATE NOT NULL,
  T_DAY VARCHAR(10) NOT NULL,
  T_MONTH VARCHAR(10)NOT NULL,
  T_QUARTER int NOT NULL,
  T_YEAR int NOT NULL,
  PRIMARY KEY (TIME_ID)
) ;

CREATE TABLE TRANSACTIONS(
  TRANSACTION_ID DOUBLE(8,0), 
  PRODUCT_ID VARCHAR(6) NOT NULL, 
  CUSTOMER_ID VARCHAR(4) NOT NULL,
  TIME_ID VARCHAR(10) NOT NULL,  
  STORE_ID VARCHAR(10) NOT NULL, 
  SUPPLIER_ID VARCHAR(5) NOT NULL, 
  QUANTITY DOUBLE(3,0) NOT NULL,
  SALE DOUBLE(5,2) DEFAULT 0.0,
  PRIMARY KEY (TRANSACTION_ID, PRODUCT_ID, CUSTOMER_ID, TIME_ID, STORE_ID, SUPPLIER_ID),
  FOREIGN KEY (PRODUCT_ID) REFERENCES products(PRODUCT_ID),
  FOREIGN KEY (CUSTOMER_ID) REFERENCES customers(CUSTOMER_ID),
  FOREIGN KEY (TIME_ID) REFERENCES Time_d(TIME_ID),
  FOREIGN KEY (STORE_ID) REFERENCES store(STORE_ID),
  FOREIGN KEY (SUPPLIER_ID) REFERENCES supplier(SUPPLIER_ID)
);