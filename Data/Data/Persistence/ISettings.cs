using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface ISettings
    {
        Task AddSetting(Settings setting, long deviceId);
        Task<Settings> GetSetting(long id);
        Task<List<Settings>> GetSettings(long id);
        Task UpdateSetting(Settings setting);
        Task RemoveSetting(long id);
    }
}