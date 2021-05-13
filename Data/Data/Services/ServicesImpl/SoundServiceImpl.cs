using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class SoundServiceImpl : ISoundService
    {
        private readonly PersistenceRouter persistenceRouter;
        private readonly IHardwareService hardwareService;
        public SoundServiceImpl(PersistenceRouter persistenceRouter, IHardwareService service)
        {
            this.persistenceRouter = persistenceRouter;
            this.hardwareService = service;
        }

        public async Task<IEnumerable<Measurement>> GetAllSounds(long deviceId)
        {
            IEnumerable<MeasurementSet> measurements = await persistenceRouter.getAllMeasurementSets(deviceId);
            List<Measurement> sounds = new List<Measurement>();
            foreach (var measurementSet in measurements)
            {
                sounds.Add(new Measurement{Timestamp=measurementSet.Timestamp, Value=measurementSet.Sound});
            }
            return sounds;
        }

        public async Task<Measurement> GetLastSound(long deviceId)
        {
            MeasurementSet measurements = await persistenceRouter.getLatestMeasurmentSet(deviceId);
            
            return new Measurement{Timestamp=measurements.Timestamp, Value=measurements.Sound};
        }
    }
}