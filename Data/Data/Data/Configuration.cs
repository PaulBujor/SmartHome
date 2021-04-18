using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public abstract class Configuration
	{
		[Key]
		[JsonPropertyName("configurationId")]
		public long ConfigurationID { get; set; }
		[JsonPropertyName("active")]
		public bool Active { get; set; }
		[JsonPropertyName("minOrDefault")]
		public double MinOrDefault { get; set; }
		[JsonPropertyName("max")]
		public double Max { get; set; }

		public Configuration()
		{
		}
	}
}
