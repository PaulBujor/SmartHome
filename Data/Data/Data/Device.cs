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
		public Measurement Alarm { get; set; }
		public Measurement Temperature { get; set; }
		public Measurement CO2 { get; set; }
		public Measurement Humidity { get; set; }
	}
}
