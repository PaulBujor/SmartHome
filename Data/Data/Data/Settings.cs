using Data.Data.ConcreteConfigurations;
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
		public DeviceConfiguration DeviceConfiguration { get; set; }
		[JsonPropertyName("alarmConfiguration")]
		public AlarmConfiguration AlarmConfiguration { get; set; }
		[JsonPropertyName("temperatureConfiguration")]
		public TemperatureConfiguration TemperatureConfiguration { get; set; }
		[JsonPropertyName("co2Configuration")]
		public CO2Configuration CO2Configuration { get; set; }
		[JsonPropertyName("humidityConfiguration")]
		public HumidityConfiguration HumidityConfiguration { get; set; }

		public static Settings Defaults()
		{
			return new Settings
			{
				DeviceConfiguration = new DeviceConfiguration { Active = true },
				AlarmConfiguration = new AlarmConfiguration { Active = true, MinOrDefault = 0.5 },
				CO2Configuration = new CO2Configuration { Active = true, Max = 650, MinOrDefault = 200 },
				HumidityConfiguration = new HumidityConfiguration { Active = true, Max = 50, MinOrDefault = 30 },
				TemperatureConfiguration = new TemperatureConfiguration { Active = true, Max = 23, MinOrDefault = 20 }
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
