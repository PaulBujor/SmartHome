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
		public Configuration DeviceConfiguration { get; set; }
		public Configuration AlarmConfiguration { get; set; }
		public Configuration TemperatureConfiguration { get; set; }
		public Configuration CO2Configuration { get; set; }
		public Configuration HumidityConfiguration { get; set; }
	}
}
