using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class HumidityServiceImpl : IHumidityService
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;
        public HumidityServiceImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }
        //HUMIDITY
        public async Task<Measurement> AddHumidity(Measurement humidity, long deviceId)
        {
            hardwareService.CheckDeviceExists(deviceId);
            await persistenceRouter.AddHumidityMeasurement(humidity, deviceId);
            return humidity;
        }

        public async Task<IEnumerable<Measurement>> GetAllHumidities(long deviceId)
        {
            IEnumerable<Measurement> humidities = await persistenceRouter.GetHumidityMeasurements(deviceId);
            return humidities;
        }

        public async Task<Measurement> GetLastHumidity(long deviceId)
        {
            return await persistenceRouter.GetLatestHumidityMeasurement(deviceId);
        }

        public async Task RemoveHumidity(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}