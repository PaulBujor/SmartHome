using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Data.Data
{
	public class User
	{
		[Key]
		[JsonPropertyName("userId")]
		public long UserID { get; set; }
		[JsonPropertyName("email")]
		public string Email { get; set; }
		[JsonPropertyName("password")]
		public string Password { get; set; }
		public List<Device> Devices { get; set; }

		public User()
		{
		}
	}
}
