using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IMeasurement
    {
        Task AddMeasurement(Measurement measurement);
        Task<Measurement> GetMeasurement(long id);
        Task<List<Measurement>> GetAlarmMeasurements(long deviceID);
        Task<List<Measurement>> GetCO2Measurements(long deviceID);
        Task<List<Measurement>> GetHumidityMeasurements(long deviceID);
        Task<List<Measurement>> GetTemperatureMeasurements(long deviceID);
        Task RemoveMeasurement(long id);
    }
}