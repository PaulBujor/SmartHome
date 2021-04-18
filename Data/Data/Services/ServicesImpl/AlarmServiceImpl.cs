using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class AlarmServiceImpl : IAlarmService
    {
        PersistenceRouter persistenceRouter;
        public AlarmServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
        }
        //MOTION
        public async Task<Measurement> AddMotion(Measurement motion, long deviceId)
        {
            await persistenceRouter.AddAlarmMeasurement(motion, deviceId);
            return motion;
        }

        public async Task<IEnumerable<Measurement>> GetAllMotions(long deviceId)
        {
            IEnumerable<Measurement> motions = await persistenceRouter.GetAlarmMeasurements(deviceId);
            return motions;
        }

        public async Task<Measurement> GetLastMotion(long deviceId)
        {
            return await persistenceRouter.GetLatestAlarmMeasurement(deviceId);
        }

        public async Task RemoveMotion(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}