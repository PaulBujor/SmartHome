using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Configuration
	{
		[Key]
		public long ConfigurationID { get; set; }
		public bool Active { get; set; }
		public double MinOrDefault { get; set; }
		public double Max { get; set; }
	}
}
