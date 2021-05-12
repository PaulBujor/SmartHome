using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class MeasurementSetImpl : IMeasurementSetService
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;

        public MeasurementSetImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }
        public async Task AddMeasurementSet(long deviceId, MeasurementSet measurementSet)
        {
            await hardwareService.CheckDeviceExists(deviceId);
            await persistenceRouter.addMeasurementSet(measurementSet, deviceId);
        }

        async Task<MeasurementSet> IMeasurementSetService.GetAllMeasurementsSet(long deviceId)
        {
            return await persistenceRouter.getLatestMeasurmentSet(deviceId);
        }

        async Task<MeasurementSet> IMeasurementSetService.GetLastMeasurementSet(long deviceId)
        {
            MeasurementSet lastMeasurementSet = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            return lastMeasurementSet;
        }
    }
}