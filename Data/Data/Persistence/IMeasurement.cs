using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IMeasurement
    {
        Task AddCO2Measurement(Measurement measurement, long deviceID);
        Task AddAlarmMeasurement(Measurement measurement, long deviceID);
        Task AddHumidityMeasurement(Measurement measurement, long deviceID);
        Task AddTemperatureMeasurement(Measurement measurement, long deviceID);

        Task<Measurement> GetLatestCO2Measurement(long deviceID);
        Task<Measurement> GetLatestAlarmMeasurement(long deviceID);
        Task<Measurement> GetLatestHumidityMeasurement(long deviceID);
        Task<Measurement> GetLatestTemperatureMeasurement(long deviceID);
  
        Task<IEnumerable<Measurement>> GetAlarmMeasurements(long deviceID);
        Task<IEnumerable<Measurement>> GetCO2Measurements(long deviceID);
        Task<IEnumerable<Measurement>> GetHumidityMeasurements(long deviceID);
        Task<IEnumerable<Measurement>> GetTemperatureMeasurements(long deviceID);
        Task RemoveMeasurement(long id);
    }
}