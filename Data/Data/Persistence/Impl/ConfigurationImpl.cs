using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class ConfigurationImpl : IConfiguration
    {
        private Database _databaseContext;

        public ConfigurationImpl(Database context)
        {
            _databaseContext = context;
        }
        public async Task AddConfiguration(Configuration configuration)
        {
            throw new System.NotImplementedException();
        }

        public async Task<Configuration> GetConfiguration(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Configuration>> GetConfigurations()
        {
            throw new System.NotImplementedException();
        }

        public async Task UpdateConfiguration(Configuration configuration)
        {
            throw new System.NotImplementedException();
        }
    }
}