using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHardwareService
    {

        Task<Settings> GetSettings(long deviceID);
        Task SetSettings(Settings settings,long deviceID);

    }
}