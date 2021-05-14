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
		[JsonPropertyName("measurements")]
		public List<MeasurementSet> Measurements { get; set; }
	

		public Device()
		{
		}
	}
}
