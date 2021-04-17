using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class CO2ServiceImpl : ICO2Service
    {
        PersistenceRouter persistenceRouter;

        public CO2ServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
            
        }
        // CO2
        public async Task<Measurement> AddCO2(Measurement co2, long deviceId)
        {
            await persistenceRouter.AddMeasurement(co2, deviceId);
            return co2;
        }

        public async Task RemoveCO2(int id)
        {
            await persistenceRouter.RemoveMeasurement(id);
        }

        public async Task<IList<Measurement>> GetAllCO2s(long deviceId)
        {
            List<Measurement> co2s = await persistenceRouter.GetCO2Measurements(deviceId);
            return co2s;
        }

        public Task<Measurement> GetLastCO2(long deviceId)
        {
            throw new System.NotImplementedException();
        }

    }
}