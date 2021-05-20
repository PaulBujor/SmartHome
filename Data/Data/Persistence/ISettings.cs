using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface ISettings
    {

        Task<Thresholds> GetSettings(long deviceID);
        Task SetSettings(Thresholds thresholds,long deviceID);
  

    }
}