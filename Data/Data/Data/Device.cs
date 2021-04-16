using Data.Data.ConcreteMeasurements;
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
		public List<AlarmMeasurement> Alarm { get; set; }
		public List<TemperatureMeasurement> Temperature { get; set; }
		public List<CO2Measurement> CO2 { get; set; }
		public List<HumidityMeasurement> Humidity { get; set; }
	}
}
