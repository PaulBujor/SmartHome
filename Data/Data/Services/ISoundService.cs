using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface ISoundService
    {
        Task<IEnumerable<Measurement>> GetAllSounds(long deviceId);   
        Task<Measurement> GetLastSound(long deviceId);
    }
}