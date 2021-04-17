using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class MeasurementImpl : IMeasurement
    {
        private Database _databaseContext;

        public MeasurementImpl(Database context)
        {
            _databaseContext = context;
        }
        public async Task AddMeasurement(Measurement measurement)
        {
            throw new System.NotImplementedException();
        }

        public async Task<Measurement> GetMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Measurement>> GetMeasurements()
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}