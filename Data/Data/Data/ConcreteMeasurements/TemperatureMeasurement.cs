using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data
{
	public class TemperatureMeasurement : Measurement
	{
		public TemperatureMeasurement() { }
		public TemperatureMeasurement(Measurement measurement)
		{
			MeasurementID = measurement.MeasurementID;
			Timestamp = measurement.Timestamp;
			Value = measurement.Value;
		}
	}
}
