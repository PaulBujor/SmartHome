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

        public async Task<IEnumerable<Measurement>> GetAllHumidities(long deviceId)
        {
            IEnumerable<MeasurementSet> measurements = await persistenceRouter.getAllMeasurementSets(deviceId);
            List<Measurement> humidities = new List<Measurement>();
            foreach (var measurementSet in measurements)
            {
                humidities.Add(new Measurement{Timestamp=measurementSet.Timestamp, Value=measurementSet.Humidity});
            }
            return humidities;
        }

        public async Task<Measurement> GetLastHumidity(long deviceId)
        {
            MeasurementSet measurements = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            
            return new Measurement{Timestamp=measurements.Timestamp, Value=measurements.Humidity};
        }
    }
}