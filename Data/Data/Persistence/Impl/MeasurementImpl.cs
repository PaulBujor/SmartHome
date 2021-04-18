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
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            
        }

        public async Task<Measurement> GetMeasurement(long id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<IEnumerable<Measurement>> GetAlarmMeasurements(long deviceID)
        {
            return (await _databaseContext.Devices.Include(p=>p.Alarm).FirstOrDefaultAsync(p => p.DeviceID == deviceID)).Alarm;
        }

        public async Task<IEnumerable<Measurement>> GetCO2Measurements(long deviceID)
        {
            return (await _databaseContext.Devices.Include(p => p.CO2).FirstOrDefaultAsync(p => p.DeviceID == deviceID))
                .CO2;
        }

        public async Task<IEnumerable<Measurement>> GetHumidityMeasurements(long deviceID)
        {
            return (await _databaseContext.Devices.Include(p => p.Humidity)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID)).Humidity;
        }

        public async Task<IEnumerable<Measurement>> GetTemperatureMeasurements(long deviceID)
        {
            return (await _databaseContext.Devices.Include(p => p.Temperature)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID)).Temperature;
        }

        public async Task RemoveMeasurement(long id)
        {
            Measurement tmpMeasurement =
                await _databaseContext.Measurements.FirstOrDefaultAsync(p => p.MeasurementID == id);
            if (tmpMeasurement != null)
            {
                _databaseContext.Remove(tmpMeasurement);
                await _databaseContext.SaveChangesAsync();
            }
        }
    }
}