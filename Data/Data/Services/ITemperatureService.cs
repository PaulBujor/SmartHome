using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ITemperatureService
    {
         //Temperature
        Task<IList<Measurement>> GetAllTemperatures();
        Task<Measurement> AddTemperature(Measurement temperature);
        Task<Measurement> GetLastTemeperature();
        Task RemoveTemperature(int id);
    }
}