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

        public async Task<IEnumerable<Measurement>> GetAllTemperatures(long deviceId)
        {
            IEnumerable<MeasurementSet> measurements = await persistenceRouter.getAllMeasurementSets(deviceId);
            List<Measurement> temperatures = new List<Measurement>();
            foreach (var measurementSet in measurements)
            {
                temperatures.Add(new Measurement{Timestamp=measurementSet.Timestamp, Value=measurementSet.Temperature});
            }
            return temperatures;
        }

        public async Task<Measurement> GetLastTemperature(long deviceId)
        {
            MeasurementSet measurements = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            
            return new Measurement{Timestamp=measurements.Timestamp, Value=measurements.Temperature};
        }
    }
}