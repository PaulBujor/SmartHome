USE [Data_db]
GO

INSERT INTO [stage].[DimMeasurement]
           ([measurementID]
           ,[co2Value]
           ,[temperatureValue]
           ,[humidityValue]
           ,[soundValue])
     SELECT
			[measurementID]
           ,[CO2]
           ,[Temperature]
           ,[Humidity]
           ,[Sound]
		FROM dbo.Measurements
GO

INSERT INTO [stage].[DimSettings]
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
		FROM dbo.Settings
GO

INSERT INTO [stage].[DeviceFact]
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
		inner join dbo.Measurements m on d.ID = m.DeviceID
		inner join dbo.Settings s on d.DeviceSettingsSettingsID = s.SettingsID
GO

