using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public class EUser : User
	{
		[JsonPropertyName("newDeviceName")]
		public string NewDeviceName { get; set; }

		public EUser()
		{
		}
	}
}
