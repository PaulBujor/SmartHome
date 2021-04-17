using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Services;

namespace Data
{
    public class SmartHomeImpl : ITemperatureService, IHumidityService, ICO2Service, IHardwareService, ISmartHomeService  //IService is now called ISmartHomeService
    {

        // TEMPERATURE
        public Task<Measurement> AddTemperature(Measurement temperature)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<Measurement>> GetAllTemperatures()
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetLastTemeperature()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveTemperature(int id)
        {
            throw new System.NotImplementedException();
        }


        // CO2
        public Task<Measurement> AddCO2(Measurement co2)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveCO2(int id)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<Measurement>> GetAllCO2s()
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetLastCO2()
        {
            throw new System.NotImplementedException();
        }


        // DEVICE
        public Task<Device> AddDevice(Device device)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveDevice(long DeviceId)
        {
            throw new System.NotImplementedException();
        }

        //HUMIDITY
        public Task<Measurement> AddHumidity(Measurement humidity)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<Measurement>> GetAllHumidities()
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetLastHumidity()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveHumidity(int id)
        {
            throw new System.NotImplementedException();
        }

        //SETTINGS
        public Task<Settings> AddSetting(Settings settings)
        {
            throw new System.NotImplementedException();
        }
        public Task<IList<Measurement>> GetAllSettings()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveSetting(int id)
        {
            throw new System.NotImplementedException();
        }

        public Task<Settings> GetSettingsByID(long id)
        {
            throw new System.NotImplementedException();
        }

        //MOTION
        public Task<Measurement> AddMotion(Measurement motion)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<Measurement>> GetAllMotions()
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetLastMotion()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveMotion(int id)
        {
            throw new System.NotImplementedException();
        }
        
    }
}