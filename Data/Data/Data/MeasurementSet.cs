using System;
using System.ComponentModel.DataAnnotations;

namespace Data.Data
{
    public class MeasurementSet
    {
        [Key]
        public long MeasurementID { get; set; }
        public DateTime Timestamp { get; set; }
        public double CO2 { get; set; }
        public double Humidity { get; set; }
        public double Temperature { get; set; }
        public double Alarm { get; set; }

        public MeasurementSet()
        {
            
        }
    }
}