using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class SettingsImpl : ISettings
    {
        public Task AddSetting(Settings setting)
        {
            throw new System.NotImplementedException();
        }

        public Task<Settings> GetSetting(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Settings>> GetSettings()
        {
            throw new System.NotImplementedException();
        }

        public Task UpdateSetting(Settings setting)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveSetting(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}