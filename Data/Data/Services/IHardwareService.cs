using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHardwareService
    {

        Task<Thresholds> GetSettings(long deviceID);
        Task SetSettings(Thresholds thresholds,long deviceID);
        Task CheckDeviceExists(long deviceId);
        Task Reset(long id);
    }
}