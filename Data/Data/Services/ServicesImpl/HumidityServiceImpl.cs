using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class HumidityServiceImpl : IHumidityService
    {
        PersistenceRouter persistenceRouter;
        public HumidityServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
        }
        //HUMIDITY
        public async Task<Measurement> AddHumidity(Measurement humidity, long deviceId)
        {
            await persistenceRouter.AddHumidityMeasurement(humidity, deviceId);
            return humidity;
        }

        public async Task<IEnumerable<Measurement>> GetAllHumidities(long deviceId)
        {
            IEnumerable<Measurement> humidities = await persistenceRouter.GetHumidityMeasurements(deviceId);
            return humidities;
        }

        public async Task<Measurement> GetLastHumidity(long deviceId)
        {
            return await persistenceRouter.GetLatestHumidityMeasurement(deviceId);
        }

        public async Task RemoveHumidity(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}