using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ITemperatureService
    {
         //Temperature
        Task<IList<Measurement>> GetAllTemperatures(long deviceId);
        Task<Measurement> AddTemperature(Measurement temperature, long deviceId);
        Task<Measurement> GetLastTemperature(long deviceId);
        Task RemoveTemperature(int id);
    }
}