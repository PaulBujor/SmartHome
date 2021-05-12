using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ICO2Service
    {
        Task<IEnumerable<Measurement>> GetAllCO2s(long deviceId);
        Task<Measurement> GetLastCO2(long deviceId);
    }
}