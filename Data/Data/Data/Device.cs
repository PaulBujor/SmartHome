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
		[JsonPropertyName("deviceName")]
		public string DeviceName { get; set; }
		[JsonIgnore]
		public Thresholds DeviceThresholds { get; set; }
		[JsonIgnore]
		public List<MeasurementSet> Measurements { get; set; }

		[JsonIgnore]
		public List<User> Owners { get; set; }


		public Device()
		{
		}
	}
}
