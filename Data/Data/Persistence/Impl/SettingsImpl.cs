using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class SettingsImpl : ISettings
    {
        private Database _databaseContext;
        public SettingsImpl(Database context)
        {
            _databaseContext = context;
        }
        public async Task AddSetting(Settings setting)
        {
            throw new System.NotImplementedException();
        }

        public async Task<Settings> GetSetting(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Settings>> GetSettings(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task UpdateSetting(Settings setting)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveSetting(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}