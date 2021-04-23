using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHumidityService
    {
         //Humidity
        Task<IEnumerable<Measurement>> GetAllHumidities(long deviceId);
        Task<Measurement> GetLastHumidity(long deviceId);
        Task<Measurement> AddHumidity(Measurement humidity, long deviceId);
        Task RemoveHumidity(int id);

    }
}