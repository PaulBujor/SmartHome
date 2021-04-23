using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence.Impl;

namespace Data.Properties.Persistence
{
    public class PersistenceRouter : IConfiguration,IDevice,IMeasurement,ISettings
    {
         private IConfiguration _configuration;
         private IDevice _device;
         private IMeasurement _measurement;
         private ISettings _settings;

         public PersistenceRouter()
         {
             Database context = new Database();
             _configuration = new ConfigurationImpl(context);
             _device = new DeviceImpl(context);
             _measurement = new MeasurementImpl(context);
             _settings = new SettingsImpl(context);
         }
        
        public async Task AddConfiguration(Configuration configuration)
        {
          await _configuration.AddConfiguration(configuration);
        }

        public async Task<Configuration> GetConfiguration(long id)
        {
            return await _configuration.GetConfiguration(id);
        }

        public async Task<List<Configuration>> GetConfigurations()
        {
            throw new System.NotImplementedException();
        }

        public async Task UpdateConfiguration(Configuration configuration)
        {
           await _configuration.UpdateConfiguration(configuration);
        }

        public async Task AddDevice(Device device)
        {
            await _device.AddDevice(device);
        }

        public async Task<Device> GetDevice(long id)
        {
            return await _device.GetDevice(id);
        }

        public async Task<List<Device>> GetDevices()
        {
            return await _device.GetDevices();
        }

        public async Task UpdateDevice(Device device)
        {
            await _device.UpdateDevice(device);
        }

        public async Task RemoveDevice(long id)
        {
            await _device.RemoveDevice(id);
        }

        public async Task AddCO2Measurement(Measurement measurement, long deviceID)
        {
            await _measurement.AddCO2Measurement(measurement, deviceID);
        }

        public async Task AddAlarmMeasurement(Measurement measurement, long deviceID)
        {
            await _measurement.AddAlarmMeasurement(measurement, deviceID);
        }

        public async Task AddHumidityMeasurement(Measurement measurement, long deviceID)
        {
            await _measurement.AddHumidityMeasurement(measurement, deviceID);
        }

        public async Task AddTemperatureMeasurement(Measurement measurement, long deviceID)
        {
            await _measurement.AddTemperatureMeasurement(measurement, deviceID);
        }

        public async Task<Measurement> GetLatestCO2Measurement(long deviceID)
        {
            return await _measurement.GetLatestCO2Measurement(deviceID);
        }

        public async Task<Measurement> GetLatestAlarmMeasurement(long deviceID)
        {
            return await _measurement.GetLatestAlarmMeasurement(deviceID);
        }

        public async Task<Measurement> GetLatestHumidityMeasurement(long deviceID)
        {
            return await _measurement.GetLatestHumidityMeasurement(deviceID);
        }

        public async Task<Measurement> GetLatestTemperatureMeasurement(long deviceID)
        {
            return await _measurement.GetLatestTemperatureMeasurement(deviceID);
        }

        public async Task<IEnumerable<Measurement>> GetAlarmMeasurements(long deviceID)
        {
            return await _measurement.GetAlarmMeasurements(deviceID);
        }

        public async Task<IEnumerable<Measurement>> GetCO2Measurements(long deviceID)
        {
            return await _measurement.GetCO2Measurements(deviceID);
        }

        public async Task<IEnumerable<Measurement>> GetHumidityMeasurements(long deviceID)
        {
            return await _measurement.GetHumidityMeasurements(deviceID);
        }

        public async Task<IEnumerable<Measurement>> GetTemperatureMeasurements(long deviceID)
        {
            return await _measurement.GetTemperatureMeasurements(deviceID);
        }

        public async Task RemoveMeasurement(long id)
        {
            await _measurement.RemoveMeasurement(id);
        }

        public async Task<Settings> GetSettings(long deviceID)

        {
            return await _settings.GetSettings(deviceID);
        }

        public async Task SetSettings(Settings settings, long deviceID)
        {
            await _settings.SetSettings(settings, deviceID);
        }
    }
}