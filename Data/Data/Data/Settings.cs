using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Settings
	{
		public Configuration DeviceConfiguration { get; set; }
		public Configuration AlarmConfiguration { get; set; }
		public Configuration TemperatureConfiguration { get; set; }
		public Configuration CO2Configuration { get; set; }
		public Configuration HumidityConfiguration { get; set; }
	}
}
