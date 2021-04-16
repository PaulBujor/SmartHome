using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Configuration
	{
		public long ConfigurationID { get; set; }
		public bool Active { get; set; }
		public double MinOrDefault { get; set; }
		public double Max { get; set; }
	}
}
