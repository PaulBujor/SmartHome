using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHumidityService
    {
         //Humidity
        Task<IList<Measurement>> GetAllHumidities();
        Task<Measurement> GetLastHumidity();
        Task<Measurement> AddHumidity(Measurement humidity);
        Task RemoveHumidity(int id);

    }
}