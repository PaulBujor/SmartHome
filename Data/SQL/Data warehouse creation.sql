CREATE TABLE edw.DimDate (
 D_ID INT NOT NULL,
 Date DATETIME
);

ALTER TABLE edw.DimDate ADD CONSTRAINT PK_edwDimDate PRIMARY KEY (D_ID);


CREATE TABLE edw.DimMeasurement (
 M_ID INT NOT NULL,
 measurementID INT,
 co2Value INT,
 temperatureValue INT,
 humidityValue INT,
 soundValue INT
);

ALTER TABLE edw.DimMeasurement ADD CONSTRAINT PK_edwDimMeasurement PRIMARY KEY (M_ID);


CREATE TABLE edw.DimSettings (
 S_ID INT NOT NULL,
 minHumidity INT,
 maxHumidity INT,
 minTemperature INT,
 maxTemperature INT,
 minCo2 INT,
 maxCo2 INT
);

ALTER TABLE edw.DimSettings ADD CONSTRAINT PK_edwDimSettings PRIMARY KEY (S_ID);


CREATE TABLE stage.DimMeasurement (
 measurementID INT NOT NULL,
 co2Value INT,
 temperatureValue INT,
 humidityValue INT,
 soundValue INT
);

ALTER TABLE stage.DimMeasurement ADD CONSTRAINT PK_stageDimMeasurement PRIMARY KEY (measurementID);


CREATE TABLE stage.DimSettings (
 settingsID INT NOT NULL,
 minHumidity INT,
 maxHumidity INT,
 minTemperature INT,
 maxTemperature INT,
 minCo2 INT,
 maxCo2 INT
);

ALTER TABLE stage.DimSettings ADD CONSTRAINT PK_stageDimSettings PRIMARY KEY (settingsID);


CREATE TABLE edw.DeviceFact (
 deviceID CHAR(10) NOT NULL,
 D_ID INT NOT NULL,
 S_ID INT NOT NULL,
 M_ID INT NOT NULL
);

ALTER TABLE edw.DeviceFact ADD CONSTRAINT PK_edwDeviceFact PRIMARY KEY (deviceID,D_ID,S_ID,M_ID);

CREATE TABLE stage.DeviceFact (
 deviceID BIGINT NOT NULL,
 measurementID INT NOT NULL,
 settingsID INT NOT NULL,
 timestamp DATETIME
);

ALTER TABLE stage.DeviceFact ADD CONSTRAINT PK_stageDeviceFact PRIMARY KEY (deviceID,measurementID,settingsID);


ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_0 FOREIGN KEY (D_ID) REFERENCES edw.DimDate (D_ID);
ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_1 FOREIGN KEY (S_ID) REFERENCES edw.DimSettings (S_ID);
ALTER TABLE edw.DeviceFact ADD CONSTRAINT FK_edwDeviceFact_2 FOREIGN KEY (M_ID) REFERENCES edw.DimMeasurement (M_ID);


ALTER TABLE stage.DeviceFact ADD CONSTRAINT FK_stageDeviceFact_0 FOREIGN KEY (measurementID) REFERENCES stage.DimMeasurement (measurementID);
ALTER TABLE stage.DeviceFact ADD CONSTRAINT FK_stageDeviceFact_1 FOREIGN KEY (settingsID) REFERENCES stage.DimSettings (settingsID);

use Data_db

declare @StartDate DATETIME;
declare @EndDate DATETIME;

set @StartDate = '2021-04-01';
set @EndDate = DATEADD(MONTH, 3, getdate());

SELECT @StartDate as "StartDate", @EndDate as "EndDate"

while @StartDate <= @EndDate
	BEGIN
		INSERT INTO [edw].[DimDate]
				   ([D_ID]
				   ,[Date])
			 SELECT
					CONVERT(CHAR(10), @StartDate, 112) as D_ID,
					@StartDate as Date;

		SET @StartDate = DATEADD(MINUTE, 5, @StartDate);
END