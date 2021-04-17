using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;
using Microsoft.EntityFrameworkCore;

namespace Data.Properties.Persistence.Impl
{
    public class MeasurementImpl : IMeasurement
    {
        private Database _databaseContext;

        public MeasurementImpl(Database context)
        {
            _databaseContext = context;
        }

        public async Task AddMeasurement(Measurement measurement, long deviceID)
        {
            throw new System.NotImplementedException();
            /*await _databaseContext.Measurements.AddAsync(measurement);*/
        }

        public async Task<Measurement> GetMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<IEnumerable<Measurement>> GetAlarmMeasurements(long deviceID)
        {
            return (await _databaseContext.Devices.Include(p=>p.Alarm).FirstOrDefaultAsync(p => p.DeviceID == deviceID)).Alarm;
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