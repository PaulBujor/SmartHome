using System;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Data.Data
{
    public class MeasurementSet
    {
        [Key]
        [JsonPropertyName("measurementId")]
        public long MeasurementID { get; set; }
        [JsonPropertyName("timestamp")]
        public DateTime Timestamp { get; set; }
        [JsonPropertyName("co2")]
        public double CO2 { get; set; }
        [JsonPropertyName("humidity")]
        public double Humidity { get; set; }
        [JsonPropertyName("temperature")]
        public double Temperature { get; set; }
        [JsonPropertyName("sound")]
        public double Sound { get; set; }

        public MeasurementSet()
        {
            
        }
    }
}