using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHumidityService
    {
        Task<IEnumerable<Measurement>> GetAllHumidities(long deviceId);
        Task<Measurement> GetLastHumidity(long deviceId);

    }
}