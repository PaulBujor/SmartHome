using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Settings
	{
		[Key]
		[JsonPropertyName("settingsId")]
		public long SettingsID { get; set; }
		[JsonPropertyName("deviceConfiguration")]
		public bool DeviceConfiguration { get; set; }
		[JsonPropertyName("minHumidity")]
		public int MinHumidity { get; set; }
		[JsonPropertyName("maxHumidity")]
		public int MaxHumidity { get; set; }
		[JsonPropertyName("minTemperature")]
		public int MinTemperature { get; set; }
		[JsonPropertyName("maxTemperature")]
		public int MaxTemperature { get; set; }
		[JsonPropertyName("minCo2")]
		public int MinCo2 { get; set; }
		[JsonPropertyName("maxCo2")]
		public int MaxCo2 { get; set; }

		public static Settings Defaults()
		{
			return new Settings
			{
				DeviceConfiguration = true,
				MinHumidity = 30,
				MaxHumidity = 50,
				MinTemperature = 20,
				MaxTemperature = 23,
				MinCo2 = 200,
				MaxCo2 = 650
			};
		}

		public Settings()
		{
		}

		/* Default settings
		 * Settings
			{
				DeviceConfiguration = new DeviceConfiguration { Active = true},
				AlarmConfiguration = new AlarmConfiguration { Active = true, MinOrDefault = 0.5},
				CO2Configuration = new CO2Configuration { Active = true, Max = 650, MinOrDefault = 200},
				HumidityConfiguration = new HumidityConfiguration { Active = true, Max = 50, MinOrDefault = 30 },
				TemperatureConfiguration = new TemperatureConfiguration { Active = true, Max = 23, MinOrDefault = 20 }
			};
		*/
	}
}
