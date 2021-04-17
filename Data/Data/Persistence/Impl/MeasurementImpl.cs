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

        public async Task<List<Measurement>> GetAlarmMeasurements(long deviceID)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Measurement>> GetCO2Measurements(long deviceID)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Measurement>> GetHumidityMeasurements(long deviceID)
        {
            throw new System.NotImplementedException();
        }

        public async Task<List<Measurement>> GetTemperatureMeasurements(long deviceID)
        {
            throw new System.NotImplementedException();
        }

        public async Task RemoveMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}