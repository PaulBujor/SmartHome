using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class AlarmServiceImpl : IAlarmService
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;
        public AlarmServiceImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }

        public async Task<IEnumerable<Measurement>> GetAllMotions(long deviceId)
        {
            IEnumerable<MeasurementSet> measurements = await persistenceRouter.getAllMeasurementSets(deviceId);
            List<Measurement> alarms = new List<Measurement>();
            foreach (var measurementSet in measurements)
            {
                alarms.Add(new Measurement{Timestamp=measurementSet.Timestamp, Value=measurementSet.Alarm});
            }
            return alarms;
        }

        public async Task<Measurement> GetLastMotion(long deviceId)
        {
            MeasurementSet measurements = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            
            return new Measurement{Timestamp=measurements.Timestamp, Value=measurements.Alarm};
        }
    }
}