using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class TemperatureServiceImpl : ITemperatureService
    {
        PersistenceRouter persistenceRouter;

        public TemperatureServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
        }
        // TEMPERATURE
        public async Task<Measurement> AddTemperature(Measurement temperature, long deviceId)
        {
            await persistenceRouter.AddMeasurement(temperature, deviceId);
            return temperature;
        }

        public async Task<IList<Measurement>> GetAllTemperatures(long deviceId)
        {
            List<Measurement> temperatures = await persistenceRouter.GetTemperatureMeasurements(deviceId);
            return temperatures;
        }

        public Task<Measurement> GetLastTemperature(long deviceId)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveTemperature(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }
    }
}