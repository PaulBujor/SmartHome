using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class AlarmServiceImpl
    {
        PersistenceRouter persistenceRouter;
        public AlarmServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
        }
        //MOTION
        public async Task<Measurement> AddMotion(Measurement motion, long deviceId)
        {
            await persistenceRouter.AddMeasurement(motion, deviceId);
            return motion;
        }

        public async Task<IEnumerable<Measurement>> GetAllMotions(long deviceId)
        {
            IEnumerable<Measurement> motions = await persistenceRouter.GetAlarmMeasurements(deviceId);
            return motions;
        }

        public Task<Measurement> GetLastMotion(long deviceId)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveMotion(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}