USE [Data_db]
GO

INSERT INTO edw.[DimMeasurement]
           ([measurementID]
           ,[co2Value]
           ,[temperatureValue]
           ,[humidityValue]
           ,[soundValue])
     SELECT
			[measurementID]
           ,co2Value
           ,temperatureValue
           ,humidityValue
           ,soundValue
		FROM [stage].[DimMeasurement]
GO

INSERT INTO edw.[DimSettings]
           ([settingsID]
           ,[minHumidity]
           ,[maxHumidity]
           ,[minTemperature]
           ,[maxTemperature]
           ,[minCo2]
           ,[maxCo2])
     SELECT
			[settingsID]
           ,[minHumidity]
           ,[maxHumidity]
           ,[minTemperature]
           ,[maxTemperature]
           ,[minCo2]
           ,[maxCo2]
		FROM stage.DimSettings
GO

INSERT INTO edw.[DeviceFact]
           ([deviceID]
           ,[measurementID]
           ,[settingsID]
           ,[timestamp])
     SELECT
			d.[deviceID]
           ,[measurementID]
           ,[settingsID]
           ,[timestamp]
		FROM dbo.Devices d
		inner join dbo.Measurements m on d.DeviceID = m.DeviceID
		inner join dbo.Settings s on d.DeviceSettingsSettingsID = s.SettingsID
GO

