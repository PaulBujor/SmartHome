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
 ThresholdsID INT,
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

DROP TABLE edw.DeviceFact

CREATE TABLE edw.DeviceFact (
 F_ID INT IDENTITY,
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

ALTER TABLE edw.DeviceFact ADD CONSTRAINT PK_edwDeviceFact PRIMARY KEY (F_ID,D_ID,S_ID,T_ID,Dev_ID);


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

--dim date generate
declare @StartDate DATE;
declare @EndDate DATE;

set @StartDate = '2021-01-01';
set @EndDate = DATEADD(year, 1, getdate());


while @StartDate <= @EndDate
	BEGIN
		SET IDENTITY_INSERT edw.DimDate ON
		INSERT INTO [edw].[DimDate]
				   ([D_ID]
				   ,[Date]
				   ,[Day]
				   ,[Month]
				   ,[Year])
			 SELECT
					CONVERT(CHAR(8), @StartDate, 112) as D_ID,
					@StartDate as Date,
					DATEPART(day, @StartDate) as Day,
					DATEPART(month, @StartDate) as Month,
					DATEPART(year, @StartDate) as Year

		SET @StartDate = DATEADD(day, 1, @StartDate);
		
		SET IDENTITY_INSERT edw.DimDate OFF
	END


--dim time generate
declare @StartTime TIME;
declare @EndTime TIME;

set @StartTime = '14:30';
set @EndTime = '23:55';

while @StartTime < @EndTime
	BEGIN
		SET IDENTITY_INSERT edw.DimTime ON
		INSERT INTO [edw].[DimTime]
				   ([T_ID]
				   ,[Time]
				   ,[Hour]
				   ,[Minutes])
			 SELECT
					FORMAT(@StartTime, 'hhmm') as T_ID,
					@StartTime as Time,
					DATEPART(HOUR, @StartTime) as Hour,
					DATEPART(MINUTE, @StartTime) as Minutes

		SET @StartTime = DATEADD(MINUTE, 5, @StartTime);
		
		SET IDENTITY_INSERT edw.DimTime OFF
	END


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