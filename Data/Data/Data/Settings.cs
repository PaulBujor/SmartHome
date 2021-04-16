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
	}
}
