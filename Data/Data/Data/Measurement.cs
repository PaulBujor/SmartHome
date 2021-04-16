using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public abstract class Measurement
	{
		[Key]
		public long MeasurementID { get; set; }
		public DateTime Timestamp { get; set; }
		public double Value { get; set; }
	}
}
