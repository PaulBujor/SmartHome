using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IAlarmService
    {
         //Motion
        Task<IEnumerable<Measurement>> GetAllMotions(long deviceId);   
        Task<Measurement> AddMotion(Measurement motion, long deviceId);
        Task<Measurement> GetLastMotion(long deviceId);
        Task RemoveMotion(int id);
    }
}