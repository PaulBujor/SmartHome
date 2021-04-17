using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IHardwareService
    {
        Task<IList<Measurement>> GetAllSettings(long deviceId);
        Task<Settings> GetSettingsByID(long id);
        Task<Settings> AddSetting(Settings settings);
        Task RemoveSetting(int id);
    }
}