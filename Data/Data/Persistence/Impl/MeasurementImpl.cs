using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;
using Data.Data.ConcreteMeasurements;
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
        
        public async Task AddCO2Measurement(Measurement measurement, long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            tmpDevice.CO2.Add((CO2Measurement)measurement);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task AddAlarmMeasurement(Measurement measurement, long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            tmpDevice.Alarm.Add((AlarmMeasurement)measurement);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task AddHumidityMeasurement(Measurement measurement, long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            tmpDevice.Humidity.Add((HumidityMeasurement)measurement);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task AddTemperatureMeasurement(Measurement measurement, long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            tmpDevice.Temperature.Add((TemperatureMeasurement)measurement);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task<Measurement> GetLatestCO2Measurement(long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p => p.CO2)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            Measurement tmpMeasurement = tmpDevice.CO2.OrderByDescending(p => p.Timestamp).ToArray()[0];
            return tmpMeasurement;
        }

        public async Task<Measurement> GetLatestAlarmMeasurement(long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p => p.Alarm)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            Measurement tmpMeasurement = tmpDevice.Alarm.OrderByDescending(p => p.Timestamp).ToArray()[0];
            return tmpMeasurement;
        }

        public async Task<Measurement> GetLatestHumidityMeasurement(long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p => p.Humidity)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            Measurement tmpMeasurement = tmpDevice.Humidity.OrderByDescending(p => p.Timestamp).ToArray()[0];
            return tmpMeasurement;
        }

        public async Task<Measurement> GetLatestTemperatureMeasurement(long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p => p.Temperature)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            Measurement tmpMeasurement = tmpDevice.Temperature.OrderByDescending(p => p.Timestamp).ToArray()[0];
            return tmpMeasurement;
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