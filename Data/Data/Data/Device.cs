using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Device
	{
		public long DeviceID { get; set; }
		public Configuration DeviceConfiguration { get; set; }
		public List<Measurement> Alarm { get; set; }
		public List<Measurement> Temperature { get; set; }
		public List<Measurement> CO2 { get; set; }
		public List<Measurement> Humidity { get; set; }
	}
}
