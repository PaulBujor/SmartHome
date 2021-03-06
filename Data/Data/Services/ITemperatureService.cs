using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ITemperatureService
    {
         //Temperature
        Task<IEnumerable<Measurement>> GetAllTemperatures(long deviceId);
        Task<Measurement> GetLastTemperature(long deviceId);
    }
}