using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class CO2ServiceImpl : ICO2Service
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;

        public CO2ServiceImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }
        // CO2
        public async Task<Measurement> AddCO2(Measurement co2, long deviceId)
        {
            await hardwareService.CheckDeviceExists(deviceId);
            await persistenceRouter.AddCO2Measurement(co2, deviceId);
            return co2;
        }

        public async Task RemoveCO2(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }

        public async Task<IEnumerable<Measurement>> GetAllCO2s(long deviceId)
        {
            IEnumerable<Measurement> co2s = await persistenceRouter.GetCO2Measurements(deviceId);
            return co2s;
        }

        public async Task<Measurement> GetLastCO2(long deviceId)
        {
            return await persistenceRouter.GetLatestCO2Measurement(deviceId);
        }

    }
}