--------------------------------------------------------------------------------------ORACLE------------------------------------------------------------------------------------------

CREATE TABLE COUNTRY (
	COUNTRY_ID NUMBER,
	NAME VARCHAR(50) NOT NULL, 
	CONSTRAINT COUNTRY_PK PRIMARY KEY (COUNTRY_ID),
	CONSTRAINT COUNTRY_NAME_UNIQUE UNIQUE (NAME),
	CONSTRAINT COUNTRY_NAME_CHECK CHECK (LENGTH(NAME) > 3)
);

CREATE SEQUENCE COUNTRY_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER COUNTRY_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON COUNTRY
    FOR EACH ROW
BEGIN
    SELECT COUNTRY_SEQUENCE.NEXTVAL
    INTO :NEW.COUNTRY_ID
    FROM DUAL;
END;

CREATE TABLE HOTEL (
	HOTEL_ID NUMBER,
	NAME VARCHAR(50) NOT NULL,
	STARS SMALLINT NOT NULL,
	WEBSITE VARCHAR(255),
	LATITUDE NUMBER(9,7) NOT NULL,
	LONGITUDE NUMBER(10,7) NOT NULL,
	CONSTRAINT HOTEL_PK PRIMARY KEY (HOTEL_ID),
	CONSTRAINT HOTEL_NAME_UNIQUE UNIQUE (NAME),
	CONSTRAINT HOTEL_WEBSITE_UNIQUE UNIQUE (WEBSITE),
	CONSTRAINT HOTEL_NAME_CHECK CHECK (LENGTH(NAME) > 3),
	CONSTRAINT HOTEL_STARS_CHECK CHECK(STARS > 0 AND STARS < 6),
	CONSTRAINT HOTEL_WEBSITE_REGEX_CHECK CHECK (REGEXP_INSTR(WEBSITE, 'https?\:\/\/www\.\w+\.[a-z]{2,3}($|\/[\w\/\-]+\/$)') > 0),
	CONSTRAINT HOTEL_LATITUDE_CHECK CHECK (LATITUDE > -90.000000 AND LATITUDE < 90.000000),
	CONSTRAINT HOTEL_LONGITUDE_CHECK CHECK (LONGITUDE > -180.000000 AND LONGITUDE < 180.000000)
);

CREATE SEQUENCE HOTEL_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER HOTEL_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON HOTEL
    FOR EACH ROW
BEGIN
    SELECT HOTEL_SEQUENCE.NEXTVAL
    INTO :NEW.HOTEL_ID
    FROM DUAL;
END;

CREATE TABLE FEATURE (
	FEATURE_ID NUMBER,
	NAME VARCHAR(30) NOT NULL,
	CONSTRAINT FEATURE_PK PRIMARY KEY (FEATURE_ID),
	CONSTRAINT FEATURE_NAME_UNIQUE UNIQUE (NAME),
	CONSTRAINT FEATURE_NAME_CHECK CHECK (LENGTH(NAME) > 3)
);

CREATE SEQUENCE FEATURE_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER FEATURE_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON FEATURE
    FOR EACH ROW
BEGIN
    SELECT FEATURE_SEQUENCE.NEXTVAL
    INTO :NEW.FEATURE_ID
    FROM DUAL;
END;

CREATE TABLE HOTEL_FEATURE (
	HOTEL_ID NUMBER NOT NULL,
	FEATURE_ID NUMBER NOT NULL,
	CONSTRAINT HOTEL_FEATURE_PK PRIMARY KEY (HOTEL_ID, FEATURE_ID),
	CONSTRAINT HOTEL_FEATURE_HOTEL_ID_FK FOREIGN KEY (HOTEL_ID) REFERENCES HOTEL (HOTEL_ID),
	CONSTRAINT HOTEL_FEATURE_FEATURE_ID_FK FOREIGN KEY (FEATURE_ID) REFERENCES FEATURE (FEATURE_ID)
);

CREATE TABLE USER (
    USER_ID NUMBER,
    LOGIN VARCHAR(30) NOT NULL,
    PASSWORD VARCHAR(30) NOT NULL,
	ROLE VARCHAR(10) DEFAULT 'USER',
    CONSTRAINT USER_PK PRIMARY KEY (USER_ID),
    CONSTRAINT USER_LOGIN_UNIQUE UNIQUE (LOGIN),
    CONSTRAINT USER_LOGIN_CHECK CHECK (LENGTH(LOGIN) > 4),
    CONSTRAINT USER_PASSWORD_CHECK CHECK (LENGTH(PASSWORD) > 4),
	CONSTRAINT USER_ROLE_CHECK CHECK (LENGTH(ROLE) > 3 AND ROLE IN ('USER', 'ADMIN'))
);

CREATE SEQUENCE USER_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER USER_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON USER
    FOR EACH ROW
BEGIN
    SELECT USER_SEQUENCE.NEXTVAL
    INTO :NEW.USER_ID
    FROM DUAL;
END;

