using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IAlarmService
    {
        Task<IEnumerable<Measurement>> GetAllMotions(long deviceId);   
        Task<Measurement> GetLastMotion(long deviceId);
    }
}