USE [Data_db]
GO



CREATE TABLE edw.DimDate (
 D_ID INT IDENTITY,
 Date DATE,
 Year INT,
 Month INT,
 Day INT
);

ALTER TABLE edw.DimDate ADD CONSTRAINT PK_edwDimDate PRIMARY KEY (D_ID);


CREATE TABLE edw.DimDevice (
 Dev_ID INT IDENTITY,
 deviceID BIGINT NOT NULL,
 roomName NCHAR VARYING(30),
 deviceName NCHAR VARYING(50)
);

ALTER TABLE edw.DimDevice ADD CONSTRAINT PK_edwDimDevice PRIMARY KEY (Dev_ID);


CREATE TABLE edw.DimThresholds (
 S_ID INT IDENTITY,
 minHumidity INT,
 maxHumidity INT,
 minTemperature INT,
 maxTemperature INT,
 minCo2 INT,
 maxCo2 INT
);

ALTER TABLE edw.DimThresholds ADD CONSTRAINT PK_edwDimThresholds PRIMARY KEY (S_ID);


CREATE TABLE edw.DimTime (
 T_ID INT IDENTITY,
 Time TIME,
 Hour INT,
 Minutes INT
);

ALTER TABLE edw.DimTime ADD CONSTRAINT PK_edwDimTime PRIMARY KEY (T_ID);


CREATE TABLE edw.DimUser (
 U_ID INT IDENTITY,
 userId INT,
 email NCHAR VARYING(50)
);

ALTER TABLE edw.DimUser ADD CONSTRAINT PK_edwDimUser PRIMARY KEY (U_ID);


CREATE TABLE stage.DimDevice (
 deviceID BIGINT NOT NULL,
 roomName NCHAR VARYING(30),
 deviceName NCHAR VARYING(30)
);

ALTER TABLE stage.DimDevice ADD CONSTRAINT PK_stageDimDevice PRIMARY KEY (deviceID);


CREATE TABLE stage.DimThresholds (
 ThresholdsID INT NOT NULL,
 minHumidity INT,
 maxHumidity INT,
 minTemperature INT,
 maxTemperature INT,
 minCo2 INT,
 maxCo2 INT
);

ALTER TABLE stage.DimThresholds ADD CONSTRAINT PK_stageDimThresholds PRIMARY KEY (ThresholdsID);


CREATE TABLE stage.DimUser (
 userId INT NOT NULL,
 email NCHAR VARYING(50)
);

ALTER TABLE stage.DimUser ADD CONSTRAINT PK_stageDimUser PRIMARY KEY (userId);


CREATE TABLE edw.DeviceFact (
 D_ID INT NOT NULL,
 S_ID INT NOT NULL,
 T_ID INT NOT NULL,
 Dev_ID INT NOT NULL,
 UG_ID INT,
 co2Measurement INT,
 temperatureMeasurement INT,
 humidityMeasurement INT,
 soundMeasurement INT
);

ALTER TABLE edw.DeviceFact ADD CONSTRAINT PK_edwDeviceFact PRIMARY KEY (D_ID,S_ID,T_ID,Dev_ID);


CREATE TABLE stage.UserGroup (
 userGroupId INT NOT NULL,
 userId INT NOT NULL
);

ALTER TABLE stage.UserGroup ADD CONSTRAINT PK_stageUserGroup PRIMARY KEY (userGroupId,userId);


CREATE TABLE stage.DeviceFact (
 F_ID INT IDENTITY,
 ThresholdsID INT NOT NULL,
 userGroupId INT,
 deviceID BIGINT NOT NULL,
 timestamp DATETIME,
 co2Measurement INT, 
 temperatureMeasurement INT,
 humidityMeasurement INT,
 soundMeasurement INT
);

ALTER TABLE stage.DeviceFact ADD CONSTRAINT PK_stageDeviceFact PRIMARY KEY (F_ID,ThresholdsID,deviceID);

CREATE TABLE edw.UserGroup (
 UG_ID INT NOT NULL,
 U_ID INT NOT NULL
);

ALTER TABLE edw.UserGroup ADD CONSTRAINT PK_edwUserGroup PRIMARY KEY (UG_ID,U_ID);


ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_0 FOREIGN KEY (D_ID) REFERENCES edw.DimDate (D_ID);
ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_1 FOREIGN KEY (S_ID) REFERENCES edw.DimThresholds (S_ID);
ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_2 FOREIGN KEY (T_ID) REFERENCES edw.DimTime (T_ID);
ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_3 FOREIGN KEY (Dev_ID) REFERENCES edw.DimDevice (Dev_ID);

--crashed here
--ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFactUG FOREIGN KEY (UG_ID) REFERENCES edw.UserGroup(UG_ID);


ALTER TABLE stage.UserGroup ADD CONSTRAINT FK_stageBridgeUserDevice_0 FOREIGN KEY (userId) REFERENCES stage.DimUser (userId);


ALTER TABLE stage.DeviceFact ADD CONSTRAINT FK_stageDeviceFact_0 FOREIGN KEY (ThresholdsID) REFERENCES stage.DimThresholds (ThresholdsID);
ALTER TABLE stage.DeviceFact ADD CONSTRAINT FK_stageDeviceFact_1 FOREIGN KEY (deviceID) REFERENCES stage.DimDevice (deviceID);

--and here
--ALTER TABLE stage.DeviceFact ADD CONSTRAINT FK_stageDeviceFactUG FOREIGN KEY (userGroupId) REFERENCES stage.UserGroup(userGroupId);


ALTER TABLE edw.UserGroup ADD CONSTRAINT FK_edwBridgeUserDevice_0 FOREIGN KEY (U_ID) REFERENCES edw.DimUser (U_ID);

/*
DROP TABLE stage.DeviceFact
go
DROP TABLE stage.DimDevice
go
Drop table stage.dimThresholds
go
drop table stage.dimuser
go
drop table stage.usergroup
go

DROP TABLE edw.DeviceFact
go
DROP TABLE edw.DimDevice
go
Drop table edw.dimThresholds
go
drop table edw.dimuser
go
drop table edw.usergroup
go

drop table edw.dimtime
go

drop table edw.dimdate
go
*/