CREATE DOMAIN secRating AS CHAR(10)
    DEFAULT 'weak'
    CONSTRAINT allowed_values
        CHECK ( VALUE = 'excellent' OR
                VALUE = 'very good' OR
                VALUE = 'good' OR
                VALUE = 'weak');

CREATE DOMAIN skillGrade AS CHAR(3)
    DEFAULT 'C'
    CONSTRAINT allowed_values
        CHECK ( VALUE = 'A+' OR
                VALUE = 'A' OR
                VALUE = 'B+' OR
                VALUE = 'B' OR
                VALUE = 'C+' OR
                VALUE = 'C' OR
                VALUE = 'D+' OR
                VALUE = 'D');

CREATE TABLE Banks (
  BankName TEXT,
  City TEXT,
  NoAccounts INT CHECK (NoAccounts >= 0),
  Security secRating,
  PRIMARY KEY(BankName, City)
);
--response: CREATE TABLE

CREATE TABLE Robberies (
  BankName TEXT,
  City TEXT,
  Date DATE,
  Amount NUMERIC,
  PRIMARY KEY(BankName, City, Date),
  FOREIGN KEY(BankName, City)
      REFERENCES Banks(BankName, City)
      ON DELETE RESTRICT
);
--response: CREATE TABLE

CREATE TABLE Plans (
  BankName TEXT,
  City TEXT,
  PlannedDate DATE,
  NoRobbers INT CHECK (NoRobbers > 0),
  PRIMARY KEY(BankName, City, PlannedDate),
  FOREIGN KEY(BankName, City)
      REFERENCES Banks(BankName, City)
      ON DELETE CASCADE
);
--response: CREATE TABLE

CREATE TABLE Robbers (
  RobberId SERIAL PRIMARY KEY,
  Nickname TEXT,
  Age INT CHECK (Age > 0),
  NoYears INT CHECK (NoYears < Age)
);
--response: CREATE TABLE

CREATE TABLE Skills (
  SkillId SERIAL PRIMARY KEY ,
  Description TEXT UNIQUE NOT NULL
);
--response: CREATE TABLE

CREATE TABLE HasSkills (
  RobberId INT,
  SkillId INT,
  Preference INT CHECK (Preference > 0),
  Grade skillGrade,
  UNIQUE (RobberId, Preference),
  PRIMARY KEY(RobberId, SkillId),
  FOREIGN KEY(RobberId)
      REFERENCES Robbers(RobberId)
      ON DELETE CASCADE,
  FOREIGN KEY(SkillId)
      REFERENCES Skills(SkillId)
      ON DELETE CASCADE
);
--response: CREATE TABLE

CREATE TABLE HasAccounts (
  RobberId INT,
  BankName TEXT,
  City TEXT,
  PRIMARY KEY(RobberId, BankName, City),
  FOREIGN KEY(BankName, City)
      REFERENCES Banks(BankName, City)
      ON DELETE CASCADE,
  FOREIGN KEY(RobberId)
      REFERENCES Robbers(RobberId)
      ON DELETE CASCADE
);
--response: CREATE TABLE

CREATE TABLE Accomplices (
  RobberId INT,
  BankName TEXT,
  City TEXT,
  Date DATE,
  Share NUMERIC,
  PRIMARY KEY(BankName, City, Date, RobberId),
  FOREIGN KEY(BankName, City, Date)
      REFERENCES Robberies(BankName, City, Date)
      ON DELETE RESTRICT,
  FOREIGN KEY(RobberId)
      REFERENCES Robbers(RobberId)
      ON DELETE RESTRICT
);
--response: CREATE TABLE