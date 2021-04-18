using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class CO2Measurement : Measurement
	{
		public CO2Measurement() { }
		public CO2Measurement(Measurement measurement)
		{
			MeasurementID = measurement.MeasurementID;
			Timestamp = measurement.Timestamp;
			Value = measurement.Value;
		}
	}
}