CREATE TABLE TOUR (
	TOUR_ID NUMBER,
	PHOTO_PATH VARCHAR(255),
	START_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
	END_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
	DESCRIPTION VARCHAR(1000),
	COST NUMBER(14,4) NOT NULL,
	TOUR_TYPE VARCHAR(20) NOT NULL,
	HOTEL_ID NUMBER,
	COUNTRY_ID NUMBER,
	CONSTRAINT TOUR_PK PRIMARY KEY (TOUR_ID),
	CONSTRAINT TOUR_COUNTRY_ID_FK FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (COUNTRY_ID),
	CONSTRAINT TOUR_HOTEL_ID_FK FOREIGN KEY (HOTEL_ID) REFERENCES HOTEL (HOTEL_ID),
	CONSTRAINT TOUR_COST_CHECK CHECK(COST > 0.0000),
	CONSTRAINT TOUR_TYPE_CHECK CHECK(TOUR_TYPE IN ('BUSINESS', 'ECOTOURISM', 'LEISURE', 'PILGRIMAGE', 'RURAL_TOURISM',
	'SCIENTIFIC_EXPEDITION', 'SPORT_COMPETITION', 'TOURISM', 'TRAINING', 'TREATMENT'))
);

CREATE SEQUENCE TOUR_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER TOUR_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON TOUR
    FOR EACH ROW
BEGIN
    SELECT TOUR_SEQUENCE.NEXTVAL
    INTO :NEW.TOUR_ID
    FROM DUAL;
END;

CREATE OR REPLACE TRIGGER TOUR_CHECK_DATES_TRIGGER
  BEFORE INSERT OR UPDATE ON TOUR
  FOR EACH ROW
BEGIN
  IF( :NEW.START_DATE <= SYSTIMESTAMP )
  THEN
    RAISE_APPLICATION_ERROR( -20001, 'INVALID TOUR START DATE: ' || TO_CHAR(:NEW.START_DATE, 'YYYY-MM-DD HH24:MI:SS') || 
	'. START DATE MUST BE GREATER THAN THE CURRENT DATE: ' || TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'));
  END IF;
  IF( :NEW.END_DATE <= SYSTIMESTAMP )
  THEN
    RAISE_APPLICATION_ERROR( -20001, 'INVALID TOUR END DATE:' || TO_CHAR( :NEW.END_DATE, 'YYYY-MM-DD HH24:MI:SS' ) || 
	' END DATE MUST BE GREATER THAN THE CURRENT DATE: ' || TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'));
  END IF;
  IF( :NEW.END_DATE < (:NEW.START_DATE + INTERVAL '1' DAY))
  THEN
    RAISE_APPLICATION_ERROR( -20001, 'INVALID TOUR DURATION (START DATE - ' || TO_CHAR(:NEW.START_DATE, 'YYYY-MM-DD HH24:MI:SS') ||
	', END DATE - ' || TO_CHAR( :NEW.END_DATE, 'YYYY-MM-DD HH24:MI:SS' ) || ': DURATION MUST BE MORE THAN 1 DAY');
  END IF;
END;

CREATE TABLE USER_TOUR (
	USER_ID NUMBER NOT NULL,
	TOUR_ID NUMBER NOT NULL,
	CONSTRAINT USER_TOUR_PK PRIMARY KEY(USER_ID, TOUR_ID),
	CONSTRAINT USER_TOUR_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID),
	CONSTRAINT USER_TOUR_TOUR_ID_FK FOREIGN KEY (TOUR_ID) REFERENCES TOUR (TOUR_ID)
);

CREATE TABLE REVIEW (
	REVIEW_ID NUMBER,
	REVIEW_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
	REVIEW_TEXT VARCHAR(1000) NOT NULL,
	USER_ID NUMBER,
	TOUR_ID NUMBER,
	CONSTRAINT REVIEW_PK PRIMARY KEY (REVIEW_ID),
	CONSTRAINT REVIEW_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID),
    CONSTRAINT REVIEW_TOUR_ID_FK FOREIGN KEY (TOUR_ID) REFERENCES TOUR (TOUR_ID)
);

CREATE SEQUENCE REVIEW_SEQUENCE START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER REVIEW_AUTO_INCREMENT_TRIGGER
    BEFORE INSERT ON REVIEW
    FOR EACH ROW
BEGIN
    SELECT REVIEW_SEQUENCE.NEXTVAL
    INTO :NEW.REVIEW_ID
    FROM DUAL;
END;

CREATE OR REPLACE TRIGGER REVIEW_CHECK_DATE_TRIGGER
  BEFORE INSERT OR UPDATE ON REVIEW
  FOR EACH ROW
BEGIN
  IF( :NEW.REVIEW_DATE <= SYSTIMESTAMP )
  THEN
    RAISE_APPLICATION_ERROR( -20001, 'INVALID REVIEW DATE: ' || TO_CHAR(:NEW.REVIEW_DATE, 'YYYY-MM-DD HH24:MI:SS') ||
	'. REVIEW DATE MUST BE GREATER THAN THE CURRENT DATE: ' || TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'));
  END IF;
END;