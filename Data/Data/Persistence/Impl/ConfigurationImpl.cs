using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class ConfigurationImpl : IConfiguration
    {
        public Task AddConfiguration(Configuration configuration)
        {
            throw new System.NotImplementedException();
        }

        public Task<Configuration> GetConfiguration(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Configuration>> GetConfigurations()
        {
            throw new System.NotImplementedException();
        }

        public Task UpdateConfiguration(Configuration configuration)
        {
            throw new System.NotImplementedException();
        }
    }
}