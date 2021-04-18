using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IMeasurement
    {
        Task AddMeasurement(Measurement measurement, long deviceID);
        Task<Measurement> GetMeasurement(long id);
        Task<IEnumerable<Measurement>> GetAlarmMeasurements(long deviceID);
        Task<IEnumerable<Measurement>> GetCO2Measurements(long deviceID);
        Task<IEnumerable<Measurement>> GetHumidityMeasurements(long deviceID);
        Task<IEnumerable<Measurement>> GetTemperatureMeasurements(long deviceID);
        Task RemoveMeasurement(long id);
    }
}