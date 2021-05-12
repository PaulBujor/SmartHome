using System;
using System.ComponentModel.DataAnnotations;

namespace Data.Data
{
    public class MeasurementSet
    {
        [Key]
        public long MeasurementID { get; set; }
        public DateTime Timestamp { get; set; }
        public Measurement CO2 { get; set; }
        public Measurement Humidity { get; set; }
        public Measurement Temperature { get; set; }
        public Measurement Alarm { get; set; }

        public MeasurementSet()
        {
            
        }
    }
}