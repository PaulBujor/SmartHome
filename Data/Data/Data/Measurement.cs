using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Measurement
	{
		[JsonPropertyName("timestamp")]
		public DateTime Timestamp { get; set; }
		[JsonPropertyName("value")]
		public double Value { get; set; }

		public Measurement()
		{
		}
	}


}
