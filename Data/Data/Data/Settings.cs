using Data.Data.ConcreteConfigurations;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Settings
	{
		[Key]
		public long SettingsID { get; set; }
		public DeviceConfiguration DeviceConfiguration { get; set; }
		public AlarmConfiguration AlarmConfiguration { get; set; }
		public TemperatureConfiguration TemperatureConfiguration { get; set; }
		public CO2Configuration CO2Configuration { get; set; }
		public HumidityConfiguration HumidityConfiguration { get; set; }

		public Settings Defaults()
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
