USE [Data_db]
GO

DELETE FROM [stage].[DeviceFact]
DELETE FROM [stage].DimDevice
DELETE FROM [stage].DimThresholds
DELETE FROM [stage].UserGroup
DELETE FROM [stage].DimUser

INSERT INTO [stage].[DimThresholds]
           ([ThresholdsID]
           ,[minHumidity]
           ,[maxHumidity]
           ,[minTemperature]
           ,[maxTemperature]
           ,[minCo2]
           ,[maxCo2])
     SELECT
			[ThresholdsID]
           ,[minHumidity]
           ,[maxHumidity]
           ,[minTemperature]
           ,[maxTemperature]
           ,[minCo2]
           ,[maxCo2]
		FROM dbo.Thresholds
GO

INSERT INTO [stage].[DimDevice]
           ([deviceID]
           ,[deviceName])
	SELECT
			DeviceID
           ,DeviceName
		FROM dbo.Devices
GO

INSERT INTO [stage].[DimUser]
           ([userId]
           ,[email])
     SELECT
           UserID,
		   Email
		FROM dbo.Users
GO

INSERT INTO [stage].[UserGroup]
           ([userGroupId]
           ,[userId])
     SELECT
           DevicesID --user group determined by db generated device ID
           ,OwnersUserID
		FROM dbo.DeviceUser
GO

--etl until here executed

INSERT INTO [stage].[DeviceFact]
           ([ThresholdsID]
           ,[userGroupId]
           ,[deviceID]
           ,[timestamp]
           ,[co2Measurement]
           ,[temperatureMeasurement]
           ,[humidityMeasurement]
           ,[soundMeasurement])
     SELECT
			s.ThresholdsID,
			d.ID, --use device.ID
			d.DeviceID,
			m.Timestamp,
			m.CO2,
			m.Temperature,
			m.Humidity,
			m.Sound
		FROM dbo.Devices d
		inner join dbo.Measurements m on d.ID = m.DeviceID
		inner join dbo.Thresholds s on d.DeviceThresholdsThresholdsID = s.ThresholdsID
GO

