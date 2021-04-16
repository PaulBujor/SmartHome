using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class MeasurementImpl : IMeasurement
    {
        public Task AddMeasurement(Measurement measurement)
        {
            throw new System.NotImplementedException();
        }

        public Task<Measurement> GetMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Measurement>> GetMeasurements()
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}