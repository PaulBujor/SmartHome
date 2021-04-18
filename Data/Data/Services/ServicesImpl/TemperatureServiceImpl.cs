using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class TemperatureServiceImpl : ITemperatureService
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;

        public TemperatureServiceImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }
        // TEMPERATURE
        public async Task<Measurement> AddTemperature(Measurement temperature, long deviceId)
        {
            hardwareService.CheckDeviceExists(deviceId);
            await persistenceRouter.AddTemperatureMeasurement(temperature, deviceId);
            return temperature;
        }

        public async Task<IEnumerable<Measurement>> GetAllTemperatures(long deviceId)
        {
            IEnumerable<Measurement> temperatures = await persistenceRouter.GetTemperatureMeasurements(deviceId);
            return temperatures;
        }

        public async Task<Measurement> GetLastTemperature(long deviceId)
        {
            return await persistenceRouter.GetLatestTemperatureMeasurement(deviceId);
        }

        public async Task RemoveTemperature(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}