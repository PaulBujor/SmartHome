USE [Data_db]
GO


DELETE FROM edw.[DeviceFact]
DELETE FROM edw.DimDevice
DELETE FROM edw.DimThresholds
DELETE FROM edw.UserGroup
DELETE FROM edw.DimUser
go

INSERT INTO [edw].[DimDevice]
           ([deviceID]
           ,[roomName]
           ,[deviceName])
     SELECT
			deviceID,
			roomName,
			deviceName
		FROM stage.DimDevice
           
GO

INSERT INTO [edw].[DimThresholds]
           ([ThresholdsID]
		   ,[minHumidity]
           ,[maxHumidity]
           ,[minTemperature]
           ,[maxTemperature]
           ,[minCo2]
           ,[maxCo2])
     SELECT
			ThresholdsID,
           minHumidity,
		   maxHumidity,
		   minTemperature,
		   maxTemperature,
		   minCo2,
		   maxCo2
		FROM stage.DimThresholds
GO

INSERT INTO [edw].[DimUser]
           ([userId]
           ,[email])
     SELECT
			userId,
			email
		FROM stage.DimUser
GO

INSERT INTO [edw].[UserGroup]
           ([UG_ID]
           ,[U_ID])
     SELECT
           userGroupId,
		   userId
		FROM stage.UserGroup
GO

USE [Data_db]
GO

INSERT INTO [edw].[DeviceFact]
           ([D_ID]
           ,[S_ID]
           ,[T_ID]
           ,[Dev_ID]
           ,[UG_ID]
           ,[co2Measurement]
           ,[temperatureMeasurement]
           ,[humidityMeasurement]
           ,[soundMeasurement])
     SELECT
           dt.D_ID
           ,t.S_ID
           ,tm.T_ID
           ,dev.Dev_ID
           ,ug.UG_ID
           ,f.co2Measurement
           ,f.temperatureMeasurement
           ,f.humidityMeasurement
           ,f.soundMeasurement
		FROM stage.DeviceFact f
		inner join edw.DimDevice dev on f.deviceID = dev.deviceID
		inner join edw.DimThresholds t on f.ThresholdsID = t.ThresholdsID
		inner join edw.UserGroup ug on f.userGroupId = ug.UG_ID
		inner join edw.DimDate dt on DATEPART(day, f.timestamp) = DATEPART(day, dt.Date) AND DATEPART(MONTH, f.timestamp) = DATEPART(MONTH, dt.Date) AND DATEPART(YEAR, f.timestamp) = DATEPART(YEAR, dt.Date)
		inner join edw.DimTime tm on DATEPART(HOUR, f.timestamp) = DATEPART(HOUR, tm.Time) AND DATEPART(MINUTE, f.timestamp) = DATEPART(MINUTE, tm.Time)
GO



