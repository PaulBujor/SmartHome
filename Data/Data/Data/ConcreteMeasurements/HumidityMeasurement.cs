using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data.ConcreteMeasurements
{
	public class HumidityMeasurement : Measurement
	{
		public HumidityMeasurement() { }
		public HumidityMeasurement(Measurement measurement)
		{
			MeasurementID = measurement.MeasurementID;
			Timestamp = measurement.Timestamp;
			Value = measurement.Value;
		}
	}
}
