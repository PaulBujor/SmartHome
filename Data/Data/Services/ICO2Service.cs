using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ICO2Service
    {
        Task<IList<Measurement>> GetAllCO2s(long deviceId);
        Task<Measurement> AddCO2(Measurement co2);
        Task<Measurement> GetLastCO2(long deviceId);
        Task RemoveCO2(int id);
    }
}