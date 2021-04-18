using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data.ConcreteMeasurements
{
	public class AlarmMeasurement : Measurement
	{
		public AlarmMeasurement() { }
		public AlarmMeasurement(Measurement measurement)
		{
			MeasurementID = measurement.MeasurementID;
			Timestamp = measurement.Timestamp;
			Value = measurement.Value;
		}
	}
}
