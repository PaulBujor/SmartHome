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
        
        public async Task<IEnumerable<Measurement>> GetAllCO2s(long deviceId)
        {
            IEnumerable<MeasurementSet> measurements = await persistenceRouter.getAllMeasurementSets(deviceId);
            List<Measurement> co2s = new List<Measurement>();
            foreach (var measurementSet in measurements)
            {
                co2s.Add(new Measurement{Timestamp=measurementSet.Timestamp, Value=measurementSet.CO2});
            }
            return co2s;
        }

        public async Task<Measurement> GetLastCO2(long deviceId)
        {
            MeasurementSet measurements = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            
            return new Measurement{Timestamp=measurements.Timestamp, Value=measurements.CO2};
        }

    }
}