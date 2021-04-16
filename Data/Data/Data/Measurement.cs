using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class Measurement
	{
		public long MeasurementID { get; set; }
		public DateTime Timestamp { get; set; }
		public double Value { get; set; }
	}
}
