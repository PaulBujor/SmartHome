using Data.Data.ConcreteMeasurements;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Device
	{
		[Key]
		[JsonIgnore]
		public long ID { get; set; }
		[JsonPropertyName("deviceId")]
		public long DeviceID { get; set; }
		[JsonPropertyName("deviceSettings")]
		public Settings DeviceSettings { get; set; }
		[JsonPropertyName("alarm")]
		public List<AlarmMeasurement> Alarm { get; set; }
		[JsonPropertyName("temperature")]
		public List<TemperatureMeasurement> Temperature { get; set; }
		[JsonPropertyName("co2")]
		public List<CO2Measurement> CO2 { get; set; }
		[JsonPropertyName("humidity")]
		public List<HumidityMeasurement> Humidity { get; set; }

		public Device()
		{
		}
	}
}
