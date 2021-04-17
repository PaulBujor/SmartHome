using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IAlarmService
    {
         //Motion
        Task<IList<Measurement>> GetAllMotions();   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> AddMotion(Measurement motion);   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> GetLastMotion();
        Task RemoveMotion(int id);
    }
}