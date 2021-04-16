using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public class PersistenceRouter : IConfiguration,IDevice,IMeasurement,ISettings
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

        public Task AddDevice(Device device)
        {
            throw new System.NotImplementedException();
        }

        public Task<Device> GetDevice(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Device>> GetDevices()
        {
            throw new System.NotImplementedException();
        }

        public Task UpdateDevice(Device device)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveDevice(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task AddMeasurement(Measurement measurement)
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Measurement>> GetMeasurements()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

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