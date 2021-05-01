/* Create Database */
CREATE DATABASE postgres;

/* Use The Newly Created Database */
USE postgres;

/* Creating Table */
/*CREATE TABLE patients (*/
CREATE TABLE IF NOT EXISTS patients (
  internalId INTEGER NOT NULL,
  family VARCHAR(200) NOT NULL,
  gender VARCHAR(120) NOT NULL,
  PRIMARY KEY(internalId)
);

DESC patients;