using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Device
	{
		[Key]
		public long DeviceID { get; set; }
		public Configuration DeviceConfiguration { get; set; }
		public List<Measurement> Alarm { get; set; }
		public List<Measurement> Temperature { get; set; }
		public List<Measurement> CO2 { get; set; }
		public List<Measurement> Humidity { get; set; }
	}
}
