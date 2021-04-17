using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;
using Data.Services;

namespace Data
{
    public class SmartHomeImpl : ITemperatureService, IHumidityService, ICO2Service, IHardwareService, IAlarmService //ISmartHomeService
    {
        PersistenceRouter persistenceRouter;

        public SmartHomeImpl(){
            persistenceRouter = new PersistenceRouter();
        }

        // TEMPERATURE
        public async Task<Measurement> AddTemperature(Measurement temperature)
        {
            await persistenceRouter.AddMeasurement(temperature);
            return temperature;
        }

        public async Task<IList<Measurement>> GetAllTemperatures(long deviceId)
        {
            List<Measurement> temperatures = await persistenceRouter.GetTemperatureMeasurements(deviceId);
            return temperatures;
        }

        public Task<Measurement> GetLastTemperature(long deviceId)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveTemperature(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }


        // CO2
        public async Task<Measurement> AddCO2(Measurement co2)
        {
            await persistenceRouter.AddMeasurement(co2);
            return co2;
        }

        public async Task RemoveCO2(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }

        public async Task<IList<Measurement>> GetAllCO2s(long deviceId)
        {
            List<Measurement> co2s = await persistenceRouter.GetCO2Measurements(deviceId);
            return co2s;
        }

        public Task<Measurement> GetLastCO2(long deviceId)
        {
            throw new System.NotImplementedException();
        }


        // DEVICE
        public async Task<Device> AddDevice(Device device)
        {
            await persistenceRouter.AddDevice(device);
            return device;
        }

        public async Task RemoveDevice(long DeviceId)
        {
            await persistenceRouter.RemoveDevice(DeviceId);
        }

        //HUMIDITY
        public async Task<Measurement> AddHumidity(Measurement humidity)
        {
            await persistenceRouter.AddMeasurement(humidity);
            return humidity;
        }

        public async Task<IList<Measurement>> GetAllHumidities(long deviceId)
        {
            List<Measurement> humidities = await persistenceRouter.GetHumidityMeasurements(deviceId);
            return humidities;
        }

        public Task<Measurement> GetLastHumidity()
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveHumidity(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }

        //SETTINGS
        public async Task<Settings> AddSetting(Settings settings)
        {
            await persistenceRouter.AddSetting(settings);
            return settings;
        }
        public async Task<IList<Measurement>> GetAllSettings(long deviceId)
        {
            //missing deviceId in persistenceRouter???
            List<Measurement> settings = await persistenceRouter.GetSettings(deviceId);
            return settings;
        }

        public async Task RemoveSetting(int id)
        {
            await persistenceRouter.RemoveSetting(id);
        }

        public Task<Settings> GetSettingsByID(long id)
        {
            //not sure if this method is needed...
            throw new System.NotImplementedException();
        }

        //MOTION
        public async Task<Measurement> AddMotion(Measurement motion)
        {
            await persistenceRouter.AddMeasurement(motion);
            return motion;
        }

        public async Task<IList<Measurement>> GetAllMotions(long deviceId)
        {
            List<Measurement> motions = await persistenceRouter.GetAlarmMeasurements(deviceId);
            return motions;
        }

        public Task<Measurement> GetLastMotion(long deviceId)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveMotion(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
        
    }
}