using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IConfiguration
    {
        Task AddConfiguration(Configuration configuration);
        Task<Configuration> GetConfiguration(long id);
        Task<List<Configuration>> GetConfigurations();
        Task UpdateConfiguration(Configuration configuration);
    }
}